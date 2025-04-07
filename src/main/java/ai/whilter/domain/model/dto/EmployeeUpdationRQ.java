package ai.whilter.domain.model.dto;

import ai.whilter.common.util.RegularExpressions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

@Builder
@Valid
public record EmployeeUpdationRQ(
    @NotEmpty
        @Size(min = 3, max = 50)
        @Pattern(regexp = RegularExpressions.REGEX_ALPHABETS_AND_SPACES)
        @Schema(description = "Employee name", example = "Rajveer Singh")
        String name,
    @Past @NotNull @Schema(description = "Employee Date of Birth", example = "1984-06-25")
        LocalDate dob) {

  @JsonIgnore
  public boolean isEmpty() {
    return StringUtils.isEmpty(this.name) && Objects.isNull(this.dob);
  }
}
