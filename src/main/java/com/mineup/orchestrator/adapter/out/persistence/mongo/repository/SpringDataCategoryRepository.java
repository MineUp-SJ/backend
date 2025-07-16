package com.mineup.orchestrator.adapter.out.persistence.mongo.repository;

import com.mineup.orchestrator.adapter.out.persistence.mongo.document.CategoryDocument;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Mono;

@Repository
public interface SpringDataCategoryRepository  extends ReactiveCrudRepository<CategoryDocument, String> {
    Flux<CategoryDocument> findAllByIsDeleted(Boolean isDeleted, Sort sort);
    Flux<CategoryDocument> findAllByIsDeletedAndParentId(Boolean isDeleted, Sort sort,String parentId);
    Mono<CategoryDocument> findByNameAndIsDeletedAndParentId(String name, Boolean isDeleted, String parentId);
}
