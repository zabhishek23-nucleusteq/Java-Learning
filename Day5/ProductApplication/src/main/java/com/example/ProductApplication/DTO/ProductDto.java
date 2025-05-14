package Day5.ProductApplication.src.main.java.com.example.ProductApplication.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private int Id;
    @NotNull
    private String name;
    @NotNull
    private float price;
    private String description;
}
