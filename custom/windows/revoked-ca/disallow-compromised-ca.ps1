#
# Adds the compromised intermediates (disallowed\<SHA-256>.crt) to the local machine 'Disallowed' store.
# Certificates already in the store are kept. Fails if a file does not match its SHA-256 or is not in the store afterwards.
# Runs in both PowerShell 7 (nanoserver) and Windows PowerShell 5.1 (windowsservercore).
#

$ErrorActionPreference = 'Stop'

function Get-Sha256([byte[]] $bytes) {
    [BitConverter]::ToString([Security.Cryptography.SHA256]::Create().ComputeHash($bytes)).Replace('-', '')
}

$files = @(Get-ChildItem "$PSScriptRoot\disallowed" -Filter '*.crt')
if ($files.Count -eq 0) { throw "No certificates found in $PSScriptRoot\disallowed" }

$store = [Security.Cryptography.X509Certificates.X509Store]::new('Disallowed', 'LocalMachine')
$store.Open('ReadWrite')
try {
    foreach ($file in $files) {
        $cert = [Security.Cryptography.X509Certificates.X509Certificate2]::new($file.FullName)
        if ((Get-Sha256 $cert.RawData) -ne $file.BaseName) { throw "SHA-256 mismatch: $($file.Name)" }
        $store.Add($cert)
        Write-Host "Disallowed: $($cert.Subject) <- $($cert.Issuer)"
    }
} finally {
    $store.Close()
}

$store.Open('ReadOnly')
$present = @($store.Certificates | ForEach-Object { Get-Sha256 $_.RawData })
$store.Close()
$missing = @($files | Where-Object { $present -notcontains $_.BaseName })
if ($missing.Count -gt 0) { throw "Not in the 'Disallowed' store: $(($missing | ForEach-Object { $_.BaseName }) -join ', ')" }
