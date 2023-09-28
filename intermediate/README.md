# Intermediate Dockerfiles

The folder contains files that will be available intermittently for a specific period or during a transitional phase. 
Specifically, it contains files for images that will feature a different version of the tool compared to the mainline 
image.

| Type               | .NET             | Dockerfile                                                        | Build Documentation                       |
|--------------------|------------------|-------------------------------------------------------------------|-------------------------------------------|
| Linux Agent, AMD64 | .NET Runtime 3.1 | [dotnet3.1.Dockerfile)](linux/agent/dotnet3.1.Dockerfile)         | [Linux Agent (AMD64)](#linux-agent-amd64) |
| Linux Agent, ARM64 | .NET Runtime 3.1 | [dotnet3.1.arm.Dockerfile)](linux/agent/dotnet3.1.arm.Dockerfile) | [Linux Agent (ARM64)](#linux-agent-arm64) |



# .NET 3.1
## Linux Agent (AMD64)
```
docker build -f "linux/agent/dotnet3.1.Dockerfile" -t teamcity-agent:EAP-linux-dotnet-3-1 .
```

Afterwards, .NET 3.1 runtime should appear:
```
$ dotnet --list-runtimes
Microsoft.AspNetCore.App 6.0.21 [/usr/share/dotnet/shared/Microsoft.AspNetCore.App]
Microsoft.AspNetCore.App 7.0.11 [/usr/share/dotnet/shared/Microsoft.AspNetCore.App]
Microsoft.NETCore.App 3.1.32 [/usr/share/dotnet/shared/Microsoft.NETCore.App]
Microsoft.NETCore.App 6.0.21 [/usr/share/dotnet/shared/Microsoft.NETCore.App]
Microsoft.NETCore.App 7.0.11 [/usr/share/dotnet/shared/Microsoft.NETCore.App]
```

## Linux Agent (ARM64)
```
docker build -f "linux/agent/dotnet3.1.arm.Dockerfile" -t teamcity-agent:EAP-linux-dotnet-3-1-arm .
```

