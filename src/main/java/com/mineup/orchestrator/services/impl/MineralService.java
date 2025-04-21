package com.mineup.orchestrator.services.impl;

import com.mineup.orchestrator.dto.requests.MineralDtoRequest;
import com.mineup.orchestrator.dto.responses.MineralDtoResponse;
import com.mineup.orchestrator.exceptions.ResourceAlreadyExistsException;
import com.mineup.orchestrator.exceptions.ResourceNotFoundException;
import com.mineup.orchestrator.mappers.impl.MineralMapper;
import com.mineup.orchestrator.mappers.spec.IMineralMapper;
import com.mineup.orchestrator.models.Mineral;
import com.mineup.orchestrator.repositories.MineralRepository;
import com.mineup.orchestrator.services.spec.IMineralService;
import com.mineup.orchestrator.utils.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MineralService implements IMineralService {
    private final MineralRepository mineralRepository;
    private final IMineralMapper mineralMapper;

    public MineralService(MineralRepository mineralRepository, IMineralMapper mineralMapper) {
        this.mineralRepository = mineralRepository;
        this.mineralMapper = mineralMapper;
    }

    public Flux<MineralDtoResponse> findAll() {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return mineralRepository.findAllByIsDeleted(false, sort)
                .flatMap(entity-> Flux.just(mineralMapper.toDto(entity)));
    }
    public Mono<MineralDtoResponse> createMineral(MineralDtoRequest mineral) {

        String nameWithoutAccents = StringUtils.removeAccents(mineral.getName());
        String nameUpperCase = nameWithoutAccents.toUpperCase();

        return mineralRepository.findByNameAndIsDeleted(nameUpperCase, false)
                .flatMap(existingMineral -> Mono.<MineralDtoResponse>error( new ResourceAlreadyExistsException("Mineral already exists and is not deleted")))
                .switchIfEmpty(saveMineral(nameUpperCase));
    }

    public void deleteMineral(String id) {
        mineralRepository.findById(id)
                .flatMap(mineral -> {
                    mineral.setIsDeleted(true);
                    return mineralRepository.save(mineral);
                })
                .subscribe();
    }
    //aditional methods
    private Mono<MineralDtoResponse> saveMineral(String name) {
        return mineralRepository.save(Mineral.builder()
                        .name(name)
                        .isDeleted(false)
                        .build())
                .map(mineralMapper::toDto);
    }
}
