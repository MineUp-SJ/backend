package com.mineup.orchestrator.application.service;

import com.mineup.orchestrator.domain.model.Profile;
import com.mineup.orchestrator.domain.port.in.IProfileService;
import com.mineup.orchestrator.domain.port.out.ProfileRepository;
import com.mineup.orchestrator.domain.exceptions.ResourceAlreadyExistsException;


import com.mineup.orchestrator.utils.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProfileService implements IProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Flux<Profile> findAll() {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return profileRepository.findAllByIsDeleted(false, sort);
    }
    public Mono<Profile> createProfile(Profile profile) {

        String nameWithoutAccents = StringUtils.removeAccents(profile.getName());
        String nameUpperCase = nameWithoutAccents.toUpperCase();

        return profileRepository.findByNameAndIsDeleted(profile.getName(), false)
                .flatMap(existinProfile -> Mono.<Profile>error(new ResourceAlreadyExistsException("Profile already exists and is not deleted")))
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
    private Mono<Profile> saveProfile(Profile profile) {
        profile.setIsDeleted(false);
        profile.setIsVerified(false);

        return profileRepository.save(profile);
    }
}
