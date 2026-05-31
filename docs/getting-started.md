---
layout: page
title: Getting Started
---

Magic requires Java 17 or later.

## One-Line Install

Linux:

```bash
mkdir -p "$HOME/.magic" && curl -fsSL https://github.com/Yeqi99/Magic/releases/download/latest/magic-cli.tar.gz | tar -xz -C "$HOME/.magic" --strip-components=1 && { grep -qxF 'export PATH="$HOME/.magic/bin:$PATH"' "$HOME/.bashrc" 2>/dev/null || echo 'export PATH="$HOME/.magic/bin:$PATH"' >> "$HOME/.bashrc"; } && export PATH="$HOME/.magic/bin:$PATH" && magic repl
```

macOS:

```bash
mkdir -p "$HOME/.magic" && curl -fsSL https://github.com/Yeqi99/Magic/releases/download/latest/magic-cli.tar.gz | tar -xz -C "$HOME/.magic" --strip-components=1 && { grep -qxF 'export PATH="$HOME/.magic/bin:$PATH"' "$HOME/.zshrc" 2>/dev/null || echo 'export PATH="$HOME/.magic/bin:$PATH"' >> "$HOME/.zshrc"; } && export PATH="$HOME/.magic/bin:$PATH" && magic repl
```

Windows PowerShell:

```powershell
$m="$HOME\.magic"; $t=Join-Path $env:TEMP "magic-install"; Remove-Item $t -Recurse -Force -ErrorAction SilentlyContinue; New-Item -ItemType Directory -Force $m,$t | Out-Null; Invoke-WebRequest "https://github.com/Yeqi99/Magic/releases/download/latest/magic-cli.zip" -OutFile "$t\magic-cli.zip"; Expand-Archive "$t\magic-cli.zip" -DestinationPath $t -Force; Copy-Item "$t\Magic\*" $m -Recurse -Force; $bin="$m\bin"; $path=[Environment]::GetEnvironmentVariable("Path","User"); if (($path -split ';') -notcontains $bin) { [Environment]::SetEnvironmentVariable("Path", "$bin;$path", "User") }; $env:Path="$bin;$env:Path"; magic repl
```

## CLI

```bash
magic repl
magic run script.m
magic eval "print(hello world)"
magic env create dev
magic env use dev
magic env list
magic http --port 22329
```

Magic envs isolate working context under `~/.magic/envs/<name>`. Each env has its own `spells/` directory, which can be imported when the CLI starts.

## REPL

```text
magic(default:0)> vdef(name str(Magic))
added #1
magic(default:1)> print(name)
added #2
magic(default:2)> :run
```

Useful REPL commands:

```text
:run, :go                 Run the current buffer
:eval <words>             Execute one Magic line
:buffer, :look            Show the current buffer
:save <name> [directory]  Save the buffer as .m
:context                  Show variables and objects
:functions [filter]       Show registered semantics
:env list|create|use|path Manage envs
:exit                     Exit
```
