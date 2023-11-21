package cn.origincraft.magic.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class YamlUtils {
    public static Map<String, Object> getYaml(String path){
        try (InputStream inputStream = YamlUtils.class.getResourceAsStream(path);) {
            Yaml yaml = new Yaml();
            return yaml.load(inputStream);
        } catch (Exception e) {
            return null;
        }
    }
}
