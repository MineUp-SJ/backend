package com.mineup.orchestrator.services.impl;

import com.mineup.orchestrator.dto.requests.CategoryDtoRequest;
import com.mineup.orchestrator.dto.requests.MembershipDtoRequest;
import com.mineup.orchestrator.dto.responses.CategoryDtoResponse;
import com.mineup.orchestrator.dto.responses.MembershipDtoResponse;
import com.mineup.orchestrator.exceptions.ResourceAlreadyExistsException;
import com.mineup.orchestrator.mappers.spec.ICategoryMapper;
import com.mineup.orchestrator.mappers.spec.IMembershipMapper;
import com.mineup.orchestrator.models.Membership;
import com.mineup.orchestrator.repositories.CategoryRepository;
import com.mineup.orchestrator.repositories.MembershipRepository;
import com.mineup.orchestrator.services.spec.ICategoryService;
import com.mineup.orchestrator.services.spec.IMembershipService;
import com.mineup.orchestrator.utils.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final ICategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, ICategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
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
    public Mono<CategoryDtoResponse> createCategory(CategoryDtoRequest category, String parentId) {

        String nameWithoutAccents = StringUtils.removeAccents(category.getName());
        String nameUpperCase = nameWithoutAccents.toUpperCase();
        category.setName(nameUpperCase);
        return categoryRepository.findByNameAndIsDeletedAndParentId(category.getName(), false, parentId)
                .flatMap(ExistMembership -> Mono.<CategoryDtoResponse>error(new ResourceAlreadyExistsException("Membership already exists and is not deleted")))
                .switchIfEmpty(saveCategory(category,parentId));
    }

    public void deleteCategory(String id) {
        categoryRepository.findById(id)
                .flatMap(category -> {
                    category.setIsDeleted(true);
                    return categoryRepository.save(category);
                })
                .subscribe();
    }
    //aditional methods
    private Mono<CategoryDtoResponse> saveCategory(CategoryDtoRequest category, String parentId) {
        return categoryRepository.save(
                categoryMapper.toEntity(category,parentId))
                .map(categoryMapper::toDto);
    }
}
