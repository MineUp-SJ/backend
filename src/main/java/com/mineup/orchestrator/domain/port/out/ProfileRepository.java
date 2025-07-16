package com.mineup.orchestrator.domain.port.out;

import com.mineup.orchestrator.domain.model.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProfileRepository {

    Flux<Profile> findAllByIsDeleted(Boolean isDeleted, Sort sort);
    Mono<Profile> findByNameAndIsDeleted(String name, Boolean isDeleted);
    Mono<Profile> save(Profile profile);
    Mono<Profile> findById(String id);

}
