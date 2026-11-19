# check=skip=InvalidDefaultArgInFrom
#
# Patches a published TeamCity Linux image against CVE-2026-114938 ("Java4Shell") by replacing the
# bundled Amazon Corretto JDK with a fixed build (21.0.13.1.2).
#
# @param baseImage  image to patch, e.g. 'jetbrains/teamcity-agent:2026.2-linux'
# @param imageUser  'buildagent' (Agent) or 'tcuser' (Server)
# @param jdkVersion Corretto build to install; drives both the download URL and the post-install check
#

ARG baseImage

FROM ${baseImage}

USER root

ARG imageUser=buildagent

ARG jdkVersion=21.0.13.1.2
ARG jdkLinuxX64Component="https://corretto.aws/downloads/resources/${jdkVersion}/amazon-corretto-${jdkVersion}-linux-x64.tar.gz"
ARG jdkLinuxX64ComponentMD5SUM='9e596431ec718c529ee9238f983eb5eb'

# One layer on purpose: the original files stay in the layers below, so every extra layer only adds overhead.
RUN set -eux; \
    case "$(uname -m)" in \
        x86_64) ;; \
        aarch64) echo "CVE-2026-114938 does not affect ARM64 (the vulnerable intrinsic is x86-64 AVX-512 VBMI only); no patch needed."; exit 1 ;; \
        *) echo "Unsupported architecture: $(uname -m)"; exit 1 ;; \
    esac; \
    curl -LfsSo /tmp/openjdk.tar.gz "${jdkLinuxX64Component}"; \
    echo "${jdkLinuxX64ComponentMD5SUM} */tmp/openjdk.tar.gz" | md5sum -c -; \
    rm -rf /opt/java/openjdk; \
    mkdir -p /opt/java/openjdk; \
    tar -xf /tmp/openjdk.tar.gz -C /opt/java/openjdk --strip-components=1; \
    chown -R root:root /opt/java; \
    rm -f /tmp/openjdk.tar.gz; \
    /opt/java/openjdk/bin/java -version 2>&1 | grep -F "${jdkVersion}"

USER ${imageUser}
