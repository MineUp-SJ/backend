package com.mineup.orchestrator.services.impl;

import com.mineup.orchestrator.dto.requests.MineralDtoRequest;
import com.mineup.orchestrator.dto.requests.ProfileDtoRequest;
import com.mineup.orchestrator.dto.responses.MineralDtoResponse;
import com.mineup.orchestrator.dto.responses.ProfileDtoResponse;
import com.mineup.orchestrator.exceptions.ResourceAlreadyExistsException;
import com.mineup.orchestrator.mappers.spec.IMineralMapper;
import com.mineup.orchestrator.mappers.spec.IProfileMapper;
import com.mineup.orchestrator.models.Mineral;
import com.mineup.orchestrator.models.Profile;
import com.mineup.orchestrator.repositories.MineralRepository;
import com.mineup.orchestrator.repositories.ProfileRepository;
import com.mineup.orchestrator.services.spec.IMineralService;
import com.mineup.orchestrator.services.spec.IProfileService;
import com.mineup.orchestrator.utils.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProfileService implements IProfileService {
    private final ProfileRepository profileRepository;
    private final IProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository, IProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    public Flux<ProfileDtoResponse> findAll() {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return profileRepository.findAllByIsDeleted(false, sort)
                .flatMap(entity-> Flux.just(profileMapper.toDto(entity)));
    }
    public Mono<ProfileDtoResponse> createProfile(ProfileDtoRequest profile) {

        String nameWithoutAccents = StringUtils.removeAccents(profile.getName());
        String nameUpperCase = nameWithoutAccents.toUpperCase();

        return profileRepository.findByNameAndIsDeleted(profile.getName(), false)
                .flatMap(existinProfile -> Mono.<ProfileDtoResponse>error(new ResourceAlreadyExistsException("Profile already exists and is not deleted")))
                .switchIfEmpty(saveProfile(profile));
    }

    public void deleteProfile(String id) {
        profileRepository.findById(id)
                .flatMap(mineral -> {
                    mineral.setIsDeleted(true);
                    return profileRepository.save(mineral);
                })
                .subscribe();
    }
    //aditional methods
    private Mono<ProfileDtoResponse> saveProfile(ProfileDtoRequest profile) {
        return profileRepository.save(
                Profile.builder()
                        .name(profile.getName())
                        .description(profile.getDescription())
                        .isDeleted(false)
                        .isVerified(false)
                        .build()
                )
                .map(profileMapper::toDto);
    }
}
