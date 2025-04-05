package com.ksoot.domain.service;

import com.ksoot.adapter.repository.MongoAuditHistoryRepository;
import com.ksoot.common.mongo.AuditEvent;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MongoAuditHistoryService {

  private final MongoAuditHistoryRepository mongoAuditHistoryRepository;

  public Page<AuditEvent> getAuditHistory(
      final String collectionName,
      final AuditEvent.Type type,
      final List<Long> revisions,
      final String actor,
      final OffsetDateTime fromDateTime,
      final OffsetDateTime tillDateTime,
      final Pageable pageRequest) {
    return this.mongoAuditHistoryRepository.getAuditHistory(
        collectionName, type, revisions, actor, fromDateTime, tillDateTime, pageRequest);
  }
}
