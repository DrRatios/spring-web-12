package com.aleksgolds.spring.web.core.repositories.specifications;

import com.aleksgolds.spring.web.core.entities.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

public class ProductsSpecifications {

    public static Specification<ProductEntity> priceGreaterOrEqualsThan(Integer price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<ProductEntity> priceLessThanOrEqualsThan(Integer price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<ProductEntity> titleLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }

    public static Specification<ProductEntity> categoryLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.join("categories").get("name"), String.format("%%%s%%", titlePart));
    }

}
