package com.study.delivery.global.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private int status;
    private String httpMethod;
    private String path;
    private String message;
    private LocalDateTime timestamp;

    public static ErrorResponse of(final HttpStatus status, final String message, final HttpServletRequest request) {
        return ErrorResponse.builder()
                .status(status.value())
                .httpMethod(request.getMethod())
                .path(request.getRequestURI())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
