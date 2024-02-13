package com.hourglass.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Slf4j
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(GatewayApplication.class);
		Environment env = application.run(args).getEnvironment();
		log.info("gateway module is running at port: {}", env.getProperty("server.port"));
	}

}
