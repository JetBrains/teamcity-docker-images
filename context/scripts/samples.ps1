$code = Get-Content -Path "Web.cs" -Raw
Add-Type -TypeDefinition "$code" -Language CSharp
$src1 = 'https://github.com/git-for-windows/git/releases/download/v2.19.1.windows.1/MinGit-2.19.1-64-bit.zip'
$src2 = 'https://www.mercurial-scm.org/release/windows/mercurial-5.5.1-x64.msi'
$downloadScript = [Scripts.Web]::DownloadFiles($src1, 'git.zip', $src2, 'mercurial.msi')
iex $downloadScript
