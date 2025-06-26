package com.mineup.orchestrator.controllers;

import com.mineup.orchestrator.dto.requests.CategoryDtoRequest;
import com.mineup.orchestrator.dto.requests.MineralDtoRequest;
import com.mineup.orchestrator.dto.requests.ProductDtoRequest;
import com.mineup.orchestrator.dto.responses.CategoryDtoResponse;
import com.mineup.orchestrator.dto.responses.MineralDtoResponse;
import com.mineup.orchestrator.dto.responses.ProductByCategoryDtoResponse;
import com.mineup.orchestrator.dto.responses.ProductDtoResponse;
import com.mineup.orchestrator.services.impl.CategoryService;
import com.mineup.orchestrator.services.impl.MineralService;
import com.mineup.orchestrator.services.impl.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
         this.categoryService = categoryService;
        this.productService = productService;
    }

    @Operation(summary = "Get all categories", description = "Retrieve a list of all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Flux<CategoryDtoResponse>> getCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flux<CategoryDtoResponse>> getCategoriesByParentId(@PathVariable String id) {
        return ResponseEntity.ok(categoryService.findAll(id));
    }

  @Operation(summary = "Post a category", description = "Post a category")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "210", description = "Successfully category created"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
    @PostMapping
    public ResponseEntity<Mono<CategoryDtoResponse>> createCategory(@RequestBody CategoryDtoRequest category) {
        return ResponseEntity.ok(categoryService.createCategory(category,null));
    }
    @PostMapping("/{id}")
    public ResponseEntity<Mono<CategoryDtoResponse>> createCategory(@RequestBody CategoryDtoRequest category,@PathVariable String id) {
        return ResponseEntity.ok(categoryService.createCategory(category,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/products")
    public ResponseEntity<Flux<ProductDtoResponse>> getProductsByCategoryId(@PathVariable String id) {
        return ResponseEntity.ok(productService.findAll(id));
    }
    @PostMapping("/{id}/products")
    public ResponseEntity<Mono<ProductDtoResponse>> createProduct(@RequestBody ProductDtoRequest product, @PathVariable String id) {
        return ResponseEntity.ok(productService.createProduct(product,id));
    }
    @GetMapping("/products")
    public ResponseEntity<Mono<List<ProductByCategoryDtoResponse>>> getProductsByCategoryI() {
        return ResponseEntity.ok(categoryService.findProductsByCategory());
    }
/*
    @PutMapping("/{id}")
    public ResponseEntity<Mineral> updateMineral(@PathVariable Long id, @RequestBody Mineral mineral) {
        return ResponseEntity.ok(mineralService.updateMineral(id, mineral));
    }


    }*/
}
