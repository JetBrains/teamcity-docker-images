
ARG dotnetWindowsComponent_70
ARG dotnetWindowsComponentSHA512_70

FROM ${windowsservercoreImage}


RUN [Net.ServicePointManager]::SecurityProtocol = 'tls12, tls11, tls' ; \
        $code = Get-Content -Path "scripts/Web.cs" -Raw ; \
        Add-Type -IgnoreWarnings -TypeDefinition "$code" -Language CSharp ; \
        # 1. Download .NET version
        $downloadScript = [Scripts.Web]::DownloadFiles($Env:dotnetWindowsComponent_70 + '#SHA512#' + $Env:dotnetWindowsComponentSHA512_70, 'dotnet_70.zip') ; \
        Remove-Item -Force -Recurse $Env:ProgramFiles\dotnet; \

        # 2. Extract .NET version
        Expand-Archive dotnet_70.zip -Force -DestinationPath $Env:ProgramFiles\dotnet; \
        Remove-Item -Force dotnet_70.zip; \
        Get-ChildItem -Path $Env:ProgramFiles\dotnet -Include *.lzma -File -Recurse | foreach { $_.Delete()};

USER ContainerAdministrator
RUN setx /M PATH ('{0};{1}\bin;C:\Program Files\Git\cmd;C:\Program Files\Mercurial' -f $env:PATH, $env:JAVA_HOME)
USER ContainerUser
