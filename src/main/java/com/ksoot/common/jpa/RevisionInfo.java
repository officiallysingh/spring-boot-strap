package com.ksoot.common.jpa;

import com.ksoot.common.CommonConstants;
import java.time.OffsetDateTime;
import lombok.Getter;
import org.springframework.data.history.RevisionMetadata;

@Getter
public class RevisionInfo<N extends Number & Comparable<N>> {

  private final N number;

  private final OffsetDateTime datetime;

  private final String actor;

  private final RevisionMetadata.RevisionType revisionType;

  public RevisionInfo(final RevisionMetadata<N> revisionMetadata) {
    this.number = revisionMetadata.getRequiredRevisionNumber();
    this.revisionType = revisionMetadata.getRevisionType();
    if (revisionMetadata.getDelegate() instanceof RevisionEntity revisionEntity) {
      this.actor = revisionEntity.getActor();
      this.datetime = revisionEntity.getDatetime();
    } else {
      this.actor = CommonConstants.SYSTEM_USER;
      this.datetime = OffsetDateTime.from(revisionMetadata.getRequiredRevisionInstant());
    }
  }
}
