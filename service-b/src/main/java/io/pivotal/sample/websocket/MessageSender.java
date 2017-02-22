package io.pivotal.sample.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * Created by leec43 on 2/23/17.
 */
@Component
public class MessageSender {

    @Autowired
    WebSocketSession webSocketSession;

    @Autowired
    public MessageSender(WebSocketSession webSocketSession) {
    }

    public void sendMessage(String message) {
        try {
            webSocketSession.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
