package com.mineup.orchestrator.adapter.in.mapper;

import com.mineup.orchestrator.domain.model.Profile;
import com.mineup.orchestrator.adapter.in.dto.ProfileDtoRequest;
import com.mineup.orchestrator.adapter.in.dto.ProfileDtoResponse;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProfileWebMapper {

    ProfileDtoResponse toDto(Profile profile);

    Profile toDomain(ProfileDtoRequest profileDtoRequest);
}
