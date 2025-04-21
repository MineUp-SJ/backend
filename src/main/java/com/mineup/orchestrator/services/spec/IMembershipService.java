package com.mineup.orchestrator.services.spec;

import com.mineup.orchestrator.dto.requests.MembershipDtoRequest;
import com.mineup.orchestrator.dto.requests.ProfileDtoRequest;
import com.mineup.orchestrator.dto.responses.MembershipDtoResponse;
import com.mineup.orchestrator.dto.responses.ProfileDtoResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMembershipService {
   public Mono<MembershipDtoResponse> createMembership(MembershipDtoRequest membershipDtoRequest,String profileId);

}
