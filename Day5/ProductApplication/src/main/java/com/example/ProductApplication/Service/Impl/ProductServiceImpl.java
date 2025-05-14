package Day5.ProductApplication.src.main.java.com.example.ProductApplication.Service.Impl;

import com.example.ProductApplication.DTO.ProductDto;
import com.example.ProductApplication.Entity.Product;
import com.example.ProductApplication.Exception.InvalidProductException;
import com.example.ProductApplication.Exception.ProductNotFoundException;
import com.example.ProductApplication.Mapper.ProductMapper;
import com.example.ProductApplication.Repository.ProductRepository;
import com.example.ProductApplication.Service.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Service
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public String saveProduct(ProductDto dto) {
        if (dto.getPrice() <= 0) {
            throw new InvalidProductException("Product price must be greater than 0.");
        }
        Product product = ProductMapper.dtoToEntity(dto);
        productRepository.save(product);
        return "Product Added Successfully";
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(int id) {
        return productRepository.findById(id).map(ProductMapper::toDto).orElseThrow(()-> new ProductNotFoundException("No Product with this id "));
    }

    @Override
    public ProductDto updateProduct(int productId, ProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("No Product with this id "));

        if (productDto.getName() != null && !Objects.equals(product.getName(), productDto.getName())) {
            product.setName(productDto.getName());
        }
        if (productDto.getDescription() != null && !Objects.equals(product.getDescription(), productDto.getDescription())) {
            product.setDescription(productDto.getDescription());
        }
        if (productDto.getPrice() > 0 && product.getPrice() != productDto.getPrice()) {
            product.setPrice(productDto.getPrice());
        }

        productRepository.save(product);
        return ProductMapper.toDto(product);
    }

    @Override
    public String deleteProduct(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("No Product with this id "));
        productRepository.delete(product);
        return "Deleted Successfully";
    }
}
