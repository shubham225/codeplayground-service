package com.shubham.codeplayground.service;

import com.shubham.codeplayground.model.dto.FileUploadDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface StorageService {
    public FileUploadDTO uploadFileToDatabase(MultipartFile file);
    public String getDatabaseFileContentsAsString(UUID id);
}
