package app.event;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class PresenceEventListener {

    private final Set<String> activeAdmins = ConcurrentHashMap.newKeySet();

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) accessor.getSessionAttributes().get("username");
        String role = (String) accessor.getSessionAttributes().get("role");

        if (username != null && "admin".equals(role)) {
            activeAdmins.add(username);
            System.out.println("Admin online: " + username);
        }

        // System.out.println(activeAdmins);
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) accessor.getSessionAttributes().get("username");
        String role = (String) accessor.getSessionAttributes().get("role");

        if (username != null && isAdmin(role)) {
            activeAdmins.remove(username);
        }
    }

    public Set<String> getActiveAdmins() {
        return activeAdmins;
    }

    private boolean isAdmin(String role) {
        // cek dari database / JWT claim / prefix
        return role.equalsIgnoreCase("admin");
    }
}
