function coalesce($a, $b) { if ($a -ne $null) { $a } else { $b } }

Write-Host @"

 Welcome to TeamCity Server Docker container

 * Installation directory: $Env:TEAMCITY_DIST
 * Logs directory:         $Env:TEAMCITY_LOGS
 * Data directory:         $Env:TEAMCITY_DATA_PATH

"@

# Setting default values if variables not present
$TEAMCITY_DIST = coalesce $Env:TEAMCITY_DIST 'C:\TeamCity'
$TEAMCITY_CONTEXT = coalesce $Env:TEAMCITY_CONTEXT 'ROOT'
$TEAMCITY_SERVER_XML = ('{0}\conf\server.xml' -f $TEAMCITY_DIST)
$TEAMCITY_STOP_WAIT_TIME = coalesce $Env:TEAMCITY_STOP_WAIT_TIME 60
$TEAMCITY_SERVER_SCRIPT = ('{0}\bin\teamcity-server.bat' -f $TEAMCITY_DIST)
$Env:TEAMCITY_LOGS = coalesce $Env:TEAMCITY_LOGS ('{0}\logs' -f $TEAMCITY_DIST)

if (Test-Path -Path $Env:TEAMCITY_LOGS) {
    Get-ChildItem $Env:TEAMCITY_LOGS -Filter "*.pid" | ForEach-Object { Remove-Item $_.FullName -Force }
}

if ($TEAMCITY_CONTEXT -ne 'ROOT') {
    $current = Get-ChildItem ('{0}\webapps' -f $TEAMCITY_DIST) -Depth 0 -Name
    if ($current -ne $TEAMCITY_CONTEXT) {
        $currentPath = ('{0}\webapps\{1}' -f $TEAMCITY_DIST, $current)
        $destinationPath = ('{0}\webapps\{1}' -f $TEAMCITY_DIST, $TEAMCITY_CONTEXT)
        Move-Item -Path $currentPath -Destination $destinationPath
    }
}

if ($Env:TEAMCITY_HTTPS_PROXY_ENABLED -eq 'true') {
    $TEAMCITY_SERVER_XML = ('{0}\conf\server-https-proxy.xml' -f $TEAMCITY_DIST)
    Write-Host "Proxy is enabled."
}

# Set traps to gently shutdown server on `docker stop`, `docker restart` or `docker kill -s 15`
Trap {
    &$TEAMCITY_SERVER_SCRIPT stop $TEAMCITY_STOP_WAIT_TIME -force
    exit $LastExitCode
}

# Start and wait for exit
&$TEAMCITY_SERVER_SCRIPT run "-config" $TEAMCITY_SERVER_XML
exit $LastExitCode