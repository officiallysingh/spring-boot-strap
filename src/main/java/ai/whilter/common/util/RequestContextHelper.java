package ai.whilter.common.util;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@UtilityClass
public class RequestContextHelper {

  @SuppressWarnings("unchecked")
  public static <T> Optional<T> getAttribute(final String attributeName) {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    Object requestDateTimeObj =
        requestAttributes != null
            ? requestAttributes.getAttribute(attributeName, RequestAttributes.SCOPE_REQUEST)
            : null;
    if (requestDateTimeObj != null) {
      return Optional.of((T) requestDateTimeObj);
    } else {
      return Optional.empty();
    }
  }

  public static Optional<String> getHeader(final String headerName) {
    ServletRequestAttributes servletRequestAttributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (servletRequestAttributes == null) {
      return Optional.empty();
    } else {
      return Optional.ofNullable(servletRequestAttributes.getRequest().getHeader(headerName));
    }
  }

  public static List<String> getHeaders(final List<String> headerNames) {
    if (CollectionUtils.isEmpty(headerNames)) {
      return Collections.emptyList();
    } else {
      ServletRequestAttributes servletRequestAttributes =
          (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      if (servletRequestAttributes == null) {
        return Collections.emptyList();
      } else {
        return headerNames.stream().map(servletRequestAttributes.getRequest()::getHeader).toList();
      }
    }
  }
}
