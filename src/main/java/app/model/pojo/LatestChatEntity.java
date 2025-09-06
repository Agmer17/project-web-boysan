package app.model.pojo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LatestChatEntity {
    private String message;
    private LocalDateTime createdAt;
    private String username;
    private String fullName;
    private String profilePicture;
    private String role;
}
