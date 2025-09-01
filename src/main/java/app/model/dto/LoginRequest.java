package app.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "username tidak boleh kosong")
    @Size(min = 5, max = 50, message = "username harus lebih dari 5 karakter")
    private String username;

    @NotBlank(message = "password tidak boleh kosong")
    @Size(min = 5, message = "password harus lebih dari 5 karakter")
    private String password;
}
