package ai.whilter.domain.model;

import static ai.whilter.common.mongo.MongoSchema.COLLECTION_CITY;

import ai.whilter.common.mongo.AbstractEntity;
import ai.whilter.common.mongo.Auditable;
import ai.whilter.common.util.RegularExpressions;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
@ToString(exclude = {"state"})
@Auditable
@Document(collection = COLLECTION_CITY)
@TypeAlias("city")
@CompoundIndex(name = "idx_unq_city_code_name", def = "{'code': 1, 'name' : 1}", unique = true)
public class City extends AbstractEntity {

  @NotNull
  @DocumentReference(lazy = true)
  @Setter
  private State state;

  @NotEmpty
  @Size(min = 1, max = 3)
  @Pattern(regexp = RegularExpressions.CITY_CODE)
  @Indexed(unique = true, name = "idx_unq_city_code")
  @Setter
  private String code;

  @NotEmpty
  @Size(max = 100)
  @Pattern(regexp = RegularExpressions.CITY_NAME)
  @Setter
  private String name;

  public Pair<String, String> listItem() {
    return ImmutablePair.of(this.code, this.name);
  }

  @PersistenceCreator
  City(
      final String id,
      final Long version,
      final State state,
      final String code,
      final String name) {
    super(id, version);
    this.state = state;
    this.code = code;
    this.name = name;
  }
}
