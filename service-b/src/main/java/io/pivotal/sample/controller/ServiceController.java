package io.pivotal.sample.controller;

import io.pivotal.sample.websocket.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by leec43 on 2/23/17.
 */
@RestController
public class ServiceController {

    @Autowired
    MessageSender messageSender;

    public ServiceController(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @RequestMapping("/send/{message}")
    public void sendMessage(@PathVariable String message) {
        messageSender.sendMessage(message);
    }
}
