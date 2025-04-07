package ai.whilter.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RegularExpressions {

  public static final String ALPHABETS = "^[a-zA-Z]*$";

  public static final String ALPHABETS_AND_SPACES = "^[a-zA-Z ]+$";

  public static final String ALPHA_NUMERIC = "^[A-Za-z0-9]+$";

  public static final String ALPHA_NUMERIC_CAPITAL = "^[A-Z0-9]+$";

  public static final String ALPHA_NUMERIC_AND_SPACES = "^[a-zA-Z0-9 ]+$";

  public static final String TWO_CHAR_ALPHA_CAPITAL = "^[A-Z]{2}$";

  public static final String THREE_CHAR_MAX_ALPHA_NUMERIC = "^[A-Za-z0-9]{1,3}$";

  public static final String TWO_DIGIT = "^[0-9]{2}$";

  public static final String THREE_DIGIT = "^[0-9]{3}$";

  public static final String NON_SPECIAL_CHARS = "[^`~!?^]+";

  public static final String PINCODE = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$";

  public static final String PAN = "[A-Z]{5}[0-9]{4}[A-Z]{1}";

  public static final String AREA_CODE = "^\\d{5,6}$";

  public static final String AREA_NAME = "^[\\p{L} .,&()-]+$";

  public static final String CITY_CODE = "\\d{1,3}";

  public static final String CITY_NAME = "^[\\p{L} .,&()-]+$";

  public static final String REGEX_EMPLOYEE_CODE = "^[A-Z0-9]{5,10}$";

  public static final String REGEX_ALPHABETS = "^[a-zA-Z]*$";

  public static final String REGEX_ALPHABETS_AND_SPACES = "^[a-zA-Z ]+$";
}
