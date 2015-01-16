package com.percyvega.db2process.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableAutoConfiguration
@ComponentScan({
        "com.percyvega.db2process"
})
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.debug("Starting main(" + Arrays.toString(args) + ")");

        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

        logger.debug("Active Profiles: " + Arrays.toString(applicationContext.getEnvironment().getActiveProfiles()));

        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            logger.debug(beanName);
        }

        MyExecutorService myExecutorService = (MyExecutorService) applicationContext.getBean("myExecutorService");
        try {
            myExecutorService.run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.debug("Finishing main(...)");
    }

}
