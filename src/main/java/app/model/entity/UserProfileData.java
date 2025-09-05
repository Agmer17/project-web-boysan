package app.model.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileData {
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String profilePicture;
    private String gender;
    private LocalDateTime createdAt;
}
