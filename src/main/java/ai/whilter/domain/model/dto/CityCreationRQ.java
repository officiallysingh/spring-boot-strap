package ai.whilter.domain.model.dto;

import ai.whilter.common.util.RegularExpressions;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@Valid
public record CityCreationRQ(
    @Schema(description = "City or District code", example = "79")
        @Pattern(regexp = RegularExpressions.CITY_CODE)
        @Size(min = 1, max = 3)
        @NotEmpty
        String code,
    @Schema(description = "City or District name", example = "Sirsa")
        @Pattern(regexp = RegularExpressions.CITY_NAME)
        @Size(max = 100)
        @NotEmpty
        String name) {}
