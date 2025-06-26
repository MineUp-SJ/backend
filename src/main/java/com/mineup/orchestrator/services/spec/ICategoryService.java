package com.mineup.orchestrator.services.spec;

import com.mineup.orchestrator.dto.requests.CategoryDtoRequest;
import com.mineup.orchestrator.dto.responses.CategoryDtoResponse;
import com.mineup.orchestrator.dto.responses.ProductByCategoryDtoResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICategoryService {
  public Mono<CategoryDtoResponse> createCategory(CategoryDtoRequest categoryDtoRequest, String parentId);
public Mono<List<ProductByCategoryDtoResponse>> findProductsByCategory();
}
