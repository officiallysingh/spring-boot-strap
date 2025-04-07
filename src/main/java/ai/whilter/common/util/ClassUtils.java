package ai.whilter.common.util;

import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;

@UtilityClass
public final class ClassUtils {

  public static boolean isPresent(final String className) {
    try {
      org.apache.commons.lang3.ClassUtils.getClass(className, false);
      return true;
    } catch (final ClassNotFoundException e) {
      return false;
    }
  }

  public static List<Class<?>> getHierarchy(final Class<?> clazz) {
    final List<Class<?>> superClasses =
        org.apache.commons.lang3.ClassUtils.getAllSuperclasses(clazz);
    final List<Class<?>> implementedInterfaces =
        org.apache.commons.lang3.ClassUtils.getAllInterfaces(clazz);
    final List<Class<?>> hierarchy = new ArrayList<>();
    hierarchy.add(clazz);
    if (CollectionUtils.isNotEmpty(superClasses)) {
      hierarchy.addAll(superClasses);
    }
    if (CollectionUtils.isNotEmpty(implementedInterfaces)) {
      hierarchy.addAll(implementedInterfaces);
    }
    return hierarchy;
  }
}
