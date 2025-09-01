package app.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.dto.ChatMessage;
import app.model.entity.BaseUserDataLiveChat;
import app.repository.LiveChatRepository;
import app.repository.UserRepository;

@Service
public class LiveChatService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private LiveChatRepository chatRepo;

    public List<BaseUserDataLiveChat> getOnlineAdmin(Set<String> onlineAdmin) {
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

    public void saveMessage(ChatMessage message) {
        chatRepo.save(message);

        return;

    }

}
