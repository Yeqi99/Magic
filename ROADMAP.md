# Magic Roadmap

Magic is moving toward a small semantic execution core that can be embedded by host software, inspected by humans, and consumed by maintenance agents.

## Semantic Catalog

The next documentation priority is a first-class semantic catalog:

- list every built-in semantic function with aliases, argument forms, return type, side effects, and examples;
- separate core semantics from host-provided semantics such as MagicPaper and MagicRedis;
- make the catalog exportable from runtime state instead of manually maintained text only;
- keep semantic names compact while documenting their full intent for humans and agents.

## Concurrency Safety

Magic needs clear execution boundaries for servers, tools, and long-running automation:

- define which context values are local to one `SpellContext` and which values are shared through a host `ContextMap`;
- document safe patterns for async execution, daemon async execution, HTTP requests, and MCP sessions;
- make env/session isolation explicit so different projects, servers, and agents do not leak state into each other;
- improve error reporting around concurrent mutations, missing variables, and host-side failures.

## Agent-Readable Catalog

The language should be easy for agents to inspect before they write or modify scripts:

- expose semantic metadata as JSON so agents can discover function names, aliases, parameters, examples, and safety notes;
- include host capability catalogs, starting with MagicPaper semantics and practical Minecraft scenarios;
- make `magic mcp` able to answer catalog queries without scraping README text;
- provide examples that map user intent to compact Magic scripts.

## Integration Direction

Magic should stay small. Host projects should register their own domain abilities as semantics instead of pushing environment-specific behavior into the language core.

Near-term proof points:

- MagicPaper: Paper / Folia server semantics for Minecraft automation and plugin orchestration.
- MagicRedis: non-Minecraft application usage that validates Magic as a reusable semantic layer.
- Agent skill package: examples and MCP configuration that show agents how to execute, inspect, and maintain Magic scripts.
