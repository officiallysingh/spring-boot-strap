package ai.whilter.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record StateVM(
    @Schema(description = "Internal record id", example = "6558c30160463a1fee00c7dc") String id,
    @Schema(description = "Two char State code like HR for Haryana", example = "HR") String code,
    @Schema(description = "State name", example = "Haryana") String name,
    @Schema(description = "Whether its a Union Territory", nullable = true, example = "false")
        Boolean isUT,
    List<CityVM> cities) {}
