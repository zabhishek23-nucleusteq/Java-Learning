package com.example.UserProductApplication.Mapper;

import com.example.UserProductApplication.DTO.ProductDto;
import com.example.UserProductApplication.Entity.Product;

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
