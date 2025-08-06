package com.shubham.codeplayground.controller;

import com.shubham.codeplayground.model.dto.FileUploadDTO;
import com.shubham.codeplayground.model.result.AppResult;
import com.shubham.codeplayground.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.shubham.codeplayground.controller.RestApi.VERSION;

@RestController
@RequestMapping(value = VERSION + "/files")
public class FileController {
    private final StorageService storageService;

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }
    @RequestMapping(
            path = "/upload",
            method = RequestMethod.POST
    )
    public ResponseEntity<AppResult> uploadFile(@RequestParam("file") MultipartFile file) {
        FileUploadDTO fileResponse = FileUploadDTO.builder().id(UUID.randomUUID()).filename("Txt").build();
        try {
//            throw new RuntimeException("Error in Testing");
            fileResponse = storageService.uploadFile(file);
        } catch (Exception e) {
            return AppResult.error(e.getMessage(), fileResponse);
        }

        return AppResult.success(fileResponse);
    }
}
