package cn.origincraft.magic.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

public class YamlUtils {
    public static Map<String, Object> getYaml(String path){
        System.out.println(FileUtils.getJarFolderPath()+path);
        try (InputStream inputStream =new FileInputStream(FileUtils.getJarFolderPath()+path)) {
            Yaml yaml = new Yaml();
            return yaml.load(inputStream);
        } catch (Exception e) {
            return null;
        }
    }
}
