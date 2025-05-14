package Day5.ProductApplication.src.main.java.com.example.ProductApplication.Mapper;

import com.example.ProductApplication.DTO.ProductDto;
import com.example.ProductApplication.Entity.Product;

public class ProductMapper {
    public static ProductDto toDto(Product product)
    {
        return new ProductDto(0, product.getName(), product.getPrice(), product.getDescription());
    }

    public static Product dtoToEntity(ProductDto productDto)
    {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(product.getDescription());
        return product;
    }
}
