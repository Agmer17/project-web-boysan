package app.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDetails {
    private Integer id;
    private String username;
    private String passwordHash;
    private String role;
    private String fullName;
    private String email;
}
