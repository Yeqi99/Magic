package cn.origincraft.magic;

import cn.origincraft.magic.function.FunctionRegister;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.manager.MagicPackage;
import cn.origincraft.magic.object.ContextMap;
import cn.origincraft.magic.object.NormalContext;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.ErrorUtils;
import cn.origincraft.magic.utils.FileUtils;
import cn.origincraft.magic.utils.YamlUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Magic {
    public static Map<String, MagicManager> magicManagerMap = new HashMap<>();
    public static Map<String, ContextMap> mainContextMaps = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Map<String, Object> config = YamlUtils.getYaml("/config.yml");
        if (config == null) {
            System.out.println("Missing configuration file");
            return;
        }
        MagicManager magicManager = new MagicManager();
        FunctionRegister.regDefault(magicManager);
        magicManagerMap.put("default", magicManager);
        Map<String, Object> serverSetting = (Map<String, Object>) config.get("http-server");
        int port = (int) serverSetting.get("port");
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/magic", new MagicHttpHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port " + port);

        String managerName = "default";
        String contextMapName = "default";
        List<String> spellWords = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("input:");
        System.out.println("Enter /help for help");
        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.startsWith("/")) {
                String command = userInput.replace("/", "").toLowerCase();
                String[] cmdArgs = command.split(" ");
                switch (cmdArgs[0]) {
                    case "exit": {
                        System.out.println("Exit in 3 seconds");
                        server.stop(3);
                        System.exit(0);
                        return;
                    }
                    case "clear": {
                        spellWords = new ArrayList<>();
                        System.out.println("Cleared");
                        continue;
                    }
                    case "look": {
                        System.out.println("Magic Manager:" + managerName);
                        System.out.println("Context:" + contextMapName);
                        System.out.println("Current code:");
                        System.out.println("------------------------------");
                        for (String spellWord : spellWords) {
                            System.out.println(spellWord);
                        }
                        System.out.println("------------------------------");
                        continue;
                    }
                    case "save": {
                        if (cmdArgs.length < 2) {
                            System.out.println("Insufficient parameters");
                            continue;
                        }
                        Path targetFilePath;
                        if (cmdArgs.length > 2) {
                            targetFilePath = Paths.get(cmdArgs[2] + "\\" + cmdArgs[1] + ".m");
                        } else {
                            targetFilePath = Paths.get(FileUtils.getPath("spells\\" + cmdArgs[1] + ".m"));
                        }
                        Path parentDir = targetFilePath.getParent();
                        if (parentDir != null) {
                            Files.createDirectories(parentDir);
                        }
                        Files.write(targetFilePath, spellWords);
                        System.out.println("Saved in " + cmdArgs[1] + ".m");
                        continue;
                    }
                    case "help": {
                        System.out.println("------------------------------");
                        System.out.println("/exit");
                        System.out.println("/clear");
                        System.out.println("/look");
                        System.out.println("/go");
                        System.out.println("/context <name>");
                        System.out.println("/manager <name>");
                        System.out.println("/save <name> [path]");
                        System.out.println("/import <name> <path>");
                        System.out.println("------------------------------");
                        continue;
                    }
                    case "import": {
                        if (cmdArgs.length < 3) {
                            System.out.println("Insufficient parameters");
                        }
                        MagicPackage magicPackage = new MagicPackage(cmdArgs[1]);
                        magicPackage.loadFiles(cmdArgs[2]);
                        if (!magicManagerMap.containsKey(managerName)) {
                            MagicManager newMagic = new MagicManager();
                            FunctionRegister.regDefault(newMagic);
                            magicManagerMap.put(managerName, newMagic);
                        }
                        if (!mainContextMaps.containsKey(contextMapName)) {
                            mainContextMaps.put(contextMapName, new NormalContext());
                        }
                        magicPackage.importPackage(mainContextMaps.get(contextMapName), magicManagerMap.get(managerName));
                        continue;
                    }
                    case "context": {
                        if (cmdArgs.length < 2) {
                            System.out.println("Insufficient parameters");
                            continue;
                        }
                        contextMapName = cmdArgs[1];
                        System.out.println("Change context to" + contextMapName);
                        continue;
                    }
                    case "manager": {
                        if (cmdArgs.length < 2) {
                            System.out.println("Insufficient parameters");
                            continue;
                        }
                        managerName = cmdArgs[1];
                        System.out.println("Change manager to" + contextMapName);
                        continue;
                    }
                    case "go": {
                        if (!magicManagerMap.containsKey(managerName)) {
                            MagicManager newMagic = new MagicManager();
                            FunctionRegister.regDefault(newMagic);
                            magicManagerMap.put(managerName, newMagic);
                        }
                        Spell spell = new Spell(spellWords, magicManagerMap.get(managerName));
                        if (!mainContextMaps.containsKey(contextMapName)) {
                            mainContextMaps.put(contextMapName, new NormalContext());
                        }
                        System.out.println("Execute spell:");
                        SpellContext spellContext = spell.execute(mainContextMaps.get(managerName));
                        if (spellContext.hasExecuteError()) {
                            System.out.println(ErrorUtils.stringError(spellContext));
                        } else if (spellContext.hasSpellReturn()) {
                            System.out.println("Return:" + spellContext.getSpellReturn().getObject());
                        }
                        continue;
                    }
                    default: {
                        System.out.println("Unknown command, enter /help to view help！");
                    }
                }
            } else {
                if (userInput.contains(")")) {
                    spellWords.add(userInput);
                }
            }

        }
    }

    static class MagicHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // 获取请求体数据
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));

                StringBuilder requestData = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    requestData.append(line).append("\n");
                }

                // 输出请求体数据到控制台
                System.out.println("Received data via POST request:");
                Map<String, String> data = parseFormData(requestData.toString());
                System.out.println(data);
                String response = "";
                if (data.containsKey("MagicManager")) {
                    String managerName = data.get("MagicManager");
                    if (magicManagerMap.containsKey(managerName)) {
                        MagicManager magicManager = magicManagerMap.get(managerName);
                        String spellString = data.get("Spell");
                        spellString = spellString.strip();
                        String[] words = spellString.split("\n");
                        List<String> wordsList = new ArrayList<>(Arrays.asList(words));
                        Spell spell = new Spell(wordsList, magicManager);
                        if (!mainContextMaps.containsKey(managerName)) {
                            mainContextMaps.put(managerName, new NormalContext());
                        }
                        SpellContext spellContext = spell.execute(mainContextMaps.get(managerName));
                        if (spellContext.hasExecuteError()) {
                            response = ErrorUtils.stringError(spellContext);
                        } else if (spellContext.hasSpellReturn()) {
                            if (spellContext.getSpellReturn() instanceof NullResult){
                                response = spellContext.getPrintLog();
                            }else {
                                response = spellContext.getPrintLog() + "\nreturn:" + spellContext.getSpellReturn().toString();
                            }
                        }
                    } else {
                        response = "No magic manager with the specified name was found";
                    }
                } else {
                    response = "Unknown instruction";
                }
                exchange.getResponseHeaders().set("Content-Type", "text/plain");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            } else {
                exchange.sendResponseHeaders(405, 0);
            }
            exchange.close();
        }
    }

    public static Map<String, String> parseFormData(String data) {
        Map<String, String> map = new HashMap<>();
        if (data != null && !data.isEmpty()) {
            String[] pairs = data.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    try {
                        String key = URLDecoder.decode(keyValue[0], "UTF-8");
                        String value = URLDecoder.decode(keyValue[1], "UTF-8");
                        map.put(key, value);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return map;
    }
}
