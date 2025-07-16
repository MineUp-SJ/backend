package com.mineup.orchestrator.adapter.in.web;

import com.mineup.orchestrator.adapter.in.mapper.MembershipWebMapper;
import com.mineup.orchestrator.adapter.in.mapper.ProfileWebMapper;
import com.mineup.orchestrator.application.service.MembershipService;
import com.mineup.orchestrator.application.service.ProfileService;
import com.mineup.orchestrator.domain.model.Membership;
import com.mineup.orchestrator.domain.model.Profile;
import com.mineup.orchestrator.adapter.in.dto.MembershipDtoRequest;
import com.mineup.orchestrator.adapter.in.dto.ProfileDtoRequest;
import com.mineup.orchestrator.adapter.in.dto.MembershipDtoResponse;
import com.mineup.orchestrator.adapter.in.dto.ProfileDtoResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/profiles")
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileWebMapper profileWebMapper;
    private final MembershipService membershipService;
    private final MembershipWebMapper membershipWebMapper;

    public ProfileController(ProfileService profileService, MembershipService membershipService,ProfileWebMapper profileWebMapper, MembershipWebMapper membershipWebMapper) {
        this.profileService = profileService;
        this.membershipService = membershipService;
        this.profileWebMapper = profileWebMapper;
        this.membershipWebMapper = membershipWebMapper;
    }

    @Operation(summary = "Get all profiles", description = "Retrieve a list of all profiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public Flux<ProfileDtoResponse> getProfiles() {
        return profileService.findAll()
                .map(profileWebMapper::toDto);
    }


    @Operation(summary = "Add a profile", description = "Add a profile`")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "210", description = "Successfully profile created"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public Mono<ProfileDtoResponse> createProfile(@RequestBody ProfileDtoRequest profile) {
        Profile request = profileWebMapper.toDomain(profile);
        return profileService.createProfile(request)
                .map(profileWebMapper::toDto);
    }
    @Operation(summary = "Add a membership", description = "Add a membership")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "210", description = "Successfully membership created"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{profileId}/membership")
    public Mono<MembershipDtoResponse> createMembership(@PathVariable String profileId, @RequestBody MembershipDtoRequest membership) {

        Membership request = Membership.builder()
                .profileId(profileId)
                .name(membership.getName())
                .description(membership.getDescription())
                .type(membership.getType())
                .price(membership.getPrice())
                .currency(membership.getCurrency())
                .duration(membership.getDuration())
                .upToTeamMembers(membership.getUpToTeamMembers())
                .features(membership.getFeatures())
                .build();

        return membershipService.createMembership(request, profileId)
                .map(membershipWebMapper::toDto);
    }
}

