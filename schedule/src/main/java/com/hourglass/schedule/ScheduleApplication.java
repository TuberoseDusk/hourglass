package com.hourglass.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Slf4j
@ComponentScan("com.hourglass")
public class ScheduleApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ScheduleApplication.class);
        Environment env = application.run(args).getEnvironment();
        log.info("schedule module is running at port: {}", env.getProperty("server.port"));
    }

}
