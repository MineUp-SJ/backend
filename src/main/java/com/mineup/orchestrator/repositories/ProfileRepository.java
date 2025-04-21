package com.mineup.orchestrator.repositories;

import com.mineup.orchestrator.models.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProfileRepository extends ReactiveCrudRepository<Profile, String> {
    Flux<Profile> findAllByName();
    Flux<Profile> findAllByIsDeleted(Boolean isDeleted, Sort sort);
    Mono<Profile> findByNameAndIsDeleted(String name, Boolean isDeleted);
    Mono<Profile> findByName(String name);
}
