package app.model.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseServiceProduct {
    private UUID id;
    private UUID categoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime createdAt;
}
