package com.mineup.orchestrator.mappers.spec;

import com.mineup.orchestrator.dto.requests.MineralDtoRequest;
import com.mineup.orchestrator.dto.requests.ProfileDtoRequest;
import com.mineup.orchestrator.dto.responses.MineralDtoResponse;
import com.mineup.orchestrator.dto.responses.ProfileDtoResponse;
import com.mineup.orchestrator.models.Mineral;
import com.mineup.orchestrator.models.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface IProfileMapper {

    ProfileDtoResponse toDto(Profile profile);

    Profile toEntity(ProfileDtoRequest profileDtoRequest);
}
