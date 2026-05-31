package cn.origincraft.magic.cli;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.FunctionRegister;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.manager.MagicPackage;
import cn.origincraft.magic.object.NormalContext;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.ErrorUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class MagicCli {
    private static final String DEFAULT_ENV = "default";
    private static final int DEFAULT_HTTP_PORT = 22329;

    public int run(String[] args) throws IOException {
        ParsedArgs parsed = ParsedArgs.parse(args);
        if (parsed.help) {
            printUsage(System.out);
            return 0;
        }

        EnvironmentStore store = new EnvironmentStore(parsed.home);
        store.ensureHome();

        List<String> positional = parsed.positionals;
        String command = positional.isEmpty() ? "repl" : positional.get(0).toLowerCase(Locale.ROOT);
        return switch (command) {
            case "repl", "shell" -> runRepl(store, parsed);
            case "run" -> runFile(store, parsed, positional);
            case "eval", "exec" -> runEval(store, parsed, positional);
            case "env" -> runEnvCommand(store, parsed, positional);
            case "http", "server" -> runHttp(store, parsed);
            case "help" -> {
                printUsage(System.out);
                yield 0;
            }
            default -> {
                System.err.println("Unknown command: " + command);
                System.err.println("Run `magic help` for usage.");
                yield 1;
            }
        };
    }

    private int runRepl(EnvironmentStore store, ParsedArgs parsed) throws IOException {
        MagicEnvironment env = store.resolveEnvironment(parsed.envName);
        MagicSession session = MagicSession.create(env, !parsed.noImport);
        return new MagicRepl(store, session, parsed.noImport).run();
    }

    private int runFile(EnvironmentStore store, ParsedArgs parsed, List<String> positional) throws IOException {
        if (positional.size() < 2) {
            System.err.println("Usage: magic run <file.m|-> [--env name]");
            return 1;
        }
        List<String> lines = readMagicLines(positional.get(1));
        MagicSession session = MagicSession.create(store.resolveEnvironment(parsed.envName), !parsed.noImport);
        return executeAndReport(session, lines, System.out, System.err);
    }

    private int runEval(EnvironmentStore store, ParsedArgs parsed, List<String> positional) throws IOException {
        if (positional.size() < 2) {
            System.err.println("Usage: magic eval <magic words|-> [--env name]");
            return 1;
        }
        String code = positional.get(1).equals("-")
                ? readStdin()
                : String.join(" ", positional.subList(1, positional.size()));
        List<String> lines = splitLines(code);
        MagicSession session = MagicSession.create(store.resolveEnvironment(parsed.envName), !parsed.noImport);
        return executeAndReport(session, lines, System.out, System.err);
    }

    private int runEnvCommand(EnvironmentStore store, ParsedArgs parsed, List<String> positional) throws IOException {
        String subCommand = positional.size() < 2 ? "current" : positional.get(1).toLowerCase(Locale.ROOT);
        switch (subCommand) {
            case "current" -> {
                System.out.println(store.getActiveEnvName());
                return 0;
            }
            case "list", "ls" -> {
                String active = store.getActiveEnvName();
                for (String name : store.listEnvironmentNames()) {
                    String mark = name.equals(active) ? "*" : " ";
                    System.out.println(mark + " " + name);
                }
                return 0;
            }
            case "create", "new" -> {
                if (positional.size() < 3) {
                    System.err.println("Usage: magic env create <name>");
                    return 1;
                }
                MagicEnvironment env = store.createEnvironment(positional.get(2));
                System.out.println("Created env: " + env.name());
                System.out.println(env.path());
                return 0;
            }
            case "use", "activate" -> {
                if (positional.size() < 3) {
                    System.err.println("Usage: magic env use <name>");
                    return 1;
                }
                MagicEnvironment env = store.resolveEnvironment(positional.get(2));
                store.setActiveEnvName(env.name());
                System.out.println("Using env: " + env.name());
                return 0;
            }
            case "remove", "delete", "rm" -> {
                if (positional.size() < 3) {
                    System.err.println("Usage: magic env remove <name>");
                    return 1;
                }
                store.removeEnvironment(positional.get(2));
                System.out.println("Removed env: " + positional.get(2));
                return 0;
            }
            case "path" -> {
                String envName = positional.size() >= 3 ? positional.get(2) : parsed.envName;
                System.out.println(store.resolveEnvironment(envName).path());
                return 0;
            }
            case "home" -> {
                System.out.println(store.home());
                return 0;
            }
            default -> {
                System.err.println("Unknown env command: " + subCommand);
                return 1;
            }
        }
    }

    private int runHttp(EnvironmentStore store, ParsedArgs parsed) throws IOException {
        String envName = store.resolveEnvironment(parsed.envName).name();
        SessionCache sessions = new SessionCache(store, parsed.noImport);
        sessions.get(envName);

        HttpServer server = HttpServer.create(new InetSocketAddress(parsed.port), 0);
        server.createContext("/magic", new MagicHttpHandler(sessions, envName));
        server.setExecutor(null);
        server.start();
        System.out.println("Magic HTTP server listening on http://127.0.0.1:" + parsed.port + "/magic");
        System.out.println("Default env: " + envName);
        System.out.println("Press Ctrl+C to stop.");
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            server.stop(0);
            Thread.currentThread().interrupt();
        }
        return 0;
    }

    private static int executeAndReport(MagicSession session, List<String> lines, PrintStream out, PrintStream err) {
        SpellContext spellContext = session.execute(lines);
        if (spellContext.hasExecuteError()) {
            err.println(ErrorUtils.stringError(spellContext));
            return 1;
        }

        FunctionResult result = spellContext.getSpellReturn();
        if (result != null && !(result instanceof NullResult)) {
            out.println("return: " + result);
        }
        return 0;
    }

    private static List<String> readMagicLines(String file) throws IOException {
        if ("-".equals(file)) {
            return splitLines(readStdin());
        }
        return Files.readAllLines(resolveUserPath(file), StandardCharsets.UTF_8);
    }

    private static String readStdin() throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append(System.lineSeparator());
            }
        }
        return builder.toString();
    }

    private static List<String> splitLines(String code) {
        String[] lines = code.split("\\R");
        List<String> result = new ArrayList<>();
        for (String line : lines) {
            if (!line.isBlank()) {
                result.add(line);
            }
        }
        return result;
    }

    private static Path resolveUserPath(String value) {
        if (value.equals("~")) {
            return Paths.get(System.getProperty("user.home"));
        }
        if (value.startsWith("~/") || value.startsWith("~\\")) {
            return Paths.get(System.getProperty("user.home")).resolve(value.substring(2));
        }
        return Paths.get(value).toAbsolutePath().normalize();
    }

    private static void printUsage(PrintStream out) {
        out.println("Magic CLI");
        out.println();
        out.println("Usage:");
        out.println("  magic repl [--env name]");
        out.println("  magic run <file.m|-> [--env name]");
        out.println("  magic eval <magic words|-> [--env name]");
        out.println("  magic env list|current|create|use|remove|path|home");
        out.println("  magic http [--port 22329] [--env name]");
        out.println();
        out.println("Common options:");
        out.println("  -e, --env <name>     Select a Magic env/context");
        out.println("  --home <path>        Override MAGIC_HOME");
        out.println("  --no-import          Do not import .m files from the env spells folder");
        out.println("  -h, --help           Show this help");
        out.println();
        out.println("Inside REPL, use :help for interactive commands.");
    }

    static class ParsedArgs {
        private final Path home;
        private final String envName;
        private final int port;
        private final boolean noImport;
        private final boolean help;
        private final List<String> positionals;

        private ParsedArgs(Path home, String envName, int port, boolean noImport, boolean help, List<String> positionals) {
            this.home = home;
            this.envName = envName;
            this.port = port;
            this.noImport = noImport;
            this.help = help;
            this.positionals = positionals;
        }

        static ParsedArgs parse(String[] args) {
            Path home = null;
            String envName = null;
            int port = DEFAULT_HTTP_PORT;
            boolean noImport = false;
            boolean help = false;
            List<String> positionals = new ArrayList<>();

            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                switch (arg) {
                    case "-h", "--help" -> help = true;
                    case "-e", "--env" -> envName = requireValue(args, ++i, arg);
                    case "--home" -> home = resolveUserPath(requireValue(args, ++i, arg));
                    case "-p", "--port" -> port = Integer.parseInt(requireValue(args, ++i, arg));
                    case "--no-import" -> noImport = true;
                    default -> positionals.add(arg);
                }
            }

            return new ParsedArgs(home, envName, port, noImport, help, positionals);
        }

        private static String requireValue(String[] args, int index, String option) {
            if (index >= args.length) {
                throw new IllegalArgumentException("Missing value for " + option);
            }
            return args[index];
        }
    }

    static class MagicRepl {
        private final EnvironmentStore store;
        private MagicSession session;
        private final boolean noImport;
        private final List<String> buffer = new ArrayList<>();

        MagicRepl(EnvironmentStore store, MagicSession session, boolean noImport) {
            this.store = store;
            this.session = session;
            this.noImport = noImport;
        }

        int run() throws IOException {
            printWelcome();
            Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
            while (true) {
                System.out.print(prompt());
                if (!scanner.hasNextLine()) {
                    System.out.println();
                    return 0;
                }
                String line = scanner.nextLine();
                if (line.isBlank()) {
                    continue;
                }
                String trimmed = line.trim();
                if (trimmed.startsWith(":") || trimmed.startsWith("/")) {
                    int exit = handleCommand(trimmed.substring(1));
                    if (exit >= 0) {
                        return exit;
                    }
                } else {
                    buffer.add(line);
                    System.out.println("added #" + buffer.size());
                }
            }
        }

        private String prompt() {
            return "magic(" + session.environment().name() + ":" + buffer.size() + ")> ";
        }

        private void printWelcome() {
            System.out.println("Magic REPL");
            System.out.println("Env: " + session.environment().name());
            System.out.println("Type :help for commands. Type Magic words to append them to the buffer.");
        }

        private int handleCommand(String commandLine) throws IOException {
            List<String> parts = splitCommand(commandLine);
            if (parts.isEmpty()) {
                return -1;
            }
            String command = parts.get(0).toLowerCase(Locale.ROOT);
            switch (command) {
                case "help", "?" -> printHelp();
                case "exit", "quit", "q" -> {
                    return 0;
                }
                case "run", "go" -> runBufferOrFile(parts);
                case "eval", "e" -> eval(parts);
                case "buffer", "look", "buf" -> printBuffer();
                case "clear" -> {
                    buffer.clear();
                    System.out.println("buffer cleared");
                }
                case "save" -> saveBuffer(parts);
                case "import", "load" -> importPackage(parts);
                case "reset" -> {
                    session.reset(!noImport);
                    System.out.println("context reset");
                }
                case "context", "ctx" -> printContext();
                case "functions", "fn" -> printFunctions(parts);
                case "aliases", "alias" -> printAliases(parts);
                case "env" -> handleEnv(parts);
                case "home" -> System.out.println(store.home());
                default -> System.out.println("Unknown command: " + command + ". Type :help.");
            }
            return -1;
        }

        private void runBufferOrFile(List<String> parts) throws IOException {
            if (parts.size() >= 2) {
                executeAndReport(session, readMagicLines(parts.get(1)), System.out, System.err);
                return;
            }
            if (buffer.isEmpty()) {
                System.out.println("buffer is empty");
                return;
            }
            executeAndReport(session, new ArrayList<>(buffer), System.out, System.err);
        }

        private void eval(List<String> parts) {
            if (parts.size() < 2) {
                System.out.println("Usage: :eval <magic words>");
                return;
            }
            executeAndReport(session, List.of(String.join(" ", parts.subList(1, parts.size()))), System.out, System.err);
        }

        private void printBuffer() {
            if (buffer.isEmpty()) {
                System.out.println("buffer is empty");
                return;
            }
            for (int i = 0; i < buffer.size(); i++) {
                System.out.println((i + 1) + ": " + buffer.get(i));
            }
        }

        private void saveBuffer(List<String> parts) throws IOException {
            if (parts.size() < 2) {
                System.out.println("Usage: :save <name> [directory]");
                return;
            }
            if (buffer.isEmpty()) {
                System.out.println("buffer is empty");
                return;
            }
            String fileName = parts.get(1).endsWith(".m") ? parts.get(1) : parts.get(1) + ".m";
            Path directory = parts.size() >= 3 ? resolveUserPath(parts.get(2)) : session.environment().spellsPath();
            Files.createDirectories(directory);
            Path file = directory.resolve(fileName).normalize();
            Files.write(file, buffer, StandardCharsets.UTF_8);
            System.out.println("saved: " + file);
        }

        private void importPackage(List<String> parts) {
            if (parts.size() < 2) {
                System.out.println("Usage: :import <directory> [packageId]");
                return;
            }
            Path directory = resolveUserPath(parts.get(1));
            String packageId = parts.size() >= 3 ? parts.get(2) : "local";
            MagicPackage magicPackage = new MagicPackage(packageId);
            magicPackage.loadFiles(directory.toString());
            magicPackage.importPackage(session.context(), session.manager());
            System.out.println("imported " + magicPackage.getMagicInstanceIds().size() + " spell(s) as " + packageId + ".*");
        }

        private void printContext() {
            Set<String> variables = new TreeSet<>(session.context().getVariableNames());
            Set<String> objects = new TreeSet<>(session.context().getObjectNames());
            System.out.println("variables: " + (variables.isEmpty() ? "(none)" : String.join(", ", variables)));
            System.out.println("objects: " + (objects.isEmpty() ? "(none)" : String.join(", ", objects)));
        }

        private void printFunctions(List<String> parts) {
            String filter = parts.size() >= 2 ? parts.get(1).toLowerCase(Locale.ROOT) : "";
            session.manager().getFunctionsRealNames().stream()
                    .filter(name -> filter.isBlank() || name.toLowerCase(Locale.ROOT).contains(filter))
                    .sorted()
                    .forEach(System.out::println);
        }

        private void printAliases(List<String> parts) {
            if (parts.size() < 2) {
                System.out.println("Usage: :aliases <function>");
                return;
            }
            String function = parts.get(1);
            List<String> aliases = session.manager().getFastExpression().getAliasesManager().getAliases().get(function);
            if (aliases == null || aliases.isEmpty()) {
                System.out.println(function + " has no aliases");
                return;
            }
            System.out.println(function + ": " + String.join(", ", aliases));
        }

        private void handleEnv(List<String> parts) throws IOException {
            if (parts.size() == 1 || parts.get(1).equalsIgnoreCase("current")) {
                System.out.println(session.environment().name());
                return;
            }
            String subCommand = parts.get(1).toLowerCase(Locale.ROOT);
            switch (subCommand) {
                case "list", "ls" -> {
                    String active = session.environment().name();
                    for (String name : store.listEnvironmentNames()) {
                        String mark = name.equals(active) ? "*" : " ";
                        System.out.println(mark + " " + name);
                    }
                }
                case "create", "new" -> {
                    if (parts.size() < 3) {
                        System.out.println("Usage: :env create <name>");
                        return;
                    }
                    MagicEnvironment env = store.createEnvironment(parts.get(2));
                    System.out.println("created: " + env.path());
                }
                case "use", "activate" -> {
                    if (parts.size() < 3) {
                        System.out.println("Usage: :env use <name>");
                        return;
                    }
                    MagicEnvironment env = store.resolveEnvironment(parts.get(2));
                    store.setActiveEnvName(env.name());
                    session = MagicSession.create(env, !noImport);
                    buffer.clear();
                    System.out.println("using env: " + env.name());
                }
                case "path" -> System.out.println(session.environment().path());
                default -> System.out.println("Unknown env command: " + subCommand);
            }
        }

        private void printHelp() {
            System.out.println("REPL commands:");
            System.out.println("  :run, :go                 Run the current buffer");
            System.out.println("  :run <file.m>             Run a file");
            System.out.println("  :eval <words>             Run one line immediately");
            System.out.println("  :buffer, :look            Show the current buffer");
            System.out.println("  :clear                    Clear the buffer");
            System.out.println("  :save <name> [directory]  Save buffer as .m");
            System.out.println("  :import <dir> [id]        Import .m files as id.* variables");
            System.out.println("  :reset                    Reset context and re-import env spells");
            System.out.println("  :context                  Show variables and objects");
            System.out.println("  :functions [filter]       List registered functions");
            System.out.println("  :aliases <function>       Show aliases");
            System.out.println("  :env list|create|use|path Manage env/context");
            System.out.println("  :home                     Show Magic home");
            System.out.println("  :exit                     Quit");
        }

        private List<String> splitCommand(String commandLine) {
            String trimmed = commandLine.trim();
            if (trimmed.isEmpty()) {
                return List.of();
            }
            return new ArrayList<>(Arrays.asList(trimmed.split("\\s+")));
        }
    }

    static class MagicSession {
        private final MagicEnvironment environment;
        private final MagicManager manager;
        private NormalContext context;

        private MagicSession(MagicEnvironment environment, MagicManager manager, NormalContext context) {
            this.environment = environment;
            this.manager = manager;
            this.context = context;
        }

        static MagicSession create(MagicEnvironment environment, boolean importEnvironmentSpells) {
            MagicManager manager = new MagicManager();
            FunctionRegister.regDefault(manager);
            MagicSession session = new MagicSession(environment, manager, new NormalContext());
            if (importEnvironmentSpells) {
                session.importEnvironmentSpells();
            }
            return session;
        }

        SpellContext execute(List<String> lines) {
            Spell spell = new Spell(lines, manager);
            return spell.execute(context);
        }

        void reset(boolean importEnvironmentSpells) {
            context = new NormalContext();
            if (importEnvironmentSpells) {
                importEnvironmentSpells();
            }
        }

        void importEnvironmentSpells() {
            MagicPackage magicPackage = new MagicPackage("env." + environment.name());
            magicPackage.loadFiles(environment.spellsPath().toString());
            magicPackage.importPackage(context, manager);
        }

        MagicEnvironment environment() {
            return environment;
        }

        MagicManager manager() {
            return manager;
        }

        NormalContext context() {
            return context;
        }
    }

    record MagicEnvironment(String name, Path path) {
        Path spellsPath() {
            return path.resolve("spells");
        }
    }

    static class EnvironmentStore {
        private final Path home;

        EnvironmentStore(Path home) {
            if (home != null) {
                this.home = home.toAbsolutePath().normalize();
                return;
            }
            String envHome = System.getenv("MAGIC_HOME");
            if (envHome != null && !envHome.isBlank()) {
                this.home = resolveUserPath(envHome);
            } else {
                this.home = Paths.get(System.getProperty("user.home"), ".magic").toAbsolutePath().normalize();
            }
        }

        void ensureHome() throws IOException {
            Files.createDirectories(envsPath());
            if (!Files.exists(activeEnvFile())) {
                createEnvironment(DEFAULT_ENV);
                setActiveEnvName(DEFAULT_ENV);
            }
        }

        MagicEnvironment resolveEnvironment(String requestedName) throws IOException {
            String name = requestedName == null || requestedName.isBlank() ? getActiveEnvName() : requestedName;
            validateName(name);
            Path path = envsPath().resolve(name).normalize();
            if (!Files.exists(path)) {
                if (DEFAULT_ENV.equals(name)) {
                    return createEnvironment(name);
                }
                throw new IOException("Magic env does not exist: " + name);
            }
            return new MagicEnvironment(name, path);
        }

        MagicEnvironment createEnvironment(String name) throws IOException {
            validateName(name);
            Path path = envsPath().resolve(name).normalize();
            Files.createDirectories(path.resolve("spells"));
            Files.createDirectories(path.resolve("tmp"));
            return new MagicEnvironment(name, path);
        }

        void removeEnvironment(String name) throws IOException {
            validateName(name);
            if (DEFAULT_ENV.equals(name)) {
                throw new IOException("The default env cannot be removed");
            }
            Path path = envsPath().resolve(name).normalize();
            if (!path.startsWith(envsPath().normalize())) {
                throw new IOException("Refusing to remove path outside envs: " + path);
            }
            if (!Files.exists(path)) {
                throw new IOException("Magic env does not exist: " + name);
            }
            try (Stream<Path> stream = Files.walk(path)) {
                List<Path> paths = stream.sorted(Comparator.reverseOrder()).toList();
                for (Path item : paths) {
                    Files.deleteIfExists(item);
                }
            }
            if (getActiveEnvName().equals(name)) {
                setActiveEnvName(DEFAULT_ENV);
            }
        }

        List<String> listEnvironmentNames() throws IOException {
            Files.createDirectories(envsPath());
            try (Stream<Path> stream = Files.list(envsPath())) {
                return stream
                        .filter(Files::isDirectory)
                        .map(path -> path.getFileName().toString())
                        .sorted()
                        .toList();
            }
        }

        String getActiveEnvName() throws IOException {
            if (!Files.exists(activeEnvFile())) {
                return DEFAULT_ENV;
            }
            String name = Files.readString(activeEnvFile(), StandardCharsets.UTF_8).trim();
            return name.isBlank() ? DEFAULT_ENV : name;
        }

        void setActiveEnvName(String name) throws IOException {
            validateName(name);
            Files.createDirectories(home);
            Files.writeString(activeEnvFile(), name, StandardCharsets.UTF_8);
        }

        Path home() {
            return home;
        }

        private Path envsPath() {
            return home.resolve("envs");
        }

        private Path activeEnvFile() {
            return home.resolve("active-env");
        }

        private void validateName(String name) {
            if (name == null || !name.matches("[A-Za-z0-9._-]+")) {
                throw new IllegalArgumentException("Invalid Magic env name: " + name);
            }
        }
    }

    static class SessionCache {
        private final EnvironmentStore store;
        private final boolean noImport;
        private final Map<String, MagicSession> sessions = new ConcurrentHashMap<>();

        SessionCache(EnvironmentStore store, boolean noImport) {
            this.store = store;
            this.noImport = noImport;
        }

        MagicSession get(String name) throws IOException {
            try {
                return sessions.computeIfAbsent(name, key -> {
                    try {
                        return MagicSession.create(store.resolveEnvironment(key), !noImport);
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                });
            } catch (IllegalStateException e) {
                if (e.getCause() instanceof IOException ioException) {
                    throw ioException;
                }
                throw e;
            }
        }
    }

    static class MagicHttpHandler implements HttpHandler {
        private final SessionCache sessions;
        private final String defaultEnvName;

        MagicHttpHandler(SessionCache sessions, String defaultEnvName) {
            this.sessions = sessions;
            this.defaultEnvName = defaultEnvName;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                writeResponse(exchange, 405, "Only POST is supported");
                return;
            }

            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> form = parseFormData(body);
            String envName = firstNonBlank(form.get("Env"), form.get("env"), form.get("MagicManager"), defaultEnvName);
            String spellText = form.get("Spell");
            if (spellText == null || spellText.isBlank()) {
                spellText = body;
            }

            if (spellText == null || spellText.isBlank()) {
                writeResponse(exchange, 400, "Missing Spell");
                return;
            }

            try {
                MagicSession session = sessions.get(envName);
                SpellContext spellContext = session.execute(splitLines(spellText.strip()));
                if (spellContext.hasExecuteError()) {
                    writeResponse(exchange, 500, ErrorUtils.stringError(spellContext));
                    return;
                }
                String response = spellContext.getPrintLog();
                FunctionResult result = spellContext.getSpellReturn();
                if (result != null && !(result instanceof NullResult)) {
                    response = (response == null || response.isBlank() ? "" : response + "\n") + "return: " + result;
                }
                writeResponse(exchange, 200, response == null ? "" : response);
            } catch (Exception e) {
                writeResponse(exchange, 500, e.getMessage() == null ? e.toString() : e.getMessage());
            }
        }

        private static Map<String, String> parseFormData(String data) {
            Map<String, String> map = new HashMap<>();
            if (data == null || data.isBlank() || !data.contains("=")) {
                return map;
            }
            String[] pairs = data.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=", 2);
                if (keyValue.length == 2) {
                    String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                    String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                    map.put(key, value);
                }
            }
            return map;
        }

        private static String firstNonBlank(String... values) {
            for (String value : values) {
                if (value != null && !value.isBlank()) {
                    return value;
                }
            }
            return DEFAULT_ENV;
        }

        private static void writeResponse(HttpExchange exchange, int status, String response) throws IOException {
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
            exchange.sendResponseHeaders(status, bytes.length);
            try (OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(bytes);
            }
        }
    }
}
