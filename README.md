<p align="center">
  <img src="logo.png" alt="Magic logo" width="720">
</p>

# Magic

> **Magic turns software capabilities into compact, machine-readable semantics that humans can write, agents can understand, and runtimes can safely execute.**
>
> **Magic 将软件能力转化为紧凑、机器可读的语义：人可以书写，智能体可以理解，运行时可以安全执行。**

[English](README_EN.md) | [中文](README.md) | [Docs](https://yeqi99.github.io/Magic/)

MagicPaper proof-of-use integration: [Yeqi99/MagicPaper](https://github.com/Yeqi99/MagicPaper)

Magic 是一个面向接入方的 Java 脚本语言内核。它的设计目标不是把语法做得像传统编程语言一样华丽，而是尽量做到“0 代码糖”：把表达能力交给语义函数，把控制流、变量、容器、环境能力都抽象成可注册、可替换、可组合的语义。

换句话说，Magic 希望在同样的文本量里存储更多可执行逻辑，并让第三方项目可以快速把自己的业务能力接入为一组短语义。Minecraft 方向的 [MagicPaper](https://github.com/Yeqi99/MagicPaper) 是当前最完整的落地实现；MagicRedis 则是 Magic 在实际应用场景中的另一个范例。

## 当前实现

Magic 基于 Java 17 和 Maven 构建。

Magic 当前已经实现了一个可嵌入的解释执行核心：

- `MagicManager`：语义注册与执行管理器，维护函数、别名和语义优先级。
- `FastExpression` / `FunctionManager` / `AliasesManager`：负责解析 `name(args)` 形式的表达式、函数注册和别名映射。
- `Spell`：一组 Magic 语句，按行顺序执行。
- `MagicWords`：一行 Magic 语句，一行中可以包含多个语义函数。
- `ContextMap` / `NormalContext`：运行时上下文，用于存储变量、公共对象和执行状态。
- `SpellContext`：单次执行过程中的上下文，记录返回值、错误、打印日志、跳转与控制信息。
- `MagicPackage` / `MagicInstance`：加载 `.m` 文件，并将文件中的魔咒导入到上下文。

内置语义覆盖了脚本语言的基础能力：

- 算术：加、减、乘、除、取模、幂、整除、自计算。
- 比较与逻辑：比较、相等、忽略大小写相等、与、或、异或、非。
- 变量：变量定义、对象定义、读取、写入、存在性判断、上下文读取。
- 容器：列表、集合、映射、参数集合、随机取值、解包。
- 控制流：`if`、`ifNot`、`while`、遍历、返回、中断、跳转、跳过、等待。
- 执行：执行 Spell、执行带参数 Spell、加载上下文、eval、异步执行、守护异步执行。
- 输入输出：控制台输入、打印、导入。
- 信息：执行次数、当前语句序号、长度、类型、时间、范围、自身引用、空值判断。
- 扩展：Java 类反射、别名注册、自定义函数注册、列表排序、字符串拆分。

## 基本语法

Magic 的基本单元是语义函数：

```magic
print(hello world)
```

参数默认按空格拆分；嵌套函数会先被解析并返回结果：

```magic
vdef(name str(Magic))
print(name)
print(add(1 2))
```

一组语句组成一个 `Spell`：

```magic
vdef(playerName str(Yeqi))
print(playerName)
return(playerName)
```

Magic 的重点是“语义优先”。例如在 Minecraft 中，`player()`、`item()`、`stap()` 这类能力不是语言内核写死的，而是由 MagicPaper 注册进来的环境语义。

## 在 Java 中接入

最小接入方式如下：

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

第三方接入通常只需要做三件事：

1. 创建并持有一个 `MagicManager`。
2. 调用 `FunctionRegister.regDefault(manager)` 注册系统语义。
3. 继承 `ArgsFunction` 或 `OnlyStringFunction`，把自己的环境能力注册成语义函数。

函数注册示例：

```java
manager.registerFunction(new YourFunction(), "alias1", "alias2");
```

如果你的项目只希望复用 Magic 的执行模型，也可以只注册自己的语义，不启用默认系统语义。

## 命令行 CLI

Magic 提供一个命令行入口 `magic`。它既可以像脚本解释器一样运行 `.m` 文件，也可以进入交互式调试台，还可以创建类似 Python 虚拟环境体验的 Magic env。

常用命令：

```bash
magic repl
magic run script.m
magic eval "print(hello world)"
magic env create dev
magic env use dev
magic env list
magic http --port 22329
```

全局选项：

```bash
magic repl --env dev
magic run script.m --env dev
magic eval "print(hello)" --no-import
magic env home
```

Magic env 是一个独立的工作上下文目录，默认位于 `~/.magic/envs/<name>`。每个 env 都有自己的 `spells/` 目录，CLI 启动时会把该目录下的 `.m` 文件导入为 `env.<envName>.<fileName>` 变量。这样可以像 Python 的虚拟环境一样，为不同项目、服务器或实验隔离上下文。

## 终端调试台

进入 REPL：

```bash
magic repl
```

在 REPL 中，直接输入 Magic 语句会加入当前缓冲区：

```text
magic(default:0)> vdef(name str(Magic))
added #1
magic(default:1)> print(name)
added #2
magic(default:2)> :run
```

REPL 命令以 `:` 开头，也兼容旧调试台的 `/` 前缀：

```text
:run, :go                 执行当前缓冲区
:run <file.m>             执行文件
:eval <words>             立即执行一行 Magic 语句
:buffer, :look            查看当前缓冲区
:clear                    清空缓冲区
:save <name> [directory]  保存缓冲区为 .m 文件，默认保存到当前 env 的 spells/
:import <dir> [id]        导入目录下的 .m 文件为 id.* 变量
:reset                    重置上下文，并重新导入当前 env 的 spells/
:context                  查看当前上下文变量和对象
:functions [filter]       查看已注册语义
:aliases <function>       查看语义别名
:env list|create|use|path 管理 env
:home                     查看 Magic home
:exit                     退出
```

## 安装方式

安装前需要本机已经有 Java 17 或更高版本。

Linux 一条命令：

```bash
mkdir -p "$HOME/.magic" && curl -fsSL https://github.com/Yeqi99/Magic/releases/download/latest/magic-cli.tar.gz | tar -xz -C "$HOME/.magic" --strip-components=1 && { grep -qxF 'export PATH="$HOME/.magic/bin:$PATH"' "$HOME/.bashrc" 2>/dev/null || echo 'export PATH="$HOME/.magic/bin:$PATH"' >> "$HOME/.bashrc"; } && export PATH="$HOME/.magic/bin:$PATH" && magic repl
```

macOS 一条命令：

```bash
mkdir -p "$HOME/.magic" && curl -fsSL https://github.com/Yeqi99/Magic/releases/download/latest/magic-cli.tar.gz | tar -xz -C "$HOME/.magic" --strip-components=1 && { grep -qxF 'export PATH="$HOME/.magic/bin:$PATH"' "$HOME/.zshrc" 2>/dev/null || echo 'export PATH="$HOME/.magic/bin:$PATH"' >> "$HOME/.zshrc"; } && export PATH="$HOME/.magic/bin:$PATH" && magic repl
```

Windows PowerShell 一条命令：

```powershell
$m="$HOME\.magic"; $t=Join-Path $env:TEMP "magic-install"; Remove-Item $t -Recurse -Force -ErrorAction SilentlyContinue; New-Item -ItemType Directory -Force $m,$t | Out-Null; Invoke-WebRequest "https://github.com/Yeqi99/Magic/releases/download/latest/magic-cli.zip" -OutFile "$t\magic-cli.zip"; Expand-Archive "$t\magic-cli.zip" -DestinationPath $t -Force; Copy-Item "$t\Magic\*" $m -Recurse -Force; $bin="$m\bin"; $path=[Environment]::GetEnvironmentVariable("Path","User"); if (($path -split ';') -notcontains $bin) { [Environment]::SetEnvironmentVariable("Path", "$bin;$path", "User") }; $env:Path="$bin;$env:Path"; magic repl
```

如果命令执行完成后当前终端已经能进入 `magic repl`，以后新开的终端也可以直接使用：

```bash
magic repl
```

开发者也可以从源码构建并安装：

```bash
mvn package
```

Linux / macOS 源码安装：

```bash
sh scripts/install.sh
export PATH="$HOME/.magic/bin:$PATH"
magic repl
```

Windows PowerShell 源码安装：

```powershell
powershell -ExecutionPolicy Bypass -File scripts\install.ps1
$env:Path="$HOME\.magic\bin;$env:Path"
magic repl
```

安装脚本会把 jar 放到 `~/.magic/lib/magic.jar`，并在 `~/.magic/bin` 中生成 `magic` 启动脚本。也可以通过 `MAGIC_HOME` 或安装脚本的 prefix 参数指定安装目录。

不安装也可以直接运行构建产物：

```bash
java -jar target/Magic-*.jar repl
java -jar target/Magic-*.jar run script.m
```

## HTTP 调试入口

如果需要保留外部调用方式，可以启动 HTTP 调试服务：

```bash
magic http --port 22329 --env default
```

发送 `POST /magic`，请求体可以是表单：

```text
Spell=print(hello)&Env=default
```

也可以直接发送纯文本 Magic 语句。HTTP 模式会复用指定 env 的上下文，适合给编辑器、网页调试面板或其他工具调用。

## 发布与自动构建

仓库使用 GitHub Actions 自动构建 Magic：

- push 到 `master` / `main`：自动构建，保存 `magic-latest` 工作流产物，并更新 `latest` Release。
- pull request：自动构建校验。
- 推送 `v*` tag 或发布 GitHub Release：自动构建并上传 Release 资产。

发布一个新版本时，只需要打 tag 并推送：

```bash
git tag vX.Y.Z
git push origin vX.Y.Z
```

自动发布的 Release 资产包括：

```text
magic.jar
magic-cli.zip
magic-cli.tar.gz
SHA256SUMS
```

也可以先在 GitHub 创建 Release；Release 发布后，CI/CD 会自动把最新构建产物上传到该 Release。

## 项目结构

```text
src/main/java/cn/origincraft/magic
  expression/     表达式、函数注册、别名管理
  function/       函数抽象、内置语义、返回值类型
  manager/        .m 包加载与 Magic 实例
  object/         Spell、MagicWords、上下文对象
  utils/          解析、文件、错误、变量、结果等工具

src/test/java
  Test.java       最小执行示例
  SimpleIDE.java  简单 Swing 语义提示实验
  example.m       示例 Magic 语句
```

## 生态位置

- Magic：语言核心与解释执行框架。
- MagicPaper：把 Magic 接入 Paper / Folia 服务器，提供 Minecraft 领域语义。
- MagicRedis：Magic 语言在实际应用中的范例，用于验证语言在非 Minecraft 环境里的可接入性。

Magic 的长期方向不是替代宿主项目的 Java 代码，而是让宿主项目把复杂能力沉淀为语义，最终让使用者用更少文本编排更多逻辑。

## 未来预期

- 继续稳定语法解析、错误提示、上下文隔离和执行安全。
- 为语义函数生成更完整的自描述文档，让接入方和使用者不依赖外部站点也能查到用法。
- 完善语义包、导入机制和第三方扩展约定，降低其他项目接入 Magic 的成本。
- 在 MagicPaper、MagicRedis 等实际项目中反向验证语言设计，保留足够小的核心，避免把环境能力写死进语言内核。
- 在样本足够后探索自然语言到 Magic 语义的转换，让不同语言的用户可以更低门槛地生成 Magic 脚本。

## 开源协议

本项目采用 GNU GPL-3.0 许可证。使用、修改和分发本项目代码时，请遵循 GPL-3.0 的相关要求。
