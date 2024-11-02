package com.shubham.onlinetest.exception.handler;

import com.shubham.onlinetest.exception.ProblemNotFoundException;
import com.shubham.onlinetest.exception.UserNotFoundException;
import com.shubham.onlinetest.model.dto.ExceptionDTO;
import com.shubham.onlinetest.model.result.AppResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceImpl {
    @ExceptionHandler({UserNotFoundException.class, ProblemNotFoundException.class})
    public ResponseEntity<AppResult> handleNotFoundException(Exception exception, HttpServletRequest request) {
        ExceptionDTO error = new ExceptionDTO(exception, request);
        HttpStatus status = HttpStatus.NOT_FOUND;

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
