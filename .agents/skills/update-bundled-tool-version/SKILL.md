---
name: update-bundled-tool-version
description: Use when bumping the version of a bundled tool (Git, Git LFS, JDK/Corretto, Docker, containerd, .NET, Mercurial, Perforce) in the teamcity-docker-images repo — editing configs/*.config, fetching the correct upstream checksum, regenerating Dockerfiles with generate.sh, and staging the result.
---

# Update a Bundled Tool Version

## Overview

Bundled tool versions are **not** hand-edited in Dockerfiles. They live in
`configs/*.config` as the single source of truth; the Dockerfiles under
`context/generated/` and `generated/` are produced from them by a generator.

**Core principle:** edit the config, pull the matching checksum from the
tool's upstream source, then regenerate — never edit a generated Dockerfile
by hand.

## Where versions live

| File | Platform | Tools |
|------|----------|-------|
| `configs/common.config` | Cross-platform (shared) | Shared version variables referenced by the OS configs via `${...}` — currently `.NET` (`dotnetComponentVersion`) |
| `configs/linux.config` | Linux (amd64/ARM) | JDK (Corretto), .NET, Git, Git LFS, Docker, containerd, Perforce |
| `configs/windows.config` | Windows | Git (MinGit), JDK (Corretto), .NET, Mercurial |

A version defined in `common.config` (e.g. `dotnetComponentVersion`) is the
single value for every platform; bump it once there and both OS configs pick it
up. The OS-specific checksum lines (`...SHA512` etc.) still live in `linux.config`
/ `windows.config` and must be updated alongside it.

Each tool block carries, in order:
1. One or more `#` comment lines with the **upstream source URL(s)** — this is where you verify the new version and its checksum.
2. The version and/or download URL.
3. An optional checksum line (`...SHA256`, `...SHA512`, `...MD5SUM`).
4. A `...Name` line whose display string embeds the version (and sometimes `${...Checksum}` by variable reference).

**Every place the version string appears in the block must be updated**: URL, checksum, and the human-readable `...Name`.

## Checksum source per tool (the important part)
The checksum is tool-specific. Use the upstream source, not a guess:

| Tool | Version form | Checksum from |
|------|-------------|---------------|
| **Git (Windows / MinGit)** | GitHub release tag `vX.Y.Z.windows.N` | The release body's SHA-256 table. First build drops the trailing `.N` in the filename: `MinGit-2.56.1-64-bit.zip` for `.windows.1`, but `MinGit-2.56.0.4-64-bit.zip` for build 4. Verify both the asset name and hash: `gh release view <tag> --repo git-for-windows/git --json assets,body` |
| **Git (Linux)** | `1:X.Y.Z-0ppa1~ubuntuNN.NN.N` from the git-core PPA | No separate checksum line — apt pins the version. Confirm the exact version string exists at https://launchpad.net/~git-core/+archive/ubuntu/ppa |
| **Git LFS (Linux)** | `vX.Y.Z` | Version only, installed via apt |
| **JDK (Corretto)** | `X.Y.Z.P.Q` | **MD5** from the Corretto release / download page |
| **.NET** | `${dotnetComponentVersion}` (shared var in common config) | **SHA512** from the dotnet download page |
| **Docker (Linux)** | `5:X.Y.Z-1~ubuntu` | apt-pinned, no checksum |
| **containerd (Linux)** | `X.Y.Z-1~ubuntu.24.04~noble` | apt-pinned, no checksum |

## Procedure

1. **Find the tool block** in the relevant `configs/*.config`(s). A tool may appear in both `linux.config` and `windows.config` (e.g. Git) — update both.

2. **Verify the new version at the upstream source** (the `#` comment URL).
   Fetch the exact version string and the matching checksum. Use the GitHub API so you get the exact asset filename and hash for a tool hosted there when applicable.

3. **Edit the config** — change the version/URL, the checksum, and the `...Name`
   line. Change only the version-bearing values; don't reformat the block.

4. **Regenerate**. The generator uses Docker, so confirm the engine is actually
   reachable first:
   ```bash
   docker ps          # must exit 0 with a container table, not an error
   ./generate.sh
   ```
   `./generate.sh` builds the .NET generator tool, wipes `generated/` and
   `context/generated/`, and regenerates both trees plus `.teamcity/generated`.

5. **Verify the bump landed** in the generated output before staging:
   ```bash
   git diff -- configs context/generated | grep -iE 'MinGit|2\.56|Corretto' | sort -u
   ```
   Expect to see the new version in the `ARG`/`gitWindowsComponent` lines and in
   the `- Git v.X.Y.Z` doc lines.

6. **Stage** configs plus the regenerated Dockerfiles and `.md` docs. The `.md`
   files sit directly in `context/generated/` (not in subdirs), so a
   `**/*.md` pathspec misses them — list them explicitly:
   ```bash
   git add configs/linux.config configs/windows.config
   find context/generated -name Dockerfile -print0 | xargs -0 git add
   git add context/generated/*.md
   ```

## Common mistakes

- **Editing a generated Dockerfile directly** — it's overwritten on the next `generate.sh`. Always edit the config.
- **Updating the URL but not the `...Name`** — leaves a stale version in the generated docs/labels.
- **Assuming the MinGit filename follows the tag** — `.windows.1` → filename has no trailing build number; later builds do. Check the actual asset list.
- **Using the wrong checksum algorithm** — Corretto is MD5, .NET is SHA512, Git-for-Windows is SHA256. Copying a SHA256 into an MD5 field breaks the build.
- **Running `generate.sh` with a dead Docker engine** — produces a partial or failed `generated/` tree. Gate on `docker ps` first.
- **`git add context/generated/**/*.md`** — fails/misses; the `.md` files are at the top level of `context/generated/`.
