package app.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserDataLiveChat {
    private String username;
    private String fullName;
    private String role;
    private String imageUrl;
    private boolean online;

}
