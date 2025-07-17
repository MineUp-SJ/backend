package com.mineup.orchestrator.application.service;

import com.mineup.orchestrator.adapter.in.mapper.ProductWebMapper;
import com.mineup.orchestrator.domain.model.Product;
import com.mineup.orchestrator.domain.port.in.IProductService;
import com.mineup.orchestrator.domain.port.out.ProductRepository;

import com.mineup.orchestrator.domain.exceptions.ResourceAlreadyExistsException;
import com.mineup.orchestrator.utils.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ProductWebMapper productWebMapper;

    public ProductService(ProductRepository productRepository, ProductWebMapper productWebMapper) {
        this.productRepository = productRepository;
        this.productWebMapper = productWebMapper;
    }

    public Flux<Product> findAll() {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return productRepository.findAllByIsDeleted(false, sort);
    }
    public Flux<Product> findAll(String categoryId) {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return productRepository.findAllByIsDeletedAndCategoryId(false, sort, categoryId);
    }
    @CacheEvict(value = "productByCategories", allEntries = true)
    public Mono<Product> createProduct(Product product, String categoryId) {

        String nameWithoutAccents = StringUtils.removeAccents(product.getName());
        String nameUpperCase = nameWithoutAccents.toUpperCase();
        product.setName(nameUpperCase);
        return productRepository.findByNameAndIsDeletedAndCategoryId(product.getName(), false, categoryId)
                .flatMap(productExists -> Mono.<Product>error(new ResourceAlreadyExistsException("Product already exists and is not deleted")))
                .switchIfEmpty(saveProduct(product,categoryId));
    }
    @CacheEvict(value = "productByCategories", allEntries = true)
    public void deleteProduct(String id) {
        productRepository.findById(id)
                .flatMap(product -> {
                    product.setIsDeleted(true);
                    return productRepository.save(product);
                })
                .subscribe();
    }
    //aditional methods
    private Mono<Product> saveProduct(Product product, String categoryId) {
        product.setCategoryId(categoryId);
        return productRepository.save(product);
    }
}
