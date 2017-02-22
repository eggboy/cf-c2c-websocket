package io.pivotal.sample.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Created by leec43 on 2/22/17.
 */
public class ServiceBWebsocketHandler extends TextWebSocketHandler {
    private static Logger logger = LoggerFactory.getLogger(ServiceBWebsocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("New session is established " + session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        String echoMessage = "ServiceB Echoing: ".concat(message.getPayload());
        logger.info(echoMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
            throws Exception {
        logger.info(exception.getMessage());
        session.close(CloseStatus.SERVER_ERROR);
    }

}
