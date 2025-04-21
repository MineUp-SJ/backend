package com.mineup.orchestrator.services.spec;

import com.mineup.orchestrator.dto.requests.ProfileDtoRequest;
import com.mineup.orchestrator.dto.responses.ProfileDtoResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProfileService {
    public Flux<ProfileDtoResponse> findAll();
    public Mono<ProfileDtoResponse> createProfile(ProfileDtoRequest profile);

}
