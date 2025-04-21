package com.mineup.orchestrator.mappers.spec;

import com.mineup.orchestrator.dto.requests.MineralDtoRequest;
import com.mineup.orchestrator.dto.responses.MineralDtoResponse;
import com.mineup.orchestrator.models.Mineral;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface IMineralMapper {

    MineralDtoResponse toDto(Mineral mineral);
    Mineral toEntity(MineralDtoRequest mineralDtoRequest);
}
