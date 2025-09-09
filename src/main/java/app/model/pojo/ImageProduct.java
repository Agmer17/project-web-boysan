package app.model.pojo;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageProduct {
    private UUID id;
    private UUID serviceId;
    private String imageUrl;
}