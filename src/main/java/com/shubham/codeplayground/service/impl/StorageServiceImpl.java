package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.model.dto.FileUploadDTO;
import com.shubham.codeplayground.model.entity.BinaryFile;
import com.shubham.codeplayground.repository.BinaryFileRepository;
import com.shubham.codeplayground.service.StorageService;
import com.shubham.codeplayground.utils.FileUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {
    private final BinaryFileRepository fileRepository;

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

    @Override
    public String getFileContentsAsString(UUID id) {
        String fileContents = "";
        Optional<BinaryFile> optionalFile = fileRepository.findById(id);

        if(optionalFile.isEmpty())
            // TODO: Create custom exception
            throw new RuntimeException("File not found in DB");

        byte[] zippedFileData = optionalFile.get().getData();

        try {
            byte[] fileData = FileUtils.extractFile(zippedFileData);
            fileContents = new String(fileData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileContents;
    }
}
