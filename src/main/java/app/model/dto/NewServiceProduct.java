package app.model.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import app.model.common.CommonProductService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewServiceProduct extends CommonProductService {

    private List<MultipartFile> images;
}
