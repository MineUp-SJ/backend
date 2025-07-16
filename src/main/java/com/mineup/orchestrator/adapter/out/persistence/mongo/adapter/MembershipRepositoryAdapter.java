package com.mineup.orchestrator.adapter.out.persistence.mongo.adapter;

import com.mineup.orchestrator.adapter.out.persistence.mongo.repository.SpringDataMembershipRepository;
import com.mineup.orchestrator.adapter.out.persistence.mongo.mapper.MembershipPersistenceMapper;
import com.mineup.orchestrator.domain.model.Membership;
import com.mineup.orchestrator.domain.port.out.MembershipRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MembershipRepositoryAdapter implements MembershipRepository {

    SpringDataMembershipRepository springDataMembershipRepository;
    MembershipPersistenceMapper membershipPersistenceMapper;

    public MembershipRepositoryAdapter(SpringDataMembershipRepository springDataMembershipRepository, MembershipPersistenceMapper membershipPersistenceMapper) {
        this.springDataMembershipRepository = springDataMembershipRepository;
        this.membershipPersistenceMapper = membershipPersistenceMapper;
    }

    @Override
    public Flux<Membership> findAllByIsDeleted(Boolean isDeleted, Sort sort) {
        return springDataMembershipRepository.findAllByIsDeleted(isDeleted, sort)
                .map(membershipDocument -> membershipPersistenceMapper.toDomain(membershipDocument));
    }

    @Override
    public Mono<Membership> findByNameAndIsDeletedAndProfileId(String name, Boolean isDeleted, String profileId) {
        return springDataMembershipRepository.findByNameAndIsDeletedAndProfileId(name, isDeleted, profileId)
                .map(membershipDocument -> membershipPersistenceMapper.toDomain(membershipDocument));
    }
    @Override
    public Mono<Membership> save (Membership profile) {
        return springDataMembershipRepository.save(membershipPersistenceMapper.toDocument(profile))
                .map(membershipDocument -> membershipPersistenceMapper.toDomain(membershipDocument));
    }
    @Override
    public Mono<Membership> findById(String id) {
        return springDataMembershipRepository.findById(id)
                .map(membershipDocument -> membershipPersistenceMapper.toDomain(membershipDocument));
    }
}
