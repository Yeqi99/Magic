# Magic

Magic 是一个面向接入方的 Java 脚本语言内核。它的设计目标不是把语法做得像传统编程语言一样华丽，而是尽量做到“0 代码糖”：把表达能力交给语义函数，把控制流、变量、容器、环境能力都抽象成可注册、可替换、可组合的语义。

换句话说，Magic 希望在同样的文本量里存储更多可执行逻辑，并让第三方项目可以快速把自己的业务能力接入为一组短语义。Minecraft 方向的 MagicPaper 是当前最完整的落地实现；MagicRedis 则是 Magic 在实际应用场景中的另一个范例。

## 当前实现

当前仓库版本为 `1.2.5-SNAPSHOT`，基于 Java 17 和 Maven 构建。

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

## 构建与运行

构建：

```bash
mvn package
```

构建产物位于 `target/`。当前 `pom.xml` 会使用 shade 插件打包依赖，并把 `cn.origincraft.magic.Magic` 写入 Jar Manifest 的 `Main-Class`。

直接运行主类后，Magic 会读取 `src/main/resources/config.yml` 中的 HTTP 端口配置，默认端口为 `22329`。主类目前包含一个实验性质的命令行与 HTTP 执行入口：

- `/help`：查看命令。
- `/go`：执行当前输入的语句。
- `/clear`：清空当前输入。
- `/look`：查看当前 MagicManager、Context 和已输入语句。
- `/save <name> [path]`：保存当前语句为 `.m` 文件。
- `/import <name> <path>`：导入一个目录下的 `.m` 文件。
- `/context <name>`：切换上下文名。
- `/manager <name>`：切换 MagicManager 名。
- `/exit`：退出。

HTTP 入口为 `POST /magic`，当前主要用于调试和外部实验接入。

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
