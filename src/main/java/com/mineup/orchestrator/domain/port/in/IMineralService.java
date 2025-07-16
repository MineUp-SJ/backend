package com.mineup.orchestrator.domain.port.in;


import com.mineup.orchestrator.domain.model.Mineral;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMineralService {
    public Flux<Mineral> findAll();
    public Mono<Mineral> createMineral(Mineral mineral);

}
