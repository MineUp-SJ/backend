package com.mineup.orchestrator.adapter.out.persistence.mongo.adapter;

import com.mineup.orchestrator.adapter.out.persistence.mongo.repository.SpringDataCategoryRepository;
import com.mineup.orchestrator.adapter.out.persistence.mongo.mapper.CategoryPersistenceMapper;
import com.mineup.orchestrator.domain.model.Category;
import com.mineup.orchestrator.domain.port.out.CategoryRepository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CategoryRepositoryAdapter implements CategoryRepository {

    SpringDataCategoryRepository springDataCategoryRepository;
    CategoryPersistenceMapper categoryPersistenceMapper;

    public CategoryRepositoryAdapter(SpringDataCategoryRepository springDataCategoryRepository, CategoryPersistenceMapper categoryPersistenceMapper) {
        this.springDataCategoryRepository = springDataCategoryRepository;
        this.categoryPersistenceMapper = categoryPersistenceMapper;
    }

    @Override
    public Flux<Category> findAllByIsDeleted(Boolean isDeleted, Sort sort) {
        return springDataCategoryRepository.findAllByIsDeleted(isDeleted, sort)
                .map(category -> {return categoryPersistenceMapper.toDomain(category);});
    }

    @Override
    public Flux<Category> findAllByIsDeletedAndParentId(Boolean isDeleted, Sort sort, String parentId) {
        return springDataCategoryRepository.findAllByIsDeletedAndParentId(isDeleted, sort, parentId)
                .map(category -> categoryPersistenceMapper.toDomain(category));
    }

    @Override
    public Mono<Category> findByNameAndIsDeletedAndParentId(String name, Boolean isDeleted, String parentId) {
        return springDataCategoryRepository.findByNameAndIsDeletedAndParentId(name, isDeleted, parentId)
                .map(category -> categoryPersistenceMapper.toDomain(category));
    }
    @Override
    public Mono<Category> save (Category category, String parentId) {
        return springDataCategoryRepository.save(categoryPersistenceMapper.toDocument(category, parentId))
                .map(savedCategory -> categoryPersistenceMapper.toDomain(savedCategory));
    }
    @Override
    public Mono<Category> save (Category category) {
        return springDataCategoryRepository.save(categoryPersistenceMapper.toDocument(category))
                .map(savedCategory -> categoryPersistenceMapper.toDomain(savedCategory));
    }
    @Override
    public Mono<Category> findById(String id) {
        return springDataCategoryRepository.findById(id)
                .map(category -> categoryPersistenceMapper.toDomain(category));
    }
}
