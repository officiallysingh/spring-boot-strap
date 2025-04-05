package com.ksoot;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@EnableMongock
// @OpenAPIDefinition(servers = {@Server(url = "${server.servlet.context-path}")})
public class SpringBootSampleApplication {

  public static void main(final String[] args) {
    SpringApplication.run(SpringBootSampleApplication.class, args);
  }
}
