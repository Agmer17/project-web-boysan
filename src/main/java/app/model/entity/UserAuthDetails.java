package app.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDetails {

    private String username;
    private String passwordHash;
    private String role;
    private String fullName;
    private String image_url;
}
