package com.mineup.orchestrator.domain.port.out;

import com.mineup.orchestrator.domain.model.Category;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryRepository {
    Flux<Category> findAllByIsDeleted(Boolean isDeleted, Sort sort);
    Flux<Category> findAllByIsDeletedAndParentId(Boolean isDeleted, Sort sort,String parentId);
    Mono<Category> findByNameAndIsDeletedAndParentId(String name, Boolean isDeleted, String parentId);
    Mono<Category> save(Category category, String parentId);
    Mono<Category> save(Category category);
    Mono<Category> findById(String id);

}
