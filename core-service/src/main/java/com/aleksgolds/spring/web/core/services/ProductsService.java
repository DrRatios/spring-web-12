package com.aleksgolds.spring.web.core.services;

import com.aleksgolds.spring.web.api.exceptions.ResourceNotFoundException;
import com.aleksgolds.spring.web.api.dto.ProductDto;
import com.aleksgolds.spring.web.core.entities.ProductEntity;
import com.aleksgolds.spring.web.core.repositories.ProductsRepository;
import com.aleksgolds.spring.web.core.repositories.specifications.ProductsSpecifications;
import com.aleksgolds.spring.web.core.soap.products.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;

    public Page<ProductEntity> findAll(Integer minPrice, Integer maxPrice, String categoryNamePart, String partTitle, Integer page) {
        Specification<ProductEntity> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (partTitle != null) {
            spec = spec.and(ProductsSpecifications.titleLike(partTitle));
        }
        if (categoryNamePart != null) {
            spec = spec.and(ProductsSpecifications.categoryLike(categoryNamePart));
        }

        return productsRepository.findAll(spec, PageRequest.of(page - 1, 10));
    }

    public Optional<ProductEntity> findById(Long id) {
        return productsRepository.findById(id);
    }

    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }

    public ProductEntity save(ProductEntity product) {
        return productsRepository.save(product);
    }

    public static final Function<ProductEntity, Product> functionEntityToSoap = productEntity -> {
        Product s = new Product();
        s.setId(productEntity.getId());
        s.setTitle(productEntity.getTitle());
        s.setPrice(productEntity.getPrice());
        return s;
    };
    public Product getProductById(Long productId) {
        return productsRepository.findById(productId).map(functionEntityToSoap).get();
    }
    public List<Product> getAllProduct() {
        return productsRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }

    @Transactional
    public ProductEntity update(ProductDto productDto) {
        ProductEntity product = productsRepository.findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить продукта, не надйен в базе, id: " + productDto.getId()));
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        return product;
    }
}
