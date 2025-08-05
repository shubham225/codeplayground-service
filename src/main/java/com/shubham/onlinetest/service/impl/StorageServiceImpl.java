package com.shubham.onlinetest.service.impl;

import com.shubham.onlinetest.model.dto.FileUploadDTO;
import com.shubham.onlinetest.model.entity.BinaryFile;
import com.shubham.onlinetest.repository.BinaryFileRepository;
import com.shubham.onlinetest.service.StorageService;
import com.shubham.onlinetest.utils.FileUtils;
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
