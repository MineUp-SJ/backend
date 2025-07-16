package com.mineup.orchestrator.application.service;

import com.mineup.orchestrator.domain.model.Mineral;
import com.mineup.orchestrator.domain.port.out.MineralRepository;
import com.mineup.orchestrator.exceptions.ResourceAlreadyExistsException;


import com.mineup.orchestrator.domain.port.in.IMineralService;
import com.mineup.orchestrator.utils.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MineralService implements IMineralService {
    private final MineralRepository mineralRepository;

    public MineralService(MineralRepository mineralRepository) {
        this.mineralRepository = mineralRepository;
    }

    public Flux<Mineral> findAll() {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return mineralRepository.findAllByIsDeleted(false, sort);
    }
    public Mono<Mineral> createMineral(Mineral mineral) {

        String nameWithoutAccents = StringUtils.removeAccents(mineral.getName());
        String nameUpperCase = nameWithoutAccents.toUpperCase();

        return mineralRepository.findByNameAndIsDeleted(nameUpperCase, false)
                .flatMap(existingMineral -> Mono.<Mineral>error( new ResourceAlreadyExistsException("Mineral already exists and is not deleted")))
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
    private Mono<Mineral> saveMineral(String name) {
        Mineral request = Mineral.builder()
                .name(name)
                .isDeleted(false)
                .build();
        return mineralRepository.save(request);
    }
}
