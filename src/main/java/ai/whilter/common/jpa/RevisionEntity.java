package ai.whilter.common.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.DefaultRevisionEntity;

@Getter
@Setter
@ToString
@Entity
@Table(
    name = "revisions",
    indexes = {@Index(name = "idx_rev_datetime", columnList = "datetime")})
@org.hibernate.envers.RevisionEntity(RevisionEntityListener.class)
public class RevisionEntity extends DefaultRevisionEntity {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Column(name = "datetime", updatable = false, nullable = false)
  private OffsetDateTime datetime;

  @NotNull
  @Column(name = "actor", updatable = false, nullable = false)
  private String actor;
}
