package com.mineup.orchestrator.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import  com.mineup.orchestrator.models.Mineral;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MineralRepository extends ReactiveCrudRepository<Mineral, String> {
    Flux<Mineral> findAllByName();
    Flux<Mineral> findAllByIsDeleted(Boolean isDeleted, Sort sort);
    Mono<Mineral> findByNameAndIsDeleted(String name, Boolean isDeleted);
    Mono<Mineral> findByName(String name);
}
