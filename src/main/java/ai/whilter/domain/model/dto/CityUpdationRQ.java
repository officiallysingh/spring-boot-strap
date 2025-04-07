package ai.whilter.domain.model.dto;

import ai.whilter.common.util.RegularExpressions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

@Builder
@Valid
public record CityUpdationRQ(
    @Schema(description = "City or District code", example = "79")
        @Pattern(regexp = RegularExpressions.CITY_CODE)
        @Size(min = 1, max = 3)
        String code,
    @Schema(description = "City or District name", example = "Sirsa")
        @Pattern(regexp = RegularExpressions.CITY_NAME)
        @Size(max = 100)
        String name,
    @Schema(description = "State Id", example = "6558c30160463a1fee00c7dc") String stateId) {

  @JsonIgnore
  public boolean isEmpty() {
    return StringUtils.isBlank(this.code)
        && StringUtils.isBlank(this.name)
        && StringUtils.isBlank(this.stateId);
  }
}
