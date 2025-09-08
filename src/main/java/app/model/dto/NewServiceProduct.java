package app.model.dto;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class NewServiceProduct {

    @NotBlank(message = "kategori tidak boleh kosong. Jika tidak ada yang cocok, kamu bisa menambahkannya")
    private String categoryId;

    @NotBlank(message = "nama tidak boleh kosong")
    @Size(min = 4, max = 150, message = "nama harus 4 sampai 150 karakter")
    private String name;

    private String desc;

    private BigDecimal price;

    private List<MultipartFile> images;
}
