package com.mineup.orchestrator.adapter.in.mapper;

import com.mineup.orchestrator.domain.model.Mineral;
import com.mineup.orchestrator.adapter.in.dto.MineralDtoRequest;
import com.mineup.orchestrator.adapter.in.dto.MineralDtoResponse;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MineralWebMapper {

    MineralDtoResponse toDto(Mineral mineral);
    Mineral toDomain(MineralDtoRequest mineralDtoRequest);
}
