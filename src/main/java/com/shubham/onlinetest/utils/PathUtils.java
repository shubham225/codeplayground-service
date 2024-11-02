package com.shubham.onlinetest.utils;

import org.springframework.boot.system.ApplicationHome;

import java.io.File;

public class PathUtils {

    public String getCurrentJarPath() {
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();

        return jarFile != null ? jarFile.getParent() : null;
    }
}
