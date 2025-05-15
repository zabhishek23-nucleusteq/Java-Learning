package com.example.UserProductApplication.Service;

import com.example.UserProductApplication.DTO.ProductDto;

import java.util.List;

public interface IProductService {
    public String saveProduct(ProductDto dto);
    public List<ProductDto> getAllProducts();
    public ProductDto getProductById(int id);
    public ProductDto updateProduct(int productId,ProductDto productDto);
    public String deleteProduct(int id);
}
