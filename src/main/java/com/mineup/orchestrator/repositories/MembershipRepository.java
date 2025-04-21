package com.mineup.orchestrator.repositories;

import com.mineup.orchestrator.models.Membership;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MembershipRepository extends ReactiveCrudRepository<Membership, String> {
    Flux<Membership> findAllByName();
    Flux<Membership> findAllByIsDeleted(Boolean isDeleted, Sort sort);
    Mono<Membership> findByNameAndIsDeletedAndProfileId(String name, Boolean isDeleted, String profileId);
    Mono<Membership> findByName(String name);
}
