package ru.aston.company.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import ru.aston.company.model.validation.group.ForCreate;
import java.util.Set;

public record EmployeeDetailDto(
        Long id,

        @NotBlank(groups = ForCreate.class)
        @Pattern(regexp = NAME_PATTERN)
        String firstName,

        @NotBlank(groups = ForCreate.class)
        @Pattern(regexp = NAME_PATTERN)
        String lastName,

        @NotBlank(groups = ForCreate.class)
        @Pattern(regexp = POSITION_NAME_PATTERN)
        String position,

        @Valid
        Set<@Pattern(regexp = PROJECT_NAME_PATTERN) String> projects
) {
        public static final String NAME_PATTERN = "[\\p{Alpha}А-Яа-яЁё]{2,20}";
        public static final String POSITION_NAME_PATTERN = "[\\p{Alpha}А-Яа-яЁё][\\p{Alpha}\\sА-Яа-яЁё]{1,49}";
        public static final String PROJECT_NAME_PATTERN = "[\\p{Alpha}А-Яа-яЁё][\\p{Alpha}\\d\\sА-Яа-яЁё]{1,49}";
}