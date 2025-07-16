package com.mineup.orchestrator.adapter.out.persistence.mongo.adapter;

import com.mineup.orchestrator.adapter.out.persistence.mongo.repository.SpringDataProductRepository;
import com.mineup.orchestrator.adapter.out.persistence.mongo.mapper.ProductPersistenceMapper;
import com.mineup.orchestrator.domain.model.Product;
import com.mineup.orchestrator.domain.port.out.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductRepositoryAdapter implements ProductRepository {

    SpringDataProductRepository springDataProductRepository;
    ProductPersistenceMapper productPersistenceMapper;

    public ProductRepositoryAdapter(SpringDataProductRepository springDataProductRepository, ProductPersistenceMapper productPersistenceMapper) {
        this.springDataProductRepository = springDataProductRepository;
        this.productPersistenceMapper = productPersistenceMapper;
    }

    @Override
    public Flux<Product> findAllByIsDeleted(Boolean isDeleted, Sort sort) {
        return springDataProductRepository.findAllByIsDeleted(isDeleted, sort)
                .map(productDocument -> productPersistenceMapper.toDomain(productDocument));
    }

    @Override
    public Flux<Product> findAllByIsDeletedAndCategoryId(Boolean isDeleted, Sort sort, String categoryId) {
        return springDataProductRepository.findAllByIsDeletedAndCategoryId(isDeleted, sort, categoryId)
                .map(productDocument -> productPersistenceMapper.toDomain(productDocument));
    }

    @Override
    public Mono<Product> findByNameAndIsDeletedAndCategoryId(String name, Boolean isDeleted, String categoryId) {
        return springDataProductRepository.findByNameAndIsDeletedAndCategoryId(name, isDeleted, categoryId)
                .map(productDocument -> productPersistenceMapper.toDomain(productDocument));
    }
  /*  @Override
    public Mono<Product> save (Product category, String categoryId) {
        return springDataProductRepository.save(productPersistenceMapper.toDocument(category, categoryId))
                .map(productDocument -> productPersistenceMapper.toDomain(productDocument));
    }*/
    @Override
    public Mono<Product> save (Product product) {
        return springDataProductRepository.save(productPersistenceMapper.toDocument(product))
                .map(productDocument -> productPersistenceMapper.toDomain(productDocument));
    }
    @Override
    public Mono<Product> findById(String id) {
        return springDataProductRepository.findById(id)
                .map(productDocument -> productPersistenceMapper.toDomain(productDocument));
    }
}
