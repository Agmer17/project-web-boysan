package app.model.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UpdateUserProfileRequest {
    @NotBlank(message = "Full name tidak boleh kosong")
    @Size(max = 100, message = "Full name maksimal 100 karakter", min = 5)
    private String fullName;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank
    @Pattern(regexp = "pria|wanita|lainnya")
    private String gender;

    @NotBlank
    @Pattern(regexp = "\\+?\\d{10,15}", message = "Nomor telepon tidak valid")
    private String phoneNumber;

    private MultipartFile profilePicture;
}
