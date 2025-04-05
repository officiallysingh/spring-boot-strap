package com.ksoot.domain.model;

import com.ksoot.common.jpa.AbstractEntity;
import com.ksoot.common.util.RegularExpressions;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.Audited;

@Getter
// @Accessors(chain = true, fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Audited
@DynamicUpdate
@Entity
@Table(
    name = "employees",
    indexes = {@Index(name = "idx_employees_code", columnList = "code")})
public class Employee extends AbstractEntity {

  @NotEmpty
  @Size(min = 5, max = 10)
  @Pattern(regexp = RegularExpressions.REGEX_EMPLOYEE_CODE)
  @NaturalId
  @Column(name = "code", updatable = false, nullable = false, length = 20, unique = true)
  private String code;

  @NotEmpty
  @Size(min = 3, max = 50)
  @Pattern(regexp = RegularExpressions.REGEX_ALPHABETS_AND_SPACES)
  @Setter
  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @NotNull
  @Past
  @Setter
  @Column(name = "dob", nullable = false, length = 2)
  private LocalDate dob;
}
