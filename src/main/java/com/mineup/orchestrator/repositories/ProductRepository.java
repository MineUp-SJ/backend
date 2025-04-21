package com.mineup.orchestrator.repositories;

import com.mineup.orchestrator.models.Category;
import com.mineup.orchestrator.models.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, String> {
    Flux<Product> findAllByName();
    Flux<Product> findAllByIsDeleted(Boolean isDeleted, Sort sort);
    Flux<Product> findAllByIsDeletedAndCategoryId(Boolean isDeleted, Sort sort,String categoryId);
    Mono<Product> findByNameAndIsDeletedAndCategoryId(String name, Boolean isDeleted, String categoryId);
    Mono<Product> findByName(String name);
}
