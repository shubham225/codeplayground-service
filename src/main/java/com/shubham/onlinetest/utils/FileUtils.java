package com.shubham.onlinetest.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class FileUtils {
    private static final int BUFFER_SIZE = 4096; // 4KB buffer size for efficient processing

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

    /**
     * Compresses a byte array into a ZIP format and returns the compressed data.
     *
     * @param fileData The byte array of the file to be compressed.
     * @param fileName The name of the file to be stored inside the ZIP.
     * @return The compressed file as a byte array.
     * @throws IOException If an I/O error occurs.
     */
    public static byte[] compressFile(byte[] fileData, String fileName) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(fileData);
            zipOutputStream.closeEntry();
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Extracts a ZIP file from a byte array and returns the extracted content.
     * Assumes there is only one file inside the ZIP.
     *
     * @param zipData The compressed ZIP file as a byte array.
     * @return The extracted file data as a byte array.
     * @throws IOException If an I/O error occurs.
     */
    public static byte[] extractFile(byte[] zipData) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(zipData);
        ByteArrayOutputStream extractedOutputStream = new ByteArrayOutputStream();

        try (ZipInputStream zipInputStream = new ZipInputStream(byteArrayInputStream)) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            if (zipEntry == null) {
                throw new IOException("No file found inside the ZIP archive.");
            }

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                extractedOutputStream.write(buffer, 0, bytesRead);
            }
            zipInputStream.closeEntry();
        }

        return extractedOutputStream.toByteArray();
    }
}
