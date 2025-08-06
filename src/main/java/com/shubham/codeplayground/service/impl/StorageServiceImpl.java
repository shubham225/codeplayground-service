package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.model.dto.FileUploadDTO;
import com.shubham.codeplayground.model.entity.BinaryFile;
import com.shubham.codeplayground.repository.BinaryFileRepository;
import com.shubham.codeplayground.service.StorageService;
import com.shubham.codeplayground.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class StorageServiceImpl implements StorageService {
    private final BinaryFileRepository fileRepository;

    public StorageServiceImpl(BinaryFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileUploadDTO uploadFile(MultipartFile file) throws IOException {
        BinaryFile fileData = fileRepository.save(BinaryFile.builder()
                .name(file.getOriginalFilename())
                .fileType(file.getContentType())
                .data(FileUtils.compressFile(file.getBytes(), file.getOriginalFilename()))
                .build());

        return FileUploadDTO.builder()
                .id(fileData.getId())
                .filename(fileData.getName())
                .build();

    }
}
