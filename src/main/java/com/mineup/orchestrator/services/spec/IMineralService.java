package com.mineup.orchestrator.services.spec;


import com.mineup.orchestrator.dto.requests.MineralDtoRequest;
import com.mineup.orchestrator.dto.responses.MineralDtoResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMineralService {
    public Flux<MineralDtoResponse> findAll();
    public Mono<MineralDtoResponse> createMineral(MineralDtoRequest mineral);

}
