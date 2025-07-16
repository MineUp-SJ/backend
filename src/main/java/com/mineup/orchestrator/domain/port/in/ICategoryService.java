package com.mineup.orchestrator.domain.port.in;

import com.mineup.orchestrator.domain.model.Category;
import com.mineup.orchestrator.adapter.in.dto.ProductByCategoryDtoResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICategoryService {
  public Mono<Category> createCategory(Category category, String parentId);
  //public Mono<List<ProductByCategoryDtoResponse>> findProductsByCategory();
}
