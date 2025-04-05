package com.ksoot.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ksoot.common.util.RegularExpressions;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

@Builder
@Valid
public record StateUpdationRQ(
    @Schema(description = "State code. Two char code like HR for Haryana", example = "HR")
        @Size(min = 2, max = 2)
        @Pattern(regexp = RegularExpressions.TWO_CHAR_ALPHA_CAPITAL)
        String code,
    @Schema(description = "State name", nullable = true, example = "Haryana")
        @Size(max = 50)
        @Pattern(regexp = RegularExpressions.ALPHABETS_AND_SPACES)
        String name,
    @Schema(description = "Whether its a Union Territory", nullable = true, example = "false")
        Boolean isUT) {

  @JsonIgnore
  public boolean isEmpty() {
    return StringUtils.isBlank(this.code)
        && StringUtils.isBlank(this.name)
        && Objects.isNull(this.isUT);
  }
}
