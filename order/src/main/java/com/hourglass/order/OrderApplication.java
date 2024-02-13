package com.hourglass.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Slf4j
@ComponentScan("com.hourglass")
@EnableFeignClients
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(OrderApplication.class);
        Environment env = application.run(args).getEnvironment();
        log.info("ticket module is running at port: {}", env.getProperty("server.port"));
    }

}
