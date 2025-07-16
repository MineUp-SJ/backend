package com.mineup.orchestrator.adapter.out.persistence.mongo.adapter;

import com.mineup.orchestrator.adapter.out.persistence.mongo.repository.SpringDataMineralRepository;
import com.mineup.orchestrator.adapter.out.persistence.mongo.mapper.MineralPersistenceMapper;
import com.mineup.orchestrator.domain.model.Category;
import com.mineup.orchestrator.domain.model.Mineral;
import com.mineup.orchestrator.domain.port.out.MineralRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MineralRepositoryAdapter implements MineralRepository {

    SpringDataMineralRepository springDataMineralRepository;
    MineralPersistenceMapper mineralPersistenceMapper;

    public MineralRepositoryAdapter(SpringDataMineralRepository springDataMineralRepository, MineralPersistenceMapper mineralPersistenceMapper) {
        this.springDataMineralRepository = springDataMineralRepository;
        this.mineralPersistenceMapper = mineralPersistenceMapper;
    }

    @Override
    public Flux<Mineral> findAllByIsDeleted(Boolean isDeleted, Sort sort) {
        return springDataMineralRepository.findAllByIsDeleted(isDeleted, sort)
                .map(mineralDocument -> mineralPersistenceMapper.toDomain(mineralDocument));
    }

    @Override
    public Mono<Mineral> findByNameAndIsDeleted(String name, Boolean isDeleted) {
        return springDataMineralRepository.findByNameAndIsDeleted(name, isDeleted)
                .map(mineralDocument -> mineralPersistenceMapper.toDomain(mineralDocument));
    }
    @Override
    public Mono<Mineral> save (Mineral mineral) {
        return springDataMineralRepository.save(mineralPersistenceMapper.toDocument(mineral))
                .map(mineralDocument -> mineralPersistenceMapper.toDomain(mineralDocument));
    }
    @Override
    public Mono<Mineral> findById(String id) {
        return springDataMineralRepository.findById(id)
                .map(mineralDocument -> mineralPersistenceMapper.toDomain(mineralDocument));
    }
}
