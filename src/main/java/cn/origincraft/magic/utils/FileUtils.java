package cn.origincraft.magic.utils;

import java.io.File;
import java.net.URL;

public class FileUtils {
    public static String getJarPath(){
        // 获取当前类的 URL
        URL url = FileUtils.class.getProtectionDomain().getCodeSource().getLocation();

        // 获取 Jar 文件的相对位置
        return url.getPath();
    }
    public static String getPath(String path){
        return getJarFolderPath()+"\\"+path;
    }
    public static String getJarFolderPath() {
        // 获取当前类的 URL
        URL url = FileUtils.class.getProtectionDomain().getCodeSource().getLocation();

        // 获取 Jar 文件的路径
        String jarPath = url.getPath();

        // 提取 Jar 文件所在文件夹的路径

        return new File(jarPath).getParent();
    }
    public static String getResourcesPath(String path){
        URL resourceUrl = FileUtils.class.getResource("/"+path);
        if (resourceUrl==null){
            return null;
        }
        return resourceUrl.getPath();

    }
}
