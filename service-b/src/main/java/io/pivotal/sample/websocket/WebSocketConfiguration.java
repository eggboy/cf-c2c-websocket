package io.pivotal.sample.websocket;

import io.pivotal.sample.registry.EurekaServiceRegistry;
import io.pivotal.sample.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by leec43 on 2/23/17.
 */
@Configuration
public class WebSocketConfiguration {

    private static String webSocketUrl = "ws://%s:80/channel";
    private static Logger logger = LoggerFactory.getLogger(WebSocketConfiguration.class);

    @Autowired
    ServiceRegistry eurekaServiceRegistry;

    @Autowired
    SockJsClient sockJsClient;

    @Bean
    ServiceRegistry eurekaServiceRegistry() {
        return new EurekaServiceRegistry();
    }

    @Bean
    SockJsClient sockJsClient() {
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

        List<Transport> transports = new ArrayList<>(2);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());

        SockJsClient sockJsClient = new SockJsClient(transports);
        sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());

        return sockJsClient;
    }

    @Bean
    public WebSocketSession getWebSocketSession() {
        ListenableFuture<WebSocketSession> future = sockJsClient.doHandshake(new ServiceBWebsocketHandler(), getServiceUrl());

        future.addCallback(new ListenableFutureCallback<WebSocketSession>() {
            @Override
            public void onSuccess(WebSocketSession result) {
                System.out.println("callback onSuccess:" + result.getId());
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println(ex.getCause());
                System.out.println("callback onFailure:" + ex.getMessage());
                ex.printStackTrace();
            }

        });

        WebSocketSession session = null;
        try {
            session = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return session;
    }

    public String getServiceUrl() {
        String endpointUrl;
        String serviceHostName = eurekaServiceRegistry.getHostByServiceName("serviceA");

        if (serviceHostName == null || serviceHostName.equals(""))
            endpointUrl = String.format(webSocketUrl, "localhost");
        else endpointUrl = String.format(webSocketUrl, serviceHostName);

        logger.info("#################" + endpointUrl);

        return endpointUrl;
    }
}
