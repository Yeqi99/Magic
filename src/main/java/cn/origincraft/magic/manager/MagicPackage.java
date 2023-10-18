package cn.origincraft.magic.manager;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.object.ContextMap;
import cn.origincraft.magic.object.SpellContext;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MagicPackage {
    private final String id;
    private final Map<String, MagicInstance> magicInstances;

    public MagicPackage(String id) {
        this.id = id;
        magicInstances = new HashMap<>();
    }

    public void loadFiles(String path) {
        File folder = new File(path);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".m")) {
                        String fileName = file.getName();
                        String id = fileName.substring(0, fileName.lastIndexOf('.'));
                        MagicInstance instance = new MagicInstance(id, file.getAbsolutePath());
                        instance.loadContent();
                        magicInstances.put(id, instance);
                    }
                }
            }
        }
    }

    public MagicInstance getInstanceById(String id) {
        return magicInstances.get(id);
    }

    public List<MagicInstance> getInstances() {
        return new ArrayList<>(magicInstances.values());
    }

    public List<String> getInstanceIds() {
        return new ArrayList<>(magicInstances.keySet());
    }

    public void saveToFile() {
        magicInstances.forEach((id, instance) -> {
            instance.saveToFile();
        });
    }

    public void importPackage(SpellContext spellContext) {
        magicInstances.forEach((id, instance) -> {
            spellContext.putVariable(getId() + "." + id, instance.getSpell(spellContext.getMagicManager()));
        });
    }

    public void importPackage(ContextMap context, MagicManager magicManager) {
        magicInstances.forEach((id, instance) -> {
            context.putVariable(getId() + "." + id, instance.getSpell(magicManager));
        });
    }

    public String getId() {
        return id;
    }

    public Map<String, MagicInstance> getMagicInstances() {
        return magicInstances;
    }

    public MagicInstance getMagicInstance(String id) {
        return magicInstances.get(id);
    }

    public boolean hasMagicInstance(String id) {
        return magicInstances.containsKey(id);
    }

    public List<String> getMagicInstanceIds() {
        return new ArrayList<>(magicInstances.keySet());
    }
}
