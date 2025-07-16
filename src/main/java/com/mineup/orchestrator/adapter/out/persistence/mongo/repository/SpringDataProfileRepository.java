package com.mineup.orchestrator.adapter.out.persistence.mongo.repository;

import com.mineup.orchestrator.adapter.out.persistence.mongo.document.ProfileDocument;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SpringDataProfileRepository extends ReactiveCrudRepository<ProfileDocument, String> {

    Flux<ProfileDocument> findAllByIsDeleted(Boolean isDeleted, Sort sort);
    Mono<ProfileDocument> findByNameAndIsDeleted(String name, Boolean isDeleted);

}
