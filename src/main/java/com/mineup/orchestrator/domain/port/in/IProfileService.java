package com.mineup.orchestrator.domain.port.in;

import com.mineup.orchestrator.domain.model.Profile;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProfileService {
    public Flux<Profile> findAll();
    public Mono<Profile> createProfile(Profile profile);

}
