package app.event;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class PresenceEventListener {

    private final Map<String, String> activeUser = new ConcurrentHashMap<>();

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) accessor.getSessionAttributes().get("username");
        String role = (String) accessor.getSessionAttributes().get("role");

        if (username != null && role != null) {
            activeUser.put(username, role);
        }
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) accessor.getSessionAttributes().get("username");

        if (username != null) {
            activeUser.remove(username);
        }
    }

    public Set<String> getActiveAdmins() {
        return activeUser.entrySet().stream()
                .filter(entry -> isAdmin(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public boolean isUserOnline(String username) {
        return activeUser.containsKey(username);
    }

    private boolean isAdmin(String role) {
        return "admin".equalsIgnoreCase(role);
    }
}
