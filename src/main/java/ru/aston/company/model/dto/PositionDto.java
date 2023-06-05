package ru.aston.company.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import ru.aston.company.model.validation.group.ForCreate;

public record PositionDto (
        Long id,

        @NotBlank(groups = ForCreate.class)
        @Pattern(regexp = NAME_PATTERN)
        String name
) {
        public static final String NAME_PATTERN = "[\\p{Alpha}А-Яа-яЁё][\\p{Alpha}\\sА-Яа-яЁё]{1,49}";
}