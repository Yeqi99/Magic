---
layout: page
title: Semantic Catalog
---

Magic expresses behavior through semantic functions such as `print(hello)`, `vdef(name str(Magic))`, or host-provided calls such as MagicPaper's `stap(...)`.

## Core Areas

The current core semantics cover:

- arithmetic, comparison, and logic;
- variables, objects, and context access;
- lists, sets, maps, argument collections, random selection, and unpacking;
- `if`, `ifNot`, `while`, iteration, return, break, jump, skip, and wait;
- `Spell` execution, eval, context loading, async execution, and daemon async execution;
- console input, print, import, time, type, length, ranges, and null checks;
- Java reflection, aliases, custom functions, list sorting, and string splitting.

## Host Semantics

Host projects register their own domain functions into `MagicManager`.

Examples:

- MagicPaper registers Minecraft semantics such as players, items, GUI, triggers, timers, economy, permissions, and plugin hooks.
- MagicRedis demonstrates Magic as an application-level semantic layer outside Minecraft.

## Agent-Readable Direction

The next catalog goal is runtime-exported metadata:

- function name and aliases;
- argument forms and return types;
- side effects and safety notes;
- short examples;
- host package ownership.

This will let agents discover what a runtime can do before generating or modifying Magic scripts.
