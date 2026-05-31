Magic is a semantic execution layer for agent-friendly software maintenance.

Magic 是一个面向 Agent 友好型软件维护的语义执行层。

# Magic

[English](README_EN.md) | [中文](README.md)

Magic is a Java scripting-language core designed for projects that want to embed their own domain semantics. Its goal is not to imitate a traditional programming language with decorative syntax. The goal is "zero syntax sugar": expression power should come from semantic functions, while control flow, variables, containers, and environment-specific abilities are all registered, replaced, and composed as semantics.

In other words, Magic aims to store more executable logic in the same amount of text, and to let third-party projects quickly expose their own capabilities as short semantic phrases. MagicPaper is the most complete Minecraft-oriented implementation, connecting Magic to Paper / Folia server behavior. MagicRedis is another practical example of Magic used in an application scenario.

## Implementation

Magic is built with Java 17 and Maven.

The project already provides an embeddable interpretation core:

- `MagicManager`: semantic registration and execution manager for functions, aliases, and semantic priority.
- `FastExpression` / `FunctionManager` / `AliasesManager`: parsing for `name(args)` expressions, function registration, and alias mapping.
- `Spell`: a group of Magic statements executed line by line.
- `MagicWords`: one line of Magic statements, which may contain multiple semantic functions.
- `ContextMap` / `NormalContext`: runtime context for variables, shared objects, and execution state.
- `SpellContext`: per-execution context that records return values, errors, printed output, jumps, and control signals.
- `MagicPackage` / `MagicInstance`: loaders for `.m` files that import spells into a context.

Built-in semantics cover the basic capabilities expected from a scripting language:

- Arithmetic: add, subtract, multiply, divide, modulo, power, integer division, and self-calculation.
- Comparison and logic: comparison, equality, case-insensitive equality, and, or, xor, and not.
- Variables: variable definition, object definition, read, write, existence checks, and context reads.
- Containers: lists, sets, maps, argument collections, random selection, and unpacking.
- Control flow: `if`, `ifNot`, `while`, iteration, return, break, jump, skip, and wait.
- Execution: run Spell, run Spell with arguments, load context, eval, async execution, and daemon async execution.
- Input and output: console input, print, and import.
- Information: execution count, current statement index, length, type, time, ranges, self references, and null checks.
- Extension: Java class reflection, alias registration, custom function registration, list sorting, and string splitting.

## Basic Syntax

The basic unit of Magic is a semantic function:

```magic
print(hello world)
```

Arguments are split by spaces by default. Nested functions are evaluated first and return their results:

```magic
vdef(name str(Magic))
print(name)
print(add(1 2))
```

A group of statements forms a `Spell`:

```magic
vdef(playerName str(Yeqi))
print(playerName)
return(playerName)
```

Magic is designed around semantic priority. In Minecraft, for example, abilities such as `player()`, `item()`, and `stap()` are not hard-coded into the language core. MagicPaper registers those environment semantics from the outside.

## Embedding In Java

A minimal integration looks like this:

```java
import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.FunctionRegister;
import cn.origincraft.magic.object.NormalContext;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

MagicManager manager = new MagicManager();
FunctionRegister.regDefault(manager);

NormalContext context = new NormalContext();
Spell spell = new Spell(List.of(
        "vdef(name str(Magic))",
        "print(name)",
        "return(name)"
), manager);

SpellContext result = spell.execute(context);
```

Third-party integrations usually need three steps:

1. Create and keep a `MagicManager`.
2. Call `FunctionRegister.regDefault(manager)` to register system semantics.
3. Extend `ArgsFunction` or `OnlyStringFunction`, then register environment abilities as semantic functions.

Function registration example:

```java
manager.registerFunction(new YourFunction(), "alias1", "alias2");
```

If your project only wants to reuse Magic's execution model, you can register only your own semantics and skip the default system semantics.

## Command Line CLI

Magic provides a command line entry named `magic`. It can run `.m` files like a script interpreter, open an interactive debugging console, and create Magic envs that feel similar to Python virtual environments.

Common commands:

```bash
magic repl
magic run script.m
magic eval "print(hello world)"
magic env create dev
magic env use dev
magic env list
magic http --port 22329
```

Global options:

```bash
magic repl --env dev
magic run script.m --env dev
magic eval "print(hello)" --no-import
magic env home
```

A Magic env is an isolated working-context directory. By default, envs live under `~/.magic/envs/<name>`. Each env has its own `spells/` directory. When the CLI starts, `.m` files in that directory are imported as `env.<envName>.<fileName>` variables. This makes it possible to isolate contexts for different projects, servers, or experiments in a way that feels similar to Python virtual environments.

## Terminal Debug Console

Start the REPL:

```bash
magic repl
```

Inside the REPL, plain Magic statements are appended to the current buffer:

```text
magic(default:0)> vdef(name str(Magic))
added #1
magic(default:1)> print(name)
added #2
magic(default:2)> :run
```

REPL commands start with `:`. The old `/` prefix is also supported:

```text
:run, :go                 Run the current buffer
:run <file.m>             Run a file
:eval <words>             Execute one Magic line immediately
:buffer, :look            Show the current buffer
:clear                    Clear the buffer
:save <name> [directory]  Save the buffer as a .m file, defaulting to the current env's spells/
:import <dir> [id]        Import .m files under a directory as id.* variables
:reset                    Reset context and re-import the current env's spells/
:context                  Show current context variables and objects
:functions [filter]       Show registered semantics
:aliases <function>       Show aliases for a semantic function
:env list|create|use|path Manage envs
:home                     Show Magic home
:exit                     Exit
```

