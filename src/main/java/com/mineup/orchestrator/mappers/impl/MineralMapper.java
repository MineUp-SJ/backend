package com.mineup.orchestrator.mappers.impl;

import com.mineup.orchestrator.dto.requests.MineralDtoRequest;
import com.mineup.orchestrator.dto.responses.MineralDtoResponse;
import com.mineup.orchestrator.mappers.spec.IMineralMapper;
import com.mineup.orchestrator.models.Mineral;
import org.springframework.stereotype.Service;


public class MineralMapper implements IMineralMapper {
    @Override
    public MineralDtoResponse toDto(Mineral mineral) {
        return MineralDtoResponse.builder()
                .id(mineral.getId())
                .name(mineral.getName())
                .build();
    }

    @Override
    public Mineral toEntity(MineralDtoRequest mineralDtoRequest) {
        return Mineral.builder()
                .name(mineralDtoRequest.getName())
                .build();
    }
}
