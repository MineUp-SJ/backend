package com.mineup.orchestrator.adapter.in.web;

import com.mineup.orchestrator.adapter.in.dto.CategoryDtoRequest;
import com.mineup.orchestrator.adapter.in.mapper.CategoryWebMapper;
import com.mineup.orchestrator.adapter.in.mapper.ProductWebMapper;
import com.mineup.orchestrator.domain.model.Category;
import com.mineup.orchestrator.domain.model.Product;
import com.mineup.orchestrator.adapter.in.dto.ProductDtoRequest;
import com.mineup.orchestrator.adapter.in.dto.CategoryDtoResponse;
import com.mineup.orchestrator.adapter.in.dto.ProductByCategoryDtoResponse;
import com.mineup.orchestrator.adapter.in.dto.ProductDtoResponse;
import com.mineup.orchestrator.application.service.CategoryService;
import com.mineup.orchestrator.application.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final CategoryWebMapper categoryWebMapper;
    private final ProductWebMapper productWebMapper;

    public CategoryController(CategoryService categoryService, ProductService productService, CategoryWebMapper categoryWebMapper, ProductWebMapper productWebMapper) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.categoryWebMapper = categoryWebMapper;
        this.productWebMapper = productWebMapper;
    }

    @Operation(summary = "Get all categories", description = "Retrieve a list of all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public Flux<CategoryDtoResponse> getCategories() {
        return categoryService.findAll()
                .map(categoryWebMapper::toDto);
    }

    @GetMapping("/{id}")
    public Flux<CategoryDtoResponse> getCategoriesByParentId(@PathVariable String id) {
        return categoryService.findAll(id)
                .map(categoryWebMapper::toDto);
    }

    @Operation(summary = "Post a category", description = "Post a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "210", description = "Successfully category created"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public Mono<CategoryDtoResponse> createCategory(@RequestBody CategoryDtoRequest category) {
        Category request = Category.builder()
                .name(category.getName())
                .build();

        return categoryService.createCategory(request, null)
                .map(categoryWebMapper::toDto);
    }

    @PostMapping("/{id}")
    public Mono<CategoryDtoResponse> createCategory(@RequestBody CategoryDtoRequest category, @PathVariable String id) {
        Category request = Category.builder()
                .name(category.getName())
                .build();

        return categoryService.createCategory(request, id)
                .map(categoryWebMapper::toDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping("/{id}/products")
    public Flux<ProductDtoResponse> getProductsByCategoryId(@PathVariable String id) {
        return productService.findAll(id)
                .map(productWebMapper::toDto);
    }

    @PostMapping("/{id}/products")
    public Mono<ProductDtoResponse> createProduct(@RequestBody ProductDtoRequest product, @PathVariable String id) {
        //todo validate product name and type
        Product request = Product.builder()
                .name(product.getName())
                .type(product.getType())
                .build();
        return productService.createProduct(request, id)
                .map(productWebMapper::toDto);
    }

    @Cacheable(value = "productByCategories", key = "#root.method.name")
    @GetMapping("/products")
    public Mono<List<ProductByCategoryDtoResponse>> getProductsByCategoryI() {

        return this.findProductsByCategory();
    }
    @Cacheable(value = "productByCategories", key = "#root.method.name")
    public Mono<List<ProductByCategoryDtoResponse>>  findProductsByCategory() {

        Sort sort = Sort.by(Sort.Order.asc("name"));
        Mono<List<CategoryDtoResponse>> categoriesMono = categoryService.findAll().map(categoryWebMapper::toDto).collectList();
        Mono<List<ProductDtoResponse>> productsMono = productService.findAll().map(productWebMapper::toDto).collectList();

        return Mono.zip(categoriesMono, productsMono)
                .map(tuple -> {
                    List<CategoryDtoResponse> categories = tuple.getT1();
                    List<ProductDtoResponse> products = tuple.getT2();

                    // Crear mapa de categorías por ID
                    Map<String, ProductByCategoryDtoResponse> categoryMap = new HashMap<>();
                    for (CategoryDtoResponse category : categories) {
                        ProductByCategoryDtoResponse dto = new ProductByCategoryDtoResponse();
                        dto.setId(category.getId());
                        dto.setName(category.getName());
                        categoryMap.put(category.getId(), dto);
                    }

                    // Asociar productos a su categoría
                    for (ProductDtoResponse product : products) {
                        ProductByCategoryDtoResponse dto = categoryMap.get(product.getCategoryId());
                        if (dto != null) {
                            dto.getProducts().add(product);
                        }
                    }
                    // Construir árbol de categorías
                    List<ProductByCategoryDtoResponse> rootCategories = new ArrayList<>();
                    for (CategoryDtoResponse category : categories) {
                        ProductByCategoryDtoResponse dto = categoryMap.get(category.getId());
                        if (category.getParentId() == null) {
                            rootCategories.add(dto);
                        } else {
                            ProductByCategoryDtoResponse parentDto = categoryMap.get(category.getParentId());
                            if (parentDto != null) {
                                parentDto.getCategories().add(dto);
                            }
                        }
                    }

                    return rootCategories;
                });
    }
/*
    @PutMapping("/{id}")
    public ResponseEntity<Mineral> updateMineral(@PathVariable Long id, @RequestBody Mineral mineral) {
        return ResponseEntity.ok(mineralService.updateMineral(id, mineral));
    }


    }*/
}
