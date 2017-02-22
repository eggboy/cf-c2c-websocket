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
public class ServiceAWebsocketHandler extends TextWebSocketHandler {
    private static Logger logger = LoggerFactory.getLogger(ServiceAWebsocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.debug("Opened new session in instance " + this);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        String echoMessage = "ServiceA Echoing: ".concat(message.getPayload());
        logger.debug(echoMessage);
        session.sendMessage(new TextMessage(echoMessage));

        //session.close(CloseStatus.NORMAL);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
            throws Exception {
        logger.debug(exception.getMessage());
        session.close(CloseStatus.SERVER_ERROR);
    }

}
