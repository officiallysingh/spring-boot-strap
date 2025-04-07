package ai.whilter.common.config.web;

import static ai.whilter.common.CommonConstants.SLASH;

import java.util.List;

public class ActuatorUtils {

  public static String[] getPaths(final ActuatorEndpointProperties actuatorEndpointProperties) {
    if (actuatorEndpointProperties != null) {
      List<String> exposedEndpoints = null;
      if (actuatorEndpointProperties.getExposure().getInclude().contains("*")) {
        exposedEndpoints = ActuatorEndpointProperties.ALL_ENDPOINTS;
      } else {
        exposedEndpoints = actuatorEndpointProperties.getExposure().getInclude();
      }

      if (SLASH.equals(actuatorEndpointProperties.getBasePath())) {
        return exposedEndpoints.stream()
            .map(
                path ->
                    SLASH
                        + actuatorEndpointProperties.getPathMapping().getOrDefault(path, path)
                        + "/**")
            .toArray(String[]::new);
      } else {
        return new String[] {actuatorEndpointProperties.getBasePath() + "/**"};
      }
    } else {
      // TODO: Test if not creating any problem
      return new String[0];
    }
  }

  private ActuatorUtils() {
    throw new IllegalStateException("Just a utility class, not supposed to be instantiated");
  }
}
