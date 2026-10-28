#
# Checks a TeamCity Windows image against CVE-2026-111943. Read-only.
# Fails if the Corretto truststore or the MinGit CA bundle still trusts the compromised roots,
# or if the Windows 'Disallowed' store does not block all compromised intermediates.
#
# Usage: docker run --rm -v "${PWD}\windows\revoked-ca:C:\check" --entrypoint pwsh <image> -NoProfile -File C:\check\verify-compromised-ca-absence.ps1
#        (use 'powershell' instead of 'pwsh' for 'windowsservercore' images)
#

$ErrorActionPreference = 'Stop'

# SHA-256 fingerprints of the emSign Root TLS CA - G3, emSign Root CS CA - G3, emSign ECC Root CA - G3 and AAA Certificate Services roots (https://emudhra.we-have-a-problem.com/roots.json)
$roots = @(
    '86A1ECBA089C4A8D3BBE2734C612BA341D813E043CF9E8A862CD5C57A36BBE6B'
    'D7A7A0FB5D7E2731D771E9484EBCDEF71D5F0C3E0A2948782BC83EE0EA699EF4'
    '7DD78D5F4F13459A83DFF9ABBBA62EDBAF6F2D102BF257FD712F4D9F2746ED8D'
    'F2EDAAFF3D9B701FB4D14C9F285F8C0BAD90FC3BCC1FA07D47F4718EDABA34A5'
)

# SHA-256 fingerprints of the compromised intermediates, blocked through the 'Disallowed' store
$intermediates = @(
    '42C4ACCD12C4F59597925E1A08A7A90102608543809308D50EC427E118BA1493'  # EM DV TLS CA - G3B <- emSign Root TLS CA - G3
    '45A883F349AEDFD5F3A649B5498FFB121712BA8B448F3F2A91DBC5A766FCDDBE'  # EM DV TLS CA - G3B <- AAA Certificate Services
    '98B896625C0C230ADEEC3EB2CF6AAD5001ECF9156A68644E82374CE5833BC25B'  # EM OV TLS CA - G3B <- emSign Root TLS CA - G3
    '5F1DED4DD590AF74631290CF073B122A2AAC6A1FA479778A0BD43579BD832699'  # EM OV TLS CA - G3B <- AAA Certificate Services
    'E7568FE6E12331F4FD7DE61DA4C7559D0F8EB192A218D4741A3BA75184333838'  # EM EV TLS CA - G3B <- emSign Root TLS CA - G3
    '579480AEA166290BD3C643589E795FC0D0E54E86505D180F1DFF99EFD3199512'  # EM EV TLS CA - G3B <- AAA Certificate Services
    '0164D7CEAE5BB909C272AF151FF44B1177AE9B47D9424B94F1D9558C30934D19'  # EM DV TLS CA - G3C <- emSign Root TLS CA - G3
    '62B7F9868263F72F6911FC78CEAF72C1979158B7368BF648683BB2AA39B02E72'  # EM DV TLS CA - G3C <- AAA Certificate Services
    'CB6E15165AC802E9B3F993F16C25D41F4E81C21EBDE71AE1B012AC00BF9C7C3F'  # EM OV TLS CA - G3C <- emSign Root TLS CA - G3
    '7485B51C0ED192878FA1D2F948FB529AE1842CC5086C4B67F7BE5B2039DE9489'  # EM OV TLS CA - G3C <- AAA Certificate Services
    '432EC25F0D28DE9006D97BF752F17BBB2CD41F669933FCB072D9A1148D0261F3'  # EM EV TLS CA - G3C <- emSign Root TLS CA - G3
    'FD9D5C2DBBD4995AB69B8D50C439D9527B00529199C2FD01B2174EB58F07021F'  # EM EV TLS CA - G3C <- AAA Certificate Services
    '9E39DE2881273ACC225A149C72BA3E0F51424963669AB633EE1DC7DF5DB067B4'  # EM OV CS CA - G3B <- emSign Root CS CA - G3
    'A496E23AC0CAB8155FC9B117B97EAE368EFCBB9041AEC8804D5A308D2D01C2D8'  # EM OV CS CA - G3B <- AAA Certificate Services
    '4B2460A466F95FD40113BCDCCA2CEB29B3B093921DE45474DA7DF48874C630B1'  # EM OV CS CA - G3C <- emSign Root CS CA - G3
    '84112972B8E59D14560AE7E7119654474AAD119990CF3722BFC1E3B91615E1FD'  # EM OV CS CA - G3C <- AAA Certificate Services
)

$script:failed = $false

function Get-Sha256([byte[]] $bytes) {
    [BitConverter]::ToString([Security.Cryptography.SHA256]::Create().ComputeHash($bytes)).Replace('-', '')
}

function Test-Trusted([string] $name, [string[]] $fingerprints) {
    if (-not $fingerprints) {
        Write-Host "FAIL: ${name}: no certificates found"; $script:failed = $true
    } elseif ($fingerprints | Where-Object { $roots -contains $_ }) {
        Write-Host "FAIL: $name trusts a compromised root"; $script:failed = $true
    } else {
        Write-Host "OK:   $name"
    }
}

# Corretto ships its own truststore
$java = "$Env:ProgramFiles\Java\OpenJDK\bin"
$javaFps = & "$java\keytool.exe" -list -cacerts -storepass changeit -v |
    Where-Object { $_ -match '^\s*SHA256:\s*(\S+)' } | ForEach-Object { $Matches[1].Replace(':', '') }
Test-Trusted 'Corretto truststore' $javaFps

# MinGit ships its own CA bundle (Server and Agent images only)
if (Test-Path "$Env:ProgramFiles\Git\cmd\git.exe") {
    foreach ($bundle in Get-ChildItem "$Env:ProgramFiles\Git" -Recurse -Filter 'ca-bundle.crt') {
        $pem = [IO.File]::ReadAllText($bundle.FullName)
        $gitFps = [regex]::Matches($pem, '-----BEGIN CERTIFICATE-----([^-]+)-----END CERTIFICATE-----') |
            ForEach-Object { Get-Sha256 ([Convert]::FromBase64String($_.Groups[1].Value)) }
        Test-Trusted "MinGit CA bundle ($($bundle.FullName))" $gitFps
    }
    & "$Env:ProgramFiles\Git\cmd\git.exe" --version
}

# The roots stay trusted on Windows, the intermediates are blocked through the 'Disallowed' store
$disallowed = Get-ChildItem Cert:\LocalMachine\Disallowed | ForEach-Object { Get-Sha256 $_.RawData }
$missing = @($intermediates | Where-Object { $disallowed -notcontains $_ })
if ($missing.Count -gt 0) {
    Write-Host "FAIL: Windows 'Disallowed' store misses $($missing.Count) / $($intermediates.Count) compromised intermediates (apply patch.Dockerfile)"
    $missing | ForEach-Object { Write-Host "        $_" }
    $script:failed = $true
} else {
    Write-Host "OK:   Windows 'Disallowed' store"
}

# 'java -version' writes to stderr, which Windows PowerShell turns into an error under 'Stop'
$ErrorActionPreference = 'Continue'
& "$java\java.exe" -version 2>&1 | Select-Object -First 1 | ForEach-Object { "$_" }

if ($script:failed) { exit 1 }
