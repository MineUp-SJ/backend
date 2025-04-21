package com.mineup.orchestrator.controllers;

import com.mineup.orchestrator.dto.requests.MembershipDtoRequest;
import com.mineup.orchestrator.dto.requests.ProfileDtoRequest;
import com.mineup.orchestrator.dto.responses.MembershipDtoResponse;
import com.mineup.orchestrator.dto.responses.ProfileDtoResponse;
import com.mineup.orchestrator.services.impl.MembershipService;
import com.mineup.orchestrator.services.impl.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/profiles")
public class ProfileController {

    private final ProfileService profileService;
    private final MembershipService membershipService;

    public ProfileController(ProfileService profileService, MembershipService membershipService) {
        this.profileService = profileService;
        this.membershipService = membershipService;
    }

    @Operation(summary = "Get all profiles", description = "Retrieve a list of all profiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Flux<ProfileDtoResponse>> getProfiles() {
        return ResponseEntity.ok(profileService.findAll());
    }


    @Operation(summary = "Add a profile", description = "Add a profile`")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "210", description = "Successfully profile created"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Mono<ProfileDtoResponse>> createProfile(@RequestBody ProfileDtoRequest profile) {
        return ResponseEntity.ok(profileService.createProfile(profile));
    }
    @Operation(summary = "Add a membership", description = "Add a membership")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "210", description = "Successfully membership created"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{profileId}/membership")
    public ResponseEntity<Mono<MembershipDtoResponse>> createMembership(@PathVariable String profileId, @RequestBody MembershipDtoRequest membership) {
        return ResponseEntity.ok((membershipService.createMembership(membership, profileId)));
    }
}

