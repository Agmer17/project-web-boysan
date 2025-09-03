package app.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import app.event.PresenceEventListener;
import app.model.dto.ChatMessage;
import app.model.entity.BaseUserDataLiveChat;
import app.service.LiveChatService;
import app.service.MailService;
import jakarta.mail.MessagingException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatMessageController {

    @Autowired
    private PresenceEventListener onlineAdminListener;

    @Autowired
    private LiveChatService liveChatService;

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

        messagingTemplate.convertAndSendToUser(message.getTo(), "/queue/messages", message);
        messagingTemplate.convertAndSendToUser(sender, "/queue/messages", message);

    }

    @GetMapping("/live-chat/active-admin")
    @ResponseBody
    public List<BaseUserDataLiveChat> getActiveAdmins() {
        Set<String> onlineAdminData = onlineAdminListener.getActiveAdmins();
        List<BaseUserDataLiveChat> adminData = liveChatService.getOnlineAdmin(onlineAdminData);
        return adminData;
    }

    @GetMapping("/get-message")
    @ResponseBody
    public String getLiveChatFrom(@RequestParam(name = "from") String sender) {
        return sender;
    }

}
