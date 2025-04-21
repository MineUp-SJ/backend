package com.mineup.orchestrator.mappers.spec;

import com.mineup.orchestrator.dto.requests.CategoryDtoRequest;
import com.mineup.orchestrator.dto.requests.ProfileDtoRequest;
import com.mineup.orchestrator.dto.responses.CategoryDtoResponse;
import com.mineup.orchestrator.dto.responses.ProfileDtoResponse;
import com.mineup.orchestrator.models.Category;
import com.mineup.orchestrator.models.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface ICategoryMapper {

    CategoryDtoResponse toDto(Category category);
    Category toEntity(CategoryDtoRequest categoryDtoRequest);
    @Mappings({
            @Mapping(target = "parentId", source = "parentId"),
            @Mapping(target = "isVerified",  constant = "false"),
            @Mapping(target = "isDeleted",  constant = "false")
    })
    Category toEntity(CategoryDtoRequest categoryDtoRequest,String parentId);
}
