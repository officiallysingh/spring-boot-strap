package ai.whilter.common.jpa;

import ai.whilter.common.CommonConstants;
import java.time.OffsetDateTime;
import lombok.Getter;
import org.springframework.data.history.RevisionMetadata;

@Getter
public class RevisionInfo<N extends Number & Comparable<N>> {

  private final N number;

  private final OffsetDateTime datetime;

  private final String author;

  private final RevisionMetadata.RevisionType revisionType;

  public RevisionInfo(final RevisionMetadata<N> revisionMetadata) {
    this.number = revisionMetadata.getRequiredRevisionNumber();
    this.revisionType = revisionMetadata.getRevisionType();
    if (revisionMetadata.getDelegate() instanceof RevisionEntity revisionEntity) {
      this.author = revisionEntity.getAuthor();
      this.datetime = revisionEntity.getDatetime();
    } else {
      this.author = CommonConstants.SYSTEM_USER;
      this.datetime = OffsetDateTime.from(revisionMetadata.getRequiredRevisionInstant());
    }
  }
}
