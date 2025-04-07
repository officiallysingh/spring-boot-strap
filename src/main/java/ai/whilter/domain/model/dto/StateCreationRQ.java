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
public record StateCreationRQ(
    @Schema(description = "State code. Two char code like HR for Haryana", example = "HR")
        @NotEmpty
        @Size(min = 2, max = 2)
        @Pattern(regexp = RegularExpressions.TWO_CHAR_ALPHA_CAPITAL)
        String code,
    @Schema(description = "State name", example = "Haryana")
        @NotEmpty
        @Size(max = 50)
        @Pattern(regexp = RegularExpressions.ALPHABETS_AND_SPACES)
        String name,
    @Schema(description = "Whether its a Union Territory", nullable = true, example = "false")
        boolean isUT) {}
