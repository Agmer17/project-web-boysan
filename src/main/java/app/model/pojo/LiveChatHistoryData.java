package app.model.pojo;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LiveChatHistoryData {
    private UUID messageId;
    private boolean ownMessage;
    private String message;
    private String otherUsername;
    private String otherFullName;
    private String otherProfilePicture;
    private String otherRole;
}
