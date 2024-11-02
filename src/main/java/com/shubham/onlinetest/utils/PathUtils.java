package com.shubham.onlinetest.utils;

import org.springframework.boot.system.ApplicationHome;

import java.io.File;

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
}
