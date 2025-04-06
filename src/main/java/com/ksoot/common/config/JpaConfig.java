package com.ksoot.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
    repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class,
    basePackages = "com.ksoot")
public class JpaConfig {}
