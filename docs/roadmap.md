---
layout: page
title: Roadmap
---

Magic is moving toward a small semantic execution core that can be embedded by host software, inspected by humans, and consumed by maintenance agents.

## Semantic Catalog

- document every built-in semantic function with aliases, argument forms, return type, side effects, and examples;
- separate core semantics from host-provided semantics such as MagicPaper and MagicRedis;
- export the catalog from runtime state instead of relying only on manually maintained text.

## Concurrency Safety

- define which values are local to one `SpellContext` and which values are shared through a host `ContextMap`;
- document safe patterns for async execution, daemon async execution, HTTP requests, and MCP sessions;
- make env/session isolation explicit so projects, servers, and agents do not leak state into each other.

## Agent-Readable Catalog

- expose semantic metadata as JSON;
- include host capability catalogs, starting with MagicPaper;
- make `magic mcp` able to answer catalog queries without scraping README text;
- provide examples that map user intent to compact Magic scripts.
