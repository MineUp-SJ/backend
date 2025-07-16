package com.mineup.orchestrator.adapter.in.mapper;


import com.mineup.orchestrator.adapter.in.dto.CategoryDtoRequest;
import com.mineup.orchestrator.adapter.in.dto.CategoryDtoResponse;

import com.mineup.orchestrator.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface CategoryWebMapper {

      CategoryDtoResponse toDto(Category category);
      Category toEntity(CategoryDtoRequest categoryDtoRequest);
      @Mappings({
              @Mapping(target = "parentId", source = "parentId"),
              @Mapping(target = "isVerified",  constant = "false"),
              @Mapping(target = "isDeleted",  constant = "false")
      })
      Category toEntity(CategoryDtoRequest categoryDtoRequest,String parentId);

}
