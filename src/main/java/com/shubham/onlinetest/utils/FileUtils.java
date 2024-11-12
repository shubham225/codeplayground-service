package com.shubham.onlinetest.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FileUtils {
    public static void createFileWithContents(String filePath, String contents) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(contents);
            System.out.println("File created and content written successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
