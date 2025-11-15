package com.shubham.codeplayground.service.impl;

import com.shubham.codeplayground.exception.FileNotFoundInDatabaseException;
import com.shubham.codeplayground.exception.FileReadingException;
import com.shubham.codeplayground.exception.FileUploadException;
import com.shubham.codeplayground.model.dto.FileUploadDTO;
import com.shubham.codeplayground.model.entity.BinaryFile;
import com.shubham.codeplayground.repository.BinaryFileRepository;
import com.shubham.codeplayground.service.StorageService;
import com.shubham.codeplayground.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {
    private final BinaryFileRepository fileRepository;

    @Override
    public FileUploadDTO uploadFileToDatabase(MultipartFile file) {
        BinaryFile fileData = null;

        try {
            fileData = fileRepository.save(BinaryFile.builder()
                    .name(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .data(FileUtils.compressFile(file.getBytes(), file.getOriginalFilename()))
                    .build());
        } catch (IOException e) {
            throw new FileUploadException("Error while uploading file to DB",e);
        }

        return FileUploadDTO.builder()
                .id(fileData.getId())
                .filename(fileData.getName())
                .build();
    }

    @Override
    public String getDatabaseFileContentsAsString(UUID id) {
        String fileContents = "";
        BinaryFile file = fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundInDatabaseException(
                        MessageFormat.format("File with ID: {0} not found in DB", id))
                );

        try {
            byte[] fileData = FileUtils.extractFile(file.getData());
            fileContents = new String(fileData);
        } catch (IOException e) {
            throw new FileReadingException(MessageFormat.format("Error while reading file {0}", id),e);
        }

        return fileContents;
    }
}
