package io.pivotal.sample;

import io.pivotal.sample.websocket.ServiceAWebsocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebSocket
public class ServiceAApplication extends SpringBootServletInitializer
		implements WebSocketConfigurer {

	private static Logger logger = LoggerFactory.getLogger(ServiceAApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServiceAApplication.class, args);
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
		//webSocketHandlerRegistry.addHandler(serviceAWebsocketHandler(), "/channel").setAllowedOrigins("*");
		webSocketHandlerRegistry.addHandler(serviceAWebsocketHandler(), "/channel").withSockJS();
	}

	@Bean
	public WebSocketHandler serviceAWebsocketHandler() {
		return new ServiceAWebsocketHandler();
	}
}
