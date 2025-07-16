package com.mineup.orchestrator.domain.port.out;


import com.mineup.orchestrator.domain.model.Membership;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MembershipRepository{

    Flux<Membership> findAllByIsDeleted(Boolean isDeleted, Sort sort);
    Mono<Membership> findByNameAndIsDeletedAndProfileId(String name, Boolean isDeleted, String profileId);
    Mono<Membership> save(Membership profile);
    Mono<Membership> findById(String id);

}
