package com.mineup.orchestrator.services.spec;

import com.mineup.orchestrator.dto.requests.CategoryDtoRequest;
import com.mineup.orchestrator.dto.responses.CategoryDtoResponse;
import reactor.core.publisher.Mono;

public interface ICategoryService {
  public Mono<CategoryDtoResponse> createCategory(CategoryDtoRequest categoryDtoRequest, String parentId);

}
