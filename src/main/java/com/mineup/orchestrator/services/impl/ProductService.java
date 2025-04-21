package com.mineup.orchestrator.services.impl;

import com.mineup.orchestrator.dto.requests.ProductDtoRequest;
import com.mineup.orchestrator.dto.responses.ProductDtoResponse;
import com.mineup.orchestrator.exceptions.ResourceAlreadyExistsException;
import com.mineup.orchestrator.mappers.spec.IProductMapper;
import com.mineup.orchestrator.repositories.ProductRepository;
import com.mineup.orchestrator.services.spec.IProductService;
import com.mineup.orchestrator.utils.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final IProductMapper productMapper;

    public ProductService(ProductRepository productRepository, IProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Flux<ProductDtoResponse> findAll() {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return productRepository.findAllByIsDeleted(false, sort)
                .flatMap(entity-> Flux.just(productMapper.toDto(entity)));
    }
    public Flux<ProductDtoResponse> findAll(String categoryId) {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return productRepository.findAllByIsDeletedAndCategoryId(false, sort, categoryId)
                .flatMap(entity-> Flux.just(productMapper.toDto(entity)));
    }
    public Mono<ProductDtoResponse> createProduct(ProductDtoRequest product, String categoryId) {

        String nameWithoutAccents = StringUtils.removeAccents(product.getName());
        String nameUpperCase = nameWithoutAccents.toUpperCase();
        product.setName(nameUpperCase);
        return productRepository.findByNameAndIsDeletedAndCategoryId(product.getName(), false, categoryId)
                .flatMap(ExistMembership -> Mono.<ProductDtoResponse>error(new ResourceAlreadyExistsException("Product already exists and is not deleted")))
                .switchIfEmpty(saveProduct(product,categoryId));
    }

    public void deleteProduct(String id) {
        productRepository.findById(id)
                .flatMap(product -> {
                    product.setIsDeleted(true);
                    return productRepository.save(product);
                })
                .subscribe();
    }
    //aditional methods
    private Mono<ProductDtoResponse> saveProduct(ProductDtoRequest product, String categoryId) {
        return productRepository.save(
                        productMapper.toEntity(product,categoryId))
                .map(productMapper::toDto);
    }
}
