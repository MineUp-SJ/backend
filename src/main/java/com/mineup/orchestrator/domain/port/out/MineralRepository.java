package com.mineup.orchestrator.domain.port.out;


import com.mineup.orchestrator.domain.model.Mineral;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MineralRepository {
    //Flux<Mineral> findAllByName();
    Flux<Mineral> findAllByIsDeleted(Boolean isDeleted, Sort sort);
    Mono<Mineral> findByNameAndIsDeleted(String name, Boolean isDeleted);
    //Mono<Mineral> findByName(String name);
    Mono<Mineral> save(Mineral mineral);
    Mono<Mineral> findById(String id);
}
