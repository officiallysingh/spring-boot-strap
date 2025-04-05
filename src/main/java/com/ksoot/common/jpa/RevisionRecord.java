package com.ksoot.common.jpa;

import java.util.function.Function;
import lombok.Getter;
import org.springframework.data.history.Revision;

@Getter
public class RevisionRecord<N extends Number & Comparable<N>, T, V> {

  private final RevisionInfo<N> revision;

  private final V record;

  public RevisionRecord(final Revision<N, T> revision, final Function<T, V> mapper) {
    this.revision = new RevisionInfo<>(revision.getMetadata());
    this.record = mapper.apply(revision.getEntity());
  }
}
