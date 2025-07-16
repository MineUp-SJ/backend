package com.mineup.orchestrator.adapter.out.persistence.mongo.repository;

import com.mineup.orchestrator.adapter.out.persistence.mongo.document.ProductDocument;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SpringDataProductRepository extends ReactiveCrudRepository<ProductDocument, String> {
   // Flux<ProductDocument> findAllByName();
    Flux<ProductDocument> findAllByIsDeleted(Boolean isDeleted, Sort sort);
    Flux<ProductDocument> findAllByIsDeletedAndCategoryId(Boolean isDeleted, Sort sort,String categoryId);
    Mono<ProductDocument> findByNameAndIsDeletedAndCategoryId(String name, Boolean isDeleted, String categoryId);
    //Mono<ProductDocument> findByName(String name);
}
