package ai.whilter.common.jpa;

import ai.whilter.common.CommonConstants;
import ai.whilter.common.util.DateTimeUtils;
import org.hibernate.envers.RevisionListener;

public class RevisionEntityListener implements RevisionListener {

  @Override
  public void newRevision(Object revisionEntity) {
    if (revisionEntity instanceof RevisionEntity revisionEntityInfo) {
      // revisionInfo.setActor(IdentityHelper.getAuditUserId());
      revisionEntityInfo.setActor(CommonConstants.SYSTEM_USER);
      revisionEntityInfo.setDatetime(DateTimeUtils.nowOffsetDateTimeUTC());
    } else {
      throw new IllegalStateException(
          "revisionEntity is not of expected type: " + RevisionEntity.class.getName());
    }
  }
}
