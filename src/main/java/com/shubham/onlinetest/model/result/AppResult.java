package com.shubham.onlinetest.model.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppResult {
    private Integer code;
    private String message;
    private Object payload;

    public static ResponseEntity<AppResult> success() {
        return success("");
    }

    public static ResponseEntity<AppResult> success(final String msg) {
        return success(msg, null);
    }

    public static ResponseEntity<AppResult> success(final Object data) {
        return success("", data);
    }

    public static ResponseEntity<AppResult> success(final String msg, final Object data) {
        HttpStatus status = HttpStatus.OK;
        AppResult appResult =  get(status.value(), msg, data);
        return new ResponseEntity<>(appResult, status);
    }

    public static ResponseEntity<AppResult> error(final Object data) {
        return error(null, data);
    }

    public static ResponseEntity<AppResult> error(final String msg, final Object data) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        AppResult appResult =  get(status.value(), msg, data);
        return new ResponseEntity<>(appResult, status);
    }

    public static ResponseEntity<AppResult> badRequest(final String msg, final Object data) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        AppResult appResult =  get(status.value(), msg, data);
        return new ResponseEntity<>(appResult, status);
    }

    public static ResponseEntity<AppResult> unauthorized(final String msg, final Object data) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        AppResult appResult =  get(status.value(), msg, data);
        return new ResponseEntity<>(appResult, status);
    }

    private static AppResult get(final int code, final String msg, final Object data) {
        return new AppResult(code, msg, data);
    }
}
