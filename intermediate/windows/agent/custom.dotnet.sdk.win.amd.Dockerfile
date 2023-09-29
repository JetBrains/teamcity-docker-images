ARG teamCityAgentImage

FROM ${teamCityAgentImage}

# With Windows images, ARG variables become inaccessible after each FROM
ARG dotnetSdkVersion
ARG dotnetSdkChecksum

# PowerShell
SHELL ["powershell", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

# Modify .NET & other paths
USER ContainerAdministrator
RUN setx /M PATH ('{0};{1}\bin;C:\Program Files\Git\cmd;C:\Program Files\Mercurial' -f $env:PATH, $env:JAVA_HOME)

RUN [Net.ServicePointManager]::SecurityProtocol = 'tls12, tls11, tls' ; \
    $code = Get-Content -Path "scripts/Web.cs" -Raw ; \
    Add-Type -IgnoreWarnings -TypeDefinition "$code" -Language CSharp ; \
    $url = 'https://dotnetcli.blob.core.windows.net/dotnet/Sdk/' + $Env:dotnetSdkVersion + '/dotnet-sdk-' + $Env:dotnetSdkVersion + '-win-x64.zip'; \
    $downloadScript = [Scripts.Web]::DownloadFiles($url + '#SHA512#' + $Env:dotnetSdkChecksum, 'dotnet.zip') ; \
     # Remove to successfully expand archive
    Remove-Item -Force -Recurse $Env:ProgramFiles\dotnet; \
    # 2. Extract .NET version
    Expand-Archive dotnet.zip -Force -DestinationPath $Env:ProgramFiles\dotnet; \
    Remove-Item -Force dotnet.zip; \
    Get-ChildItem -Path $Env:ProgramFiles\dotnet -Include *.lzma -File -Recurse | foreach { $_.Delete()};


# SWitch back to regular user
USER ContainerUser
