package app.model.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductEntity {
    private UUID categoryId;
    private String categoryName;
    private UUID serviceId;
    private String serviceName;
    private String serviceDescription;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private List<String> imageUrl;
}
