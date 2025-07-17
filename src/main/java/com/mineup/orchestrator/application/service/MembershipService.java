package com.mineup.orchestrator.application.service;

import com.mineup.orchestrator.domain.model.Membership;
import com.mineup.orchestrator.domain.port.in.IMembershipService;
import com.mineup.orchestrator.domain.port.out.MembershipRepository;
import com.mineup.orchestrator.domain.exceptions.ResourceAlreadyExistsException;


import com.mineup.orchestrator.utils.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MembershipService implements IMembershipService {
    private final MembershipRepository membershipRepository;

    public MembershipService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    public Flux<Membership> findAll() {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        return membershipRepository.findAllByIsDeleted(false, sort);
    }

    public Mono<Membership> createMembership(Membership membership, String profileId) {

        String nameWithoutAccents = StringUtils.removeAccents(membership.getName());
        String nameUpperCase = nameWithoutAccents.toUpperCase();
        membership.setName(nameUpperCase);
        return membershipRepository.findByNameAndIsDeletedAndProfileId(membership.getName(), false, profileId)
                .flatMap(ExistMembership -> Mono.<Membership>error(new ResourceAlreadyExistsException("Membership already exists and is not deleted")))
                .switchIfEmpty(saveMembership(membership, profileId));
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
    private Mono<Membership> saveMembership(Membership membership, String profileId) {

        membership.setIsDeleted(false);
        membership.setIsVerified(false);
        membership.setProfileId(profileId);

        return membershipRepository.save(membership);
    }
}
