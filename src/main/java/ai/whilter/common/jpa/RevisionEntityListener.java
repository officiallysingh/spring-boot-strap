package ai.whilter.common.jpa;

import ai.whilter.common.CommonConstants;
import ai.whilter.common.audit.AuthorProvider;
import ai.whilter.common.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.RevisionListener;

@RequiredArgsConstructor
public class RevisionEntityListener implements RevisionListener {

  private final AuthorProvider authorProvider;

  @Override
  public void newRevision(Object revisionEntity) {
    if (revisionEntity instanceof RevisionEntity revisionEntityInfo) {
      // revisionInfo.setActor(IdentityHelper.getAuditUserId());
      revisionEntityInfo.setAuthor(CommonConstants.SYSTEM_USER);
      revisionEntityInfo.setDatetime(DateTimeUtils.nowOffsetDateTimeUTC());
    } else {
      throw new IllegalStateException(
          "revisionEntity is not of expected type: " + RevisionEntity.class.getName());
    }
  }
}
