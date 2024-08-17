package com.youyk.anchoreerchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@EnableAsync
@SpringBootApplication
public class AnchoreerChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnchoreerChatApplication.class, args);
	}

}
