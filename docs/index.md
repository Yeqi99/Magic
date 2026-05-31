---
layout: home
title: Magic
---

<p align="center">
  <img src="logo.png" alt="Magic logo" width="720">
</p>

> **Magic turns software capabilities into compact, machine-readable semantics that humans can write, agents can understand, and runtimes can safely execute.**

Magic is an embeddable Java semantic execution layer. It keeps the language core small and lets host projects expose their own abilities as compact semantic functions.

## Why Magic Exists

Magic is designed for software systems that need a small, machine-readable scripting layer instead of a large general-purpose language surface.

- Humans can write short semantic scripts.
- Agents can inspect and maintain those scripts.
- Runtimes can execute scripts with host-controlled capabilities.
- Third-party projects can register domain semantics without changing the core language.

## Proof Of Use

[MagicPaper](https://github.com/Yeqi99/MagicPaper) is the main proof-of-use integration. It connects Magic to Paper / Folia Minecraft servers and exposes players, items, GUI, timers, triggers, cooldowns, buffs, economy, permissions, and plugin hooks as Magic semantics.

MagicRedis is another practical application example that validates Magic outside Minecraft.

## Documentation

- [Getting Started](getting-started.html): install Magic, run the CLI, use envs, and start the REPL.
- [Semantic Catalog](semantics.html): current semantic areas and the catalog direction.
- [MagicPaper](magicpaper.html): Minecraft integration and example scripts.
- [Roadmap](roadmap.html): semantic catalog, concurrency safety, and agent-readable metadata.

## Repository

- Magic core: [github.com/Yeqi99/Magic](https://github.com/Yeqi99/Magic)
- MagicPaper integration: [github.com/Yeqi99/MagicPaper](https://github.com/Yeqi99/MagicPaper)
