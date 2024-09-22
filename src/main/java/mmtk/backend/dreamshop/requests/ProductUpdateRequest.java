package mmtk.backend.dreamshop.requests;

import lombok.Data;
import mmtk.backend.dreamshop.models.Category;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
