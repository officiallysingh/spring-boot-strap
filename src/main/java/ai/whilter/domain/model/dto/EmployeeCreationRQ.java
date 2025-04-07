package ai.whilter.domain.model.dto;

import ai.whilter.common.util.RegularExpressions;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.Builder;

@Builder
@Valid
public record EmployeeCreationRQ(
    @NotEmpty
        @Size(min = 5, max = 10)
        @Pattern(regexp = RegularExpressions.REGEX_EMPLOYEE_CODE)
        @Schema(
            description =
                "Employee code. 5 to 10 char long alphanumeric String, number of capital letters allowed",
            example = "ABC234XYZ")
        String code,
    @NotEmpty
        @Size(min = 3, max = 50)
        @Pattern(regexp = RegularExpressions.REGEX_ALPHABETS_AND_SPACES)
        @Schema(description = "Employee name", example = "Rajveer Singh")
        String name,
    @Past @NotNull @Schema(description = "Employee Date of Birth", example = "1984-06-25")
        LocalDate dob) {}
