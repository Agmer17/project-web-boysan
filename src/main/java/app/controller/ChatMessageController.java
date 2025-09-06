package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import app.model.dto.ChatMessage;
import app.model.pojo.BaseUserDataLiveChat;
import app.model.pojo.LatestChatEntity;
import app.model.pojo.LiveChatHistoryData;
import app.service.LiveChatService;
import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatMessageController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private LiveChatService service;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor)
            throws MessagingException {
        String sender = (String) headerAccessor.getSessionAttributes().get("username");

        message.setFrom(sender);

        service.saveMessage(message); // async ajah gaperlu nunggu ampe beres brok
                                      // lama soalnya wkwkwkwkk
        messagingTemplate.convertAndSendToUser(sender, "/queue/messages", message);

        // set ke false dulu bair di fe rendernya bener
        message.setOwnMessage(false);
        messagingTemplate.convertAndSendToUser(message.getTo(), "/queue/messages", message);

    }

    @GetMapping("/live-chat/active-admin")
    @ResponseBody
    public List<BaseUserDataLiveChat> getActiveAdmins() {
        List<BaseUserDataLiveChat> adminData = service.getOnlineAdmin();
        return adminData;
    }

    @GetMapping("/live-chat/latest")
    @ResponseBody
    public List<LatestChatEntity> getLatestChat(@SessionAttribute(name = "claims", required = true) Claims claims) {
        String currentUsername = claims.get("username", String.class);

        System.out.println("\n\n\n\n\n" + currentUsername + "\n\n\n\n\n\n\n\n\n");

        List<LatestChatEntity> latestChat = service.getLatestChat(currentUsername);

        return latestChat;
    }

    @GetMapping("/live-chat/message")
    @ResponseBody
    public List<LiveChatHistoryData> getChatHistory(@RequestParam("with") String withUser,
            @SessionAttribute(required = true) Claims claims) {

        String currentUser = claims.get("username", String.class);

        List<LiveChatHistoryData> result = service.getChatFrom(currentUser, withUser);

        return result;
    }

}
