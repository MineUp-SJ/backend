package com.mineup.orchestrator.application.service;

import com.mineup.orchestrator.domain.model.Category;
import com.mineup.orchestrator.domain.port.out.CategoryRepository;
import com.mineup.orchestrator.exceptions.ResourceAlreadyExistsException;
import com.mineup.orchestrator.domain.port.in.ICategoryService;
import com.mineup.orchestrator.utils.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Flux<Category> findAll() {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return categoryRepository.findAllByIsDeleted(false, sort);
    }
    public Flux<Category> findAll(String parentId) {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return categoryRepository.findAllByIsDeletedAndParentId(false, sort, parentId);
    }
    @CacheEvict(value = "productByCategories", allEntries = true)
    public Mono<Category> createCategory(Category category, String parentId) {

        String nameWithoutAccents = StringUtils.removeAccents(category.getName());
        String nameUpperCase = nameWithoutAccents.toUpperCase();
        category.setName(nameUpperCase);

        return categoryRepository.findByNameAndIsDeletedAndParentId(category.getName(), false, parentId)
                .flatMap(ExistMembership -> Mono.<Category>error(new ResourceAlreadyExistsException("Category already exists and is not deleted")))
                .switchIfEmpty(saveCategory(category,parentId));
    }

    @CacheEvict(value = "productByCategories", allEntries = true)
    public void deleteCategory(String id) {
         categoryRepository.findById(id)
                .flatMap(category -> {
                    category.setIsDeleted(true);
                    return categoryRepository.save(category);
                }).subscribe();
    }

    private Mono<Category> saveCategory(Category category, String parentId) {
        return categoryRepository.save(category,parentId);
    }


}
