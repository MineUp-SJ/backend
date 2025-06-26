package com.mineup.orchestrator.services.impl;

import com.mineup.orchestrator.dto.requests.CategoryDtoRequest;
import com.mineup.orchestrator.dto.requests.MembershipDtoRequest;
import com.mineup.orchestrator.dto.responses.CategoryDtoResponse;
import com.mineup.orchestrator.dto.responses.MembershipDtoResponse;
import com.mineup.orchestrator.dto.responses.ProductByCategoryDtoResponse;
import com.mineup.orchestrator.dto.responses.ProductDtoResponse;
import com.mineup.orchestrator.exceptions.ResourceAlreadyExistsException;
import com.mineup.orchestrator.mappers.spec.ICategoryMapper;
import com.mineup.orchestrator.mappers.spec.IMembershipMapper;
import com.mineup.orchestrator.models.Membership;
import com.mineup.orchestrator.repositories.CategoryRepository;
import com.mineup.orchestrator.repositories.MembershipRepository;
import com.mineup.orchestrator.services.spec.ICategoryService;
import com.mineup.orchestrator.services.spec.IMembershipService;
import com.mineup.orchestrator.utils.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final ICategoryMapper categoryMapper;
    private final ProductService productService;

    public CategoryService(CategoryRepository categoryRepository, ICategoryMapper categoryMapper, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.productService = productService;
    }

    public Flux<CategoryDtoResponse> findAll() {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return categoryRepository.findAllByIsDeleted(false, sort)
                .flatMap(entity-> Flux.just(categoryMapper.toDto(entity)));
    }
    public Flux<CategoryDtoResponse> findAll(String parentId) {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return categoryRepository.findAllByIsDeletedAndParentId(false, sort, parentId)
                .flatMap(entity-> Flux.just(categoryMapper.toDto(entity)));
    }
    @CacheEvict(value = "productByCategories", allEntries = true)
    public Mono<CategoryDtoResponse> createCategory(CategoryDtoRequest category, String parentId) {

        String nameWithoutAccents = StringUtils.removeAccents(category.getName());
        String nameUpperCase = nameWithoutAccents.toUpperCase();
        category.setName(nameUpperCase);
        return categoryRepository.findByNameAndIsDeletedAndParentId(category.getName(), false, parentId)
                .flatMap(ExistMembership -> Mono.<CategoryDtoResponse>error(new ResourceAlreadyExistsException("Membership already exists and is not deleted")))
                .switchIfEmpty(saveCategory(category,parentId));
    }

    @CacheEvict(value = "productByCategories", allEntries = true)
    public void deleteCategory(String id) {
        categoryRepository.findById(id)
                .flatMap(category -> {
                    category.setIsDeleted(true);
                    return categoryRepository.save(category);
                })
                .subscribe();
    }

    private Mono<CategoryDtoResponse> saveCategory(CategoryDtoRequest category, String parentId) {
        return categoryRepository.save(
                categoryMapper.toEntity(category,parentId))
                .map(categoryMapper::toDto);
    }

    @Cacheable(value = "productByCategories", key = "#root.method.name")
    public Mono<List<ProductByCategoryDtoResponse>>  findProductsByCategory() {

        Sort sort = Sort.by(Sort.Order.asc("name"));
        Mono<List<CategoryDtoResponse>> categoriesMono = findAll().collectList();
        Mono<List<ProductDtoResponse>> productsMono = productService.findAll().collectList();

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

}
