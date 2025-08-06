package com.shubham.codeplayground.exception.handler;

import com.shubham.codeplayground.exception.ProblemNotFoundException;
import com.shubham.codeplayground.exception.UserNotFoundException;
import com.shubham.codeplayground.model.dto.ExceptionDTO;
import com.shubham.codeplayground.model.result.AppResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class ControllerAdviceImpl {
    @ExceptionHandler({UserNotFoundException.class, ProblemNotFoundException.class})
    public ResponseEntity<AppResult> handleNotFoundException(Exception exception, HttpServletRequest request) {
        ExceptionDTO error = new ExceptionDTO(exception, request);
        HttpStatus status = HttpStatus.NOT_FOUND;

        error.setStatus(status.toString());

        return AppResult.error(exception.getMessage(), error);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<AppResult> handleMaxSizeException(Exception exception, HttpServletRequest request) {
        ExceptionDTO error = new ExceptionDTO(exception, request);
        HttpStatus status = HttpStatus.PAYLOAD_TOO_LARGE;

        error.setStatus(status.toString());

        return AppResult.error(exception.getMessage(), error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppResult> handleException(Exception exception, HttpServletRequest request) {
        ExceptionDTO error = new ExceptionDTO(exception, request);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        error.setStatus(status.toString());

        return AppResult.error(exception.getMessage(), error);
    }
}
