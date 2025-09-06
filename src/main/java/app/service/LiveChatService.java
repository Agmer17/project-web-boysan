package app.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import app.event.PresenceEventListener;
import app.model.dto.ChatMessage;
import app.model.pojo.BaseUserDataLiveChat;
import app.model.pojo.LatestChatEntity;
import app.model.pojo.LiveChatHistoryData;
import app.repository.LiveChatRepository;
import app.repository.UserRepository;
import jakarta.mail.MessagingException;

@Service
public class LiveChatService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private LiveChatRepository chatRepo;

    @Autowired
    private MailService mailService;

    @Autowired
    private PresenceEventListener onlineUserListener;

    public List<BaseUserDataLiveChat> getOnlineAdmin() {
        Set<String> onlineAdmin = onlineUserListener.getActiveAdmins();
        List<BaseUserDataLiveChat> result = userRepo.getAdminData();

        List<BaseUserDataLiveChat> onlineAdminData = result.stream()
                .peek(admin -> {
                    if (onlineAdmin.contains(admin.getUsername())) {
                        admin.setOnline(true);
                    } else {
                        // gausah ngapa ngapain
                        // default nya udah false
                    }
                })
                .toList();

        return onlineAdminData;
    }

    @Async
    public void saveMessage(ChatMessage message) throws MessagingException {
        String htmlEmailText = """
                <div style="font-family: Arial, sans-serif; background-color:#f9f9f9; padding:20px;">
                    <div style="max-width:600px; margin:auto; background:white; border-radius:10px; box-shadow:0 2px 8px rgba(0,0,0,0.1); padding:20px;">
                        <h2 style="color:#2c3e50; text-align:center; margin-bottom:20px;">
                            📩 Pesan Baru dari %s
                        </h2>
                        <p style="font-size:16px; color:#333; line-height:1.5;">
                            " %s "
                        </p>
                        <div style="margin-top:30px; text-align:center;">
                            <a href="http://localhost:3000/live-chat"
                               style="display:inline-block; padding:12px 24px; background:#3498db; color:white; text-decoration:none; border-radius:5px; font-weight:bold;">
                                Buka Livechat
                            </a>
                        </div>
                        <p style="margin-top:20px; font-size:12px; color:#888; text-align:center;">
                            Jangan balas email ini secara langsung. Segera buka aplikasi untuk membalas pesan.
                        </p>
                    </div>
                </div>
                """
                .formatted(message.getFrom(), message.getMessage());

        String receiverEmail = chatRepo.save(message);

        if (!onlineUserListener.isUserOnline(message.getTo())) {
            mailService.sendEmail(receiverEmail, "notifikasi pesan baru", htmlEmailText);
        }

        return;

    }

    public List<LatestChatEntity> getLatestChat(String username) {
        List<LatestChatEntity> lastChat = chatRepo.getLastMessage(username);

        return lastChat;
    }

    public List<LiveChatHistoryData> getChatFrom(String currentUser, String withUser) {

        List<LiveChatHistoryData> chatHistory = chatRepo.getChatHistory(currentUser, withUser);

        return chatHistory;
    }

}