## Installation

Java 17 or later must be available before installing Magic.

Linux one-line install:

```bash
mkdir -p "$HOME/.magic" && curl -fsSL https://github.com/Yeqi99/Magic/releases/download/latest/magic-cli.tar.gz | tar -xz -C "$HOME/.magic" --strip-components=1 && { grep -qxF 'export PATH="$HOME/.magic/bin:$PATH"' "$HOME/.bashrc" 2>/dev/null || echo 'export PATH="$HOME/.magic/bin:$PATH"' >> "$HOME/.bashrc"; } && export PATH="$HOME/.magic/bin:$PATH" && magic repl
```

macOS one-line install:

```bash
mkdir -p "$HOME/.magic" && curl -fsSL https://github.com/Yeqi99/Magic/releases/download/latest/magic-cli.tar.gz | tar -xz -C "$HOME/.magic" --strip-components=1 && { grep -qxF 'export PATH="$HOME/.magic/bin:$PATH"' "$HOME/.zshrc" 2>/dev/null || echo 'export PATH="$HOME/.magic/bin:$PATH"' >> "$HOME/.zshrc"; } && export PATH="$HOME/.magic/bin:$PATH" && magic repl
```

Windows PowerShell one-line install:

```powershell
$m="$HOME\.magic"; $t=Join-Path $env:TEMP "magic-install"; Remove-Item $t -Recurse -Force -ErrorAction SilentlyContinue; New-Item -ItemType Directory -Force $m,$t | Out-Null; Invoke-WebRequest "https://github.com/Yeqi99/Magic/releases/download/latest/magic-cli.zip" -OutFile "$t\magic-cli.zip"; Expand-Archive "$t\magic-cli.zip" -DestinationPath $t -Force; Copy-Item "$t\Magic\*" $m -Recurse -Force; $bin="$m\bin"; $path=[Environment]::GetEnvironmentVariable("Path","User"); if (($path -split ';') -notcontains $bin) { [Environment]::SetEnvironmentVariable("Path", "$bin;$path", "User") }; $env:Path="$bin;$env:Path"; magic repl
```

If the command opens `magic repl`, future terminals can also run:

```bash
magic repl
```

Developers can build and install from source:

```bash
mvn package
```

Linux / macOS source install:

```bash
sh scripts/install.sh
export PATH="$HOME/.magic/bin:$PATH"
magic repl
```

Windows PowerShell source install:

```powershell
powershell -ExecutionPolicy Bypass -File scripts\install.ps1
$env:Path="$HOME\.magic\bin;$env:Path"
magic repl
```

The install scripts place the jar at `~/.magic/lib/magic.jar` and generate launch scripts under `~/.magic/bin`. The install directory can also be customized through `MAGIC_HOME` or the install script prefix argument.

You can also run the build artifact directly without installing:

```bash
java -jar target/Magic-*.jar repl
java -jar target/Magic-*.jar run script.m
```

## HTTP Debug Endpoint

If external tooling needs a stable entry point, start the HTTP debug service:

```bash
magic http --port 22329 --env default
```

Send `POST /magic`. The request body can be a form:

```text
Spell=print(hello)&Env=default
```

Plain text Magic statements are also accepted. HTTP mode reuses the selected env context, which makes it suitable for editors, web debug panels, and other tools.

## Build And Release

The repository uses GitHub Actions to build Magic automatically:

- Pushes to `master` / `main`: build automatically, store the `magic-latest` workflow artifact, and update the `latest` Release.
- Pull requests: build for validation.
- `v*` tags or published GitHub Releases: build and upload Release assets automatically.

To publish a release, create and push a tag:

```bash
git tag vX.Y.Z
git push origin vX.Y.Z
```

Automatically uploaded Release assets:

```text
magic.jar
magic-cli.zip
magic-cli.tar.gz
SHA256SUMS
```

You can also create a GitHub Release first. After the Release is published, CI/CD uploads the latest build artifacts to that Release.

## Project Structure

```text
src/main/java/cn/origincraft/magic
  expression/     Expressions, function registration, alias management
  function/       Function abstractions, built-in semantics, return value types
  manager/        .m package loading and Magic instances
  object/         Spell, MagicWords, and context objects
  utils/          Parsing, files, errors, variables, results, and related utilities

src/test/java
  Test.java       Minimal execution example
  SimpleIDE.java  Simple Swing semantic-hint experiment
  example.m       Example Magic statements
```

## Ecosystem

- Magic: language core and interpretation framework.
- MagicPaper: connects Magic to Paper / Folia servers and provides Minecraft domain semantics.
- MagicRedis: a practical example of Magic in an application, used to validate integration outside Minecraft.

The long-term direction of Magic is not to replace the host project's Java code. It is to let host projects condense complex capabilities into semantics, so users can compose more logic with less text.

## Future Direction

- Keep stabilizing syntax parsing, error reporting, context isolation, and execution safety.
- Generate fuller self-describing documentation for semantic functions, so integrators and users can understand usage without relying on an external site.
- Improve semantic packages, import rules, and third-party extension conventions to reduce integration cost.
- Use real projects such as MagicPaper and MagicRedis to validate the language design, while keeping the core small and avoiding hard-coded environment behavior.
- Explore natural-language-to-Magic conversion after enough examples exist, so users of different languages can generate Magic scripts with a lower barrier.

## License

This project is licensed under GNU GPL-3.0. Please follow the GPL-3.0 requirements when using, modifying, or distributing this project.
