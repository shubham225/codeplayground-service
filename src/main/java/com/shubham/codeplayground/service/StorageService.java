package com.shubham.codeplayground.service;

import com.shubham.codeplayground.model.dto.FileUploadDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    public FileUploadDTO uploadFile(MultipartFile file) throws IOException;
}
