package com.ksoot.common.jpa;

import com.ksoot.common.Identifiable;
import com.ksoot.common.Versionable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@MappedSuperclass
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractEntity implements Identifiable<Long>, Versionable<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  protected Long id;

  @Version protected Long version;

  @Override
  public Long getId() {
    return this.id;
  }

  @Override
  public Long getVersion() {
    return this.version;
  }
}
