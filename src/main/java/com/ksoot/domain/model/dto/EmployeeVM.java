package com.ksoot.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record EmployeeVM(
    @Schema(description = "Internal record id", example = "1") Long id,
    @Schema(
            description =
                "Employee code. 5 to 10 char long alphanumeric String, number of capital letters allowed",
            example = "ABC234XYZ")
        String code,
    @Schema(description = "Employee name", example = "Rajveer Singh") String name,
    @Schema(description = "Employee Date of Birth", example = "1984-06-25") String dob) {}
