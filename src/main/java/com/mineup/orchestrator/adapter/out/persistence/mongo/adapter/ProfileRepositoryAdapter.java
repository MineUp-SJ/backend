package com.mineup.orchestrator.adapter.out.persistence.mongo.adapter;

import com.mineup.orchestrator.adapter.out.persistence.mongo.repository.SpringDataProfileRepository;
import com.mineup.orchestrator.adapter.out.persistence.mongo.mapper.ProfilePersistenceMapper;
import com.mineup.orchestrator.domain.model.Profile;
import com.mineup.orchestrator.domain.port.out.ProfileRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProfileRepositoryAdapter implements ProfileRepository {

    SpringDataProfileRepository springDataProfileRepository;
    ProfilePersistenceMapper profilePersistenceMapper;

    public ProfileRepositoryAdapter(SpringDataProfileRepository springDataProfileRepository, ProfilePersistenceMapper profilePersistenceMapper) {
        this.springDataProfileRepository = springDataProfileRepository;
        this.profilePersistenceMapper = profilePersistenceMapper;
    }

    @Override
    public Flux<Profile> findAllByIsDeleted(Boolean isDeleted, Sort sort) {
        return springDataProfileRepository.findAllByIsDeleted(isDeleted, sort)
                .map(profileDocument -> profilePersistenceMapper.toDomain(profileDocument));
    }

    @Override
    public Mono<Profile> findByNameAndIsDeleted(String name, Boolean isDeleted) {
        return springDataProfileRepository.findByNameAndIsDeleted(name, isDeleted)
                .map(profileDocument -> profilePersistenceMapper.toDomain(profileDocument));
    }
    @Override
    public Mono<Profile> save (Profile profile) {
        return springDataProfileRepository.save(profilePersistenceMapper.toDocument(profile))
                .map(profileDocument -> profilePersistenceMapper.toDomain(profileDocument));
    }
    @Override
    public Mono<Profile> findById(String id) {
        return springDataProfileRepository.findById(id)
                .map(profileDocument -> profilePersistenceMapper.toDomain(profileDocument));
    }
}
