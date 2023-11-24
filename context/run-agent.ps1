$agentDist="C:/BuildAgent"
$agentScript="${agentDist}/bin/agent.bat"
$configDir="${agentDist}/conf"
$logDir="${agentDist}/logs"

function fixDns() {
    if($Env:USE_CUSTOM_DNS) {
        $nic = Get-NetAdapter
        Set-DnsClientServerAddress -InterfaceIndex $nic.IfIndex -ServerAddresses ("$Env:USE_CUSTOM_DNS")
        Write-Host "Updated interface $nic.IfIndex to use DNS Server $Env:USE_CUSTOM_DNS"
    }
}

function configure($options) {
    if ($options.length -eq 0) { return }

    Write-Host "Run agent.bat configure ${options}"
    &$agentScript configure $options
    if ($LastExitCode -ne 0) { throw "Failed to update configuration $LastExitCode" }
    Write-Host "File buildAgent.properties was updated"
}

function unquote($value) {
    # Strip double quotes produced by docker-compose
    return $value -Replace '""', '"'
}

# Executes "agent configure" utility with the variables specified at container's start up: ...
# ... server url, name, token, etc.
function reconfigure() {
    $options=@()
    fixDns
    if ($Env:SERVER_URL) {
        $options += "--server-url"
        $options += unquote $env:SERVER_URL
    }
    if ($Env:AGENT_TOKEN) {
        $options += "--auth-token"
        $options += unquote $env:AGENT_TOKEN
    }
    if ($Env:AGENT_NAME) {
        $options += "--name"
        $options += unquote $env:AGENT_NAME
    }

    configure $options
}

# If buildAgent.dist.properties exist, saves it as buildAgent.properties (overwrites if exists)
function prepare_conf() {
    Write-Host "Will prepare agent configuration"
    Get-ChildItem "${agentDist}/conf" -Recurse | ForEach-Object {
        Write-Host ("Processing: " + $_.Name)
        if ($_.Name -eq "buildAgent.dist.properties") {
            Move-Item $_.FullName "${configDir}/buildAgent.properties" -Force
        } else {
            Move-Item $_.FullName -Destination $configDir -Force
        }
    }
    Write-Host "File buildAgent.properties was created"
}

if (Test-Path -Path $logDir) {
    Get-ChildItem $logDir -Filter "*.pid" | ForEach-Object { Remove-Item $_.FullName -Force }
}

if (Test-Path -Path "${configDir}/buildAgent.properties") {
    Write-Host "File buildAgent.properties was found in ${configDir}"
} else {
    Write-Host "Will create a new buildAgent.properties using distribution"
    if ($env:SERVER_URL) {
        Write-Host "TeamCity URL: ${env:SERVER_URL}"
    } else {
        Write-Host "TeamCity URL was not specified, please use SERVER_URL environment variable for that."
        exit 1
    }
    prepare_conf
    reconfigure
}


&$agentScript start
if ($LastExitCode -ne 0) { throw "Failed to start TeamCity build agent: $LastExitCode" }

if (!(Test-Path -Path "${logDir}/teamcity-agent.log")) {
    Write-Host -NoNewline "Waiting for TeamCity build agent start"
    while(!(Test-Path -Path "${logDir}/teamcity-agent.log")) {
        Write-Host -NoNewline "."
        Start-Sleep -s 1
    }
    Write-Host ""
}

Trap {
    &$agentScript stop force
    exit 0
}

Get-Content -Path "${logDir}/teamcity-agent.log" -Wait
