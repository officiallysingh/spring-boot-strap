package ai.whilter.common.util;

import java.math.BigDecimal;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class NumberUtils {

  public static Double toDouble(final String value) {
    return StringUtils.isNotBlank(value) ? Double.valueOf(value) : null;
  }

  public boolean isZero(final BigDecimal value) {
    return value.compareTo(BigDecimal.ZERO) == 0;
  }

  public boolean isNonZero(final BigDecimal value) {
    return value.compareTo(BigDecimal.ZERO) != 0;
  }

  public boolean gteZero(final BigDecimal value) {
    return value.compareTo(BigDecimal.ZERO) >= 0;
  }

  public boolean gtZero(final BigDecimal value) {
    return value.compareTo(BigDecimal.ZERO) > 0;
  }

  public boolean lteZero(final BigDecimal value) {
    return value.compareTo(BigDecimal.ZERO) <= 0;
  }

  public boolean ltZero(final BigDecimal value) {
    return value.compareTo(BigDecimal.ZERO) < 0;
  }

  public boolean gte(final BigDecimal source, final BigDecimal target) {
    return source.compareTo(target) >= 0;
  }

  public boolean gt(final BigDecimal source, final BigDecimal target) {
    return source.compareTo(target) > 0;
  }

  public boolean lte(final BigDecimal source, final BigDecimal target) {
    return source.compareTo(target) <= 0;
  }

  public boolean lt(final BigDecimal source, final BigDecimal target) {
    return source.compareTo(target) < 0;
  }

  public boolean ne(final BigDecimal source, final BigDecimal target) {
    return source.compareTo(target) != 0;
  }
}
