package com.example.UserProductApplication.DTO;

import lombok.*;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {
    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponseDto(int value, String message) {
        this.status = value;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}

