---
layout: page
title: MagicPaper
---

[MagicPaper](https://github.com/Yeqi99/MagicPaper) is Magic's proof-of-use integration for Paper / Folia Minecraft servers.

It turns server capabilities into Magic semantics, including:

- player, entity, item, location, potion, particle, bossbar, and component objects;
- GUI menus, dynamic buttons, triggers, and timers;
- cooldowns, buffs, item spells, item attributes, NBT, and lore templates;
- PlaceholderAPI, LuckPerms, Vault, PlayerPoints, MythicMobs, ItemsAdder, ProtocolLib, and other optional hooks;
- third-party API entry points for registering more semantics, triggers, timers, and GUI behavior.

## Example Scripts

MagicPaper keeps practical scripts in its repository:

```text
examples/
  01-onload-broadcast.m
  02-player-join-welcome.m
  03-cooldown-jump.m
  04-open-example-gui.m
  05-give-named-reward.m
```

These scripts show how server behavior can be compressed into compact semantics such as `stap(...)`, `stp(...)`, `gocd(...)`, `buffAdd(...)`, `pjump(...)`, `opengui(...)`, and `igive(...)`.

## Why It Matters

MagicPaper demonstrates the core Magic idea: a host project keeps its Java implementation, but exposes composable behavior as short semantic calls that humans can write and agents can inspect.
