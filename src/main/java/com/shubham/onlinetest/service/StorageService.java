package com.shubham.onlinetest.service;

import com.shubham.onlinetest.model.dto.FileUploadDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    public FileUploadDTO uploadFile(MultipartFile file) throws IOException;
}
