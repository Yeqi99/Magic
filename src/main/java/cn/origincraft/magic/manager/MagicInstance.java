package cn.origincraft.magic.manager;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.object.Spell;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MagicInstance {
    private final String id;
    private List<String> contentLines;
    private final String filePath;

    public MagicInstance(String id, String filePath) {
        this.id = id;
        this.contentLines = new ArrayList<>();
        this.filePath = filePath;
    }
    public String getId() {
        return id;
    }

    public List<String> getContentLines() {
        return contentLines;
    }
    public Spell getSpell(MagicManager magicManager){
        return new Spell(contentLines,magicManager);
    }
    public void setContentLines(List<String> contentLines) {
        this.contentLines = contentLines;
    }

    public String getFilePath() {
        return filePath;
    }

    public void loadContent() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : contentLines) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
