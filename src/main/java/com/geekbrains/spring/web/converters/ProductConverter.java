package com.geekbrains.spring.web.converters;

import com.geekbrains.spring.web.dto.ProductDto;
import com.geekbrains.spring.web.entities.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public ProductEntity dtoToEntity(ProductDto productDto) {
        return new ProductEntity(productDto.getId(), productDto.getTitle(), productDto.getPrice());
    }

    public ProductDto entityToDto(ProductEntity product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }
}
