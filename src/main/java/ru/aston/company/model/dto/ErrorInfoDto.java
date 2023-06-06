package ru.aston.company.model.dto;

public record ErrorInfoDto(
        String errorClass,
        String errorMessage
) {
}