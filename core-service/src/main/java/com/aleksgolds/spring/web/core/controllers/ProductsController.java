package com.aleksgolds.spring.web.core.controllers;

import com.aleksgolds.spring.web.api.exceptions.CartServiceAppError;
import com.aleksgolds.spring.web.api.exceptions.ResourceNotFoundException;
import com.aleksgolds.spring.web.core.converters.ProductConverter;
import com.aleksgolds.spring.web.api.dto.core.ProductDto;
import com.aleksgolds.spring.web.core.entities.ProductEntity;
import com.aleksgolds.spring.web.core.integrations.CartServiceIntegration;
import com.aleksgolds.spring.web.core.services.ProductsService;
import com.aleksgolds.spring.web.core.validators.ProductValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Методы работы с продуктами")
public class ProductsController {
    private final ProductsService productsService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;

    @Operation(
            summary = "Запрос на получение всех продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешное получение ответа", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            }
    )
    @GetMapping
    public Page<ProductDto> getAllProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @RequestParam(name = "category_name_part", required = false) String categoryNamePart,
            @RequestParam(name = "title_part", required = false) String titlePart
    ) {
        if (page < 1) {
            page = 1;
        }
        return productsService.findAll(minPrice, maxPrice, categoryNamePart, titlePart, page).map(
                productConverter::entityToDto
        );
    }

    @Operation(
            summary = "Запрос на получение модели продукта пo ID",
            responses = {
                    @ApiResponse(
                            description = "Успешное получение ответа", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Плохой запрос", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = CartServiceAppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ProductDto getProductById(
            @PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id) {
        ProductEntity product = productsService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
        return productConverter.entityToDto(product);
    }

    @Operation(
            summary = "Запрос на сохранение нового продукта в БД",
            responses = {
                    @ApiResponse(
                            description = "Успешное получение ответа", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    @PostMapping
    public ProductDto saveNewProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        ProductEntity product = productConverter.dtoToEntity(productDto);
        product = productsService.save(product);
        return productConverter.entityToDto(product);
    }

    @Operation(
            summary = "Запрос на обновление продукта в БД",
            responses = {
                    @ApiResponse(
                            description = "Успешное получение ответа", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        ProductEntity product = productsService.update(productDto);
        return productConverter.entityToDto(product);
    }

    @Operation(
            summary = "Запрос на удаление продукта в БД по ID",
            responses = {
                    @ApiResponse(
                            description = "Успешное получение ответа", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable
                           @Parameter(description = "Идентификатор продукта", required = true) Long id) {
        productsService.deleteById(id);
    }
}
