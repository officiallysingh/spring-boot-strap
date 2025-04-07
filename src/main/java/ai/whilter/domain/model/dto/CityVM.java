package ai.whilter.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CityVM(
    @Schema(description = "Internal record id", example = "6558c30160463a1fee00c7dc") String id,
    @Schema(description = "City or District code", example = "79") String code,
    @Schema(description = "City or District name", example = "Sirsa") String name,
    StateVM state) {}
