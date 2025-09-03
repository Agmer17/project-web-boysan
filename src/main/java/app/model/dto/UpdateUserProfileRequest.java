package app.model.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserProfileRequest {
    @NotBlank(message = "Full name tidak boleh kosong")
    @Size(max = 100, message = "Full name maksimal 100 karakter")
    private String fullName;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank
    @Pattern(regexp = "pria|wanita|lainnya")
    private String gender;

    private MultipartFile profilePicture;
}
