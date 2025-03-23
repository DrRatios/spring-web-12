package com.aleksgolds.spring.web.core.converters;

import com.aleksgolds.spring.web.api.dto.core.ProductDto;
import com.aleksgolds.spring.web.core.entities.ProductEntity;
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
