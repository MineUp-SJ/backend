package com.mineup.orchestrator.adapter.out.persistence.mongo.mapper;

import com.mineup.orchestrator.adapter.out.persistence.mongo.document.MineralDocument;
import com.mineup.orchestrator.domain.model.Mineral;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MineralPersistenceMapper {

    Mineral toDomain (MineralDocument mineralDocument);
    MineralDocument toDocument(Mineral mineral);
}
