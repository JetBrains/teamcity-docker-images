#
# Patches a published TeamCity Linux image (Ubuntu 24.04) against CVE-2026-111943.
# Updates OS CA certificates and Corretto (it ships its own truststore).
# Git and Docker use the OS CA bundle, so they are covered by the CA update.
#
# @param baseImage image to patch, e.g. 'jetbrains/teamcity-agent:2026.1-linux'
# @param imageUser 'buildagent' (Agent) or 'tcuser' (Server)
#

ARG baseImage

FROM ${baseImage}

USER root

ARG imageUser=buildagent

ARG jdkLinuxX64Component='https://corretto.aws/downloads/resources/21.0.13.0.11/amazon-corretto-21.0.13.0.11-linux-x64.tar.gz'
ARG jdkLinuxX64ComponentMD5SUM='952bee5b1b79bfb8331ce0faa9f85960'
ARG jdkLinuxARM64Component='https://corretto.aws/downloads/resources/21.0.13.0.11/amazon-corretto-21.0.13.0.11-linux-aarch64.tar.gz'
ARG jdkLinuxARM64ComponentMD5SUM='0b736dfb0921884f97e30e07be169e9b'

# One layer on purpose: the original files stay in the layers below, so every extra layer only adds overhead.
RUN set -eux; \
    # The patch is only validated for Ubuntu 24.04
    . /etc/os-release; \
    [ "$VERSION_ID" = "24.04" ] || { echo "Unsupported base OS for the patch: $PRETTY_NAME (expected Ubuntu 24.04)"; exit 1; }; \
    apt-get update; \
    # OS CA certificates
    apt-get install -y --no-install-recommends ca-certificates; \
    # Amazon Corretto (same location as in the original image)
    case "$(uname -m)" in \
        x86_64)  jdkUrl="${jdkLinuxX64Component}";   jdkMd5="${jdkLinuxX64ComponentMD5SUM}" ;; \
        aarch64) jdkUrl="${jdkLinuxARM64Component}"; jdkMd5="${jdkLinuxARM64ComponentMD5SUM}" ;; \
        *) echo "Unsupported architecture: $(uname -m)"; exit 1 ;; \
    esac; \
    curl -LfsSo /tmp/openjdk.tar.gz "$jdkUrl"; \
    echo "$jdkMd5 */tmp/openjdk.tar.gz" | md5sum -c -; \
    rm -rf /opt/java/openjdk; \
    mkdir -p /opt/java/openjdk; \
    tar -xf /tmp/openjdk.tar.gz -C /opt/java/openjdk --strip-components=1; \
    chown -R root:root /opt/java; \
    rm -f /tmp/openjdk.tar.gz; \
    /opt/java/openjdk/bin/java -version; \
    apt-get clean; rm -rf /var/lib/apt/lists/*; \
    # CVE-2026-111943: fail the build if the CA bundle still contains a compromised root (https://emudhra.we-have-a-problem.com/roots.json)
    mkdir /tmp/ca; \
    csplit -sz -f /tmp/ca/c /etc/ssl/certs/ca-certificates.crt '/-----BEGIN CERTIFICATE-----/' '{*}'; \
    fps="$(for f in /tmp/ca/c*; do openssl x509 -in "$f" -noout -fingerprint -sha256; done)"; \
    [ -n "$fps" ]; \
    ! echo "$fps" | grep -F \
      -e '86:A1:EC:BA:08:9C:4A:8D:3B:BE:27:34:C6:12:BA:34:1D:81:3E:04:3C:F9:E8:A8:62:CD:5C:57:A3:6B:BE:6B' \
      -e 'D7:A7:A0:FB:5D:7E:27:31:D7:71:E9:48:4E:BC:DE:F7:1D:5F:0C:3E:0A:29:48:78:2B:C8:3E:E0:EA:69:9E:F4' \
      -e '7D:D7:8D:5F:4F:13:45:9A:83:DF:F9:AB:BB:A6:2E:DB:AF:6F:2D:10:2B:F2:57:FD:71:2F:4D:9F:27:46:ED:8D' \
      -e 'F2:ED:AA:FF:3D:9B:70:1F:B4:D1:4C:9F:28:5F:8C:0B:AD:90:FC:3B:CC:1F:A0:7D:47:F4:71:8E:DA:BA:34:A5'; \
    rm -rf /tmp/ca

USER ${imageUser}
