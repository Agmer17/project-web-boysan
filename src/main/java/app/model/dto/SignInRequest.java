package app.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
    @NotBlank(message = "Username tidak boleh kosong")
    @Size(min = 5, max = 50, message = "Username harus antara 4-50 karakter")
    private String username;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank(message = "Nama lengkap tidak boleh kosong")
    @Size(min = 2, max = 50, message = "Nama lengkap harus antara 2-50 karakter")
    private String fullName;

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 5, message = "Password minimal 8 karakter")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$", message = "Password harus mengandung huruf besar, huruf kecil, dan angka")
    private String password;
}
