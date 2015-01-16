package com.percyvega.db2process.configuration;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.percyvega.db2process.repository")
@EntityScan("com.percyvega.db2process.domain")
public class DataConfiguration {}
