package com.mineup.orchestrator.adapter.out.persistence.mongo.repository;

import com.mineup.orchestrator.adapter.out.persistence.mongo.document.MineralDocument;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SpringDataMineralRepository extends ReactiveCrudRepository<MineralDocument, String> {
    //Flux<MineralDocument> findAllByName();
    Flux<MineralDocument> findAllByIsDeleted(Boolean isDeleted, Sort sort);
    Mono<MineralDocument> findByNameAndIsDeleted(String name, Boolean isDeleted);
    //Mono<MineralDocument> findByName(String name);
}
