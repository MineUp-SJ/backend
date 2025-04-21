package com.mineup.orchestrator.services.impl;

import com.mineup.orchestrator.dto.requests.MembershipDtoRequest;
import com.mineup.orchestrator.dto.requests.ProfileDtoRequest;
import com.mineup.orchestrator.dto.responses.MembershipDtoResponse;
import com.mineup.orchestrator.dto.responses.ProfileDtoResponse;
import com.mineup.orchestrator.exceptions.ResourceAlreadyExistsException;
import com.mineup.orchestrator.mappers.spec.IMembershipMapper;
import com.mineup.orchestrator.mappers.spec.IProfileMapper;
import com.mineup.orchestrator.models.Membership;
import com.mineup.orchestrator.models.Profile;
import com.mineup.orchestrator.repositories.MembershipRepository;
import com.mineup.orchestrator.repositories.ProfileRepository;
import com.mineup.orchestrator.services.spec.IMembershipService;
import com.mineup.orchestrator.services.spec.IProfileService;
import com.mineup.orchestrator.utils.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MembershipService implements IMembershipService {
    private final MembershipRepository membershipRepository;
    private final IMembershipMapper membershipMapper;

    public MembershipService(MembershipRepository membershipRepository, IMembershipMapper membershipMapper) {
        this.membershipRepository = membershipRepository;
        this.membershipMapper = membershipMapper;
    }

    public Flux<MembershipDtoResponse> findAll() {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return membershipRepository.findAllByIsDeleted(false, sort)
                .flatMap(entity-> Flux.just(membershipMapper.toDto(entity)));
    }
    public Mono<MembershipDtoResponse> createMembership(MembershipDtoRequest membership, String profileId) {

        String nameWithoutAccents = StringUtils.removeAccents(membership.getName());
        String nameUpperCase = nameWithoutAccents.toUpperCase();
        membership.setName(nameUpperCase);
        return membershipRepository.findByNameAndIsDeletedAndProfileId(membership.getName(), false, profileId)
                .flatMap(ExistMembership -> Mono.<MembershipDtoResponse>error(new ResourceAlreadyExistsException("Membership already exists and is not deleted")))
                .switchIfEmpty(saveMembership(membership,profileId));
    }

    public void deleteMembership(String id) {
        membershipRepository.findById(id)
                .flatMap(membership -> {
                    membership.setIsDeleted(true);
                    return membershipRepository.save(membership);
                })
                .subscribe();
    }
    //aditional methods
    private Mono<MembershipDtoResponse> saveMembership(MembershipDtoRequest membership, String profileId) {
       Membership membershipEntity = membershipMapper.toEntity(membership);
        membershipEntity.setIsDeleted(false);
        membershipEntity.setIsVerified(false);
        membershipEntity.setProfileId(profileId);

        return membershipRepository.save(membershipEntity)
                .map(membershipMapper::toDto);
    }
}
