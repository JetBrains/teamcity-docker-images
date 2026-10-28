#!/bin/sh
#
# Checks a TeamCity Linux image against CVE-2026-111943. Read-only.
# Fails if the OS CA bundle or the Corretto truststore still trusts the compromised roots.
#
# Usage: docker run --rm -v "$PWD/linux/revoked-ca:/check:ro" --entrypoint sh <image> /check/verify-compromised-ca-absence.sh
#

set -eu

# SHA-256 fingerprints of the emSign Root TLS CA - G3, emSign Root CS CA - G3, emSign ECC Root CA - G3 and AAA Certificate Services roots (https://emudhra.we-have-a-problem.com/roots.json)
roots='86:A1:EC:BA:08:9C:4A:8D:3B:BE:27:34:C6:12:BA:34:1D:81:3E:04:3C:F9:E8:A8:62:CD:5C:57:A3:6B:BE:6B
D7:A7:A0:FB:5D:7E:27:31:D7:71:E9:48:4E:BC:DE:F7:1D:5F:0C:3E:0A:29:48:78:2B:C8:3E:E0:EA:69:9E:F4
7D:D7:8D:5F:4F:13:45:9A:83:DF:F9:AB:BB:A6:2E:DB:AF:6F:2D:10:2B:F2:57:FD:71:2F:4D:9F:27:46:ED:8D
F2:ED:AA:FF:3D:9B:70:1F:B4:D1:4C:9F:28:5F:8C:0B:AD:90:FC:3B:CC:1F:A0:7D:47:F4:71:8E:DA:BA:34:A5'

status=0
tmp="$(mktemp -d)"
trap 'rm -rf "$tmp"' EXIT

# $1 - name, $2 - file with the SHA-256 fingerprints of the trusted certificates
check() {
    if [ ! -s "$2" ]; then
        echo "FAIL: $1: no certificates found"; status=1
    elif echo "$roots" | grep -Fqf - "$2"; then
        echo "FAIL: $1 trusts a compromised root"; status=1
    else
        echo "OK:   $1"
    fi
}

# OS CA bundle (used by Git, Docker, curl, etc.)
csplit -sz -f "$tmp/c" /etc/ssl/certs/ca-certificates.crt '/-----BEGIN CERTIFICATE-----/' '{*}'
for f in "$tmp"/c*; do openssl x509 -in "$f" -noout -fingerprint -sha256; done > "$tmp/os"
check "OS CA bundle" "$tmp/os"

# Corretto ships its own truststore
java="${JAVA_HOME:-/opt/java/openjdk}"
"$java/bin/keytool" -list -cacerts -storepass changeit -v | grep 'SHA256:' > "$tmp/java" || true
check "Corretto truststore" "$tmp/java"

"$java/bin/java" -version 2>&1 | head -1

exit $status
