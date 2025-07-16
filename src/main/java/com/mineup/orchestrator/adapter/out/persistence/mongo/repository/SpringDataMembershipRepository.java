package com.mineup.orchestrator.adapter.out.persistence.mongo.repository;

import com.mineup.orchestrator.adapter.out.persistence.mongo.document.MembershipDocument;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SpringDataMembershipRepository extends ReactiveCrudRepository<MembershipDocument, String> {

    Flux<MembershipDocument> findAllByIsDeleted(Boolean isDeleted, Sort sort);
    Mono<MembershipDocument> findByNameAndIsDeletedAndProfileId(String name, Boolean isDeleted, String profileId);

}
