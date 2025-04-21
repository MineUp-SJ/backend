package com.mineup.orchestrator.repositories;

import com.mineup.orchestrator.models.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CategoryRepository extends ReactiveCrudRepository<Category, String> {
    Flux<Category> findAllByName();
    Flux<Category> findAllByIsDeleted(Boolean isDeleted, Sort sort);
    Flux<Category> findAllByIsDeletedAndParentId(Boolean isDeleted, Sort sort,String parentId);
    Mono<Category> findByNameAndIsDeletedAndParentId(String name, Boolean isDeleted, String parentId);
    Mono<Category> findByName(String name);
}
