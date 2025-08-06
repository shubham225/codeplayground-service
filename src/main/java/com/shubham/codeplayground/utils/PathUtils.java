package com.shubham.codeplayground.utils;

import org.springframework.boot.system.ApplicationHome;

import java.io.File;
import java.nio.file.Paths;

public class PathUtils {

    public String getCurrentJarPath() {
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();

        return jarFile != null ? jarFile.getParent() : null;
    }

    public static String concatPath(String baseUrl, String... paths) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);

        if (!baseUrl.endsWith("/")) {
            urlBuilder.append("/");
        }

        for (String path : paths) {
            if (path != null) {
                String trimmedPath = path.startsWith("/") ? path.substring(1) : path;
                urlBuilder.append(trimmedPath);

                if (!trimmedPath.endsWith("/") && !path.equals(paths[paths.length - 1])) {
                    urlBuilder.append("/");
                }
            }
        }

        return urlBuilder.toString();
    }

    public static String getWslPath(String path) {
        StringBuilder sb = new StringBuilder();

        int index = path.indexOf(':');
        String mount = "/mnt/" + path.substring(0,index).toLowerCase();
        String folderPath = path.substring(path.indexOf(':')+1);

        return Paths.get(mount, folderPath).toString().replace("\\", "/");
    }
}
