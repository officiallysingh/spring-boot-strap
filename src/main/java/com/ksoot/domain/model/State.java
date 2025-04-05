package com.ksoot.domain.model;

import static com.ksoot.common.mongo.MongoSchema.COLLECTION_STATE;

import com.ksoot.common.mongo.AbstractEntity;
import com.ksoot.common.mongo.Auditable;
import com.ksoot.common.util.RegularExpressions;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = "cities")
@Auditable
@Document(collection = COLLECTION_STATE)
@TypeAlias("state")
public class State extends AbstractEntity {

  @NotEmpty
  @Size(min = 2, max = 2)
  @Pattern(regexp = RegularExpressions.TWO_CHAR_ALPHA_CAPITAL)
  @Indexed(unique = true)
  @Setter
  private String code;

  @NotEmpty
  @Size(max = 50)
  @Pattern(regexp = RegularExpressions.ALPHABETS_AND_SPACES)
  @Indexed(unique = true)
  @Setter
  private String name;

  @NotNull @Setter private Boolean isUT;

  @DocumentReference(lazy = true, sort = "{ 'name' : 1 }")
  private List<City> cities;

  @PersistenceCreator
  State(
      final String id,
      final Long version,
      final String code,
      final String name,
      final Boolean isUT,
      final List<City> cities) {
    super(id, version);
    this.code = code;
    this.name = name;
    this.isUT = isUT;
    this.cities = cities;
    this.initCitiesIfNull();
  }

  public static State of(final String code, final String name, final Boolean isUT) {
    return new State(code, name, isUT, null);
  }

  public void addCity(final City city) {
    this.initCitiesIfNull();
    this.cities.add(city);
  }

  public void removeCity(final City city) {
    if (Objects.nonNull(this.cities)) {
      this.cities.remove(city);
    }
  }

  public void addCities(final List<City> cities) {
    this.initCitiesIfNull();
    this.cities.addAll(cities);
  }

  private void initCitiesIfNull() {
    if (Objects.isNull(this.cities)) {
      this.cities = new ArrayList<>();
    }
  }

  public Pair<String, String> listItem() {
    return ImmutablePair.of(this.code, this.name);
  }
}
