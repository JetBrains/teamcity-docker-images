# agent-upgrade-dist

A build-time utility that prepares the TeamCity build agent distribution for Docker images.

## Overview

This tool extracts agent-side plugins and tools from the TeamCity server distribution and assembles them into the build agent directory structure.

1. Scans the server's plugin directory (`webapps/ROOT/WEB-INF/plugins`) for `.zip` and `.jar` files
2. Identifies agent plugins (`agent/` entries) and bundled tools (`bundled*/` entries) within each archive
3. Unpacks them into the agent's `plugins/` and `tools/` directories respectively
4. Computes SHA-1 hashes for each plugin and the core `buildAgent.zip`
5. Generates `teamcity-agent.xml` (agent metadata) and `unpacked-plugins.xml` (plugin registry) under `system/.teamcity-agent/`

## Usage

```
./gradlew run --args="<serverRootDir> [agentRootDir]"
```

- `serverRootDir` — path to the TeamCity server distribution (e.g., `TeamCity/`)
- `agentRootDir` — (optional) path to the agent directory; defaults to `<serverRootDir>/buildAgent`

## Requirements

- JDK 21

## Build

```
./gradlew build
```
