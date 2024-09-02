package org.thirtysix.talentnexus.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @MessageMapping("/notify")
    @SendTo("/topic/notifications")
    public String notifyCompany(String message) {
        // 这里可以处理接收到的消息
        return message;
    }
}
