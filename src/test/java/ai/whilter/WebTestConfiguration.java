package ai.whilter;

import ai.whilter.common.util.MessageProvider;
import com.ksoot.problem.spring.boot.autoconfigure.ProblemJacksonConfiguration;
import com.ksoot.problem.spring.boot.autoconfigure.web.ProblemWebAutoConfiguration;
import com.ksoot.problem.spring.boot.autoconfigure.web.WebExceptionHandler;
import com.ksoot.problem.spring.config.ProblemBeanRegistry;
import com.ksoot.problem.spring.config.ProblemMessageProviderConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

@TestConfiguration
@ImportAutoConfiguration(
    classes = {
      ProblemBeanRegistry.class,
      ProblemMessageProviderConfig.class,
      ProblemJacksonConfiguration.class,
      ProblemWebAutoConfiguration.class,
      WebExceptionHandler.class
    })
public class WebTestConfiguration {

  @Bean
  MessageProvider messageProvider(final MessageSource messageSource) {
    return new MessageProvider(messageSource);
  }
}
