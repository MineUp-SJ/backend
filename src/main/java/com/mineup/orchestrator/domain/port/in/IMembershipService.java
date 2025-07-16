package com.mineup.orchestrator.domain.port.in;

import com.mineup.orchestrator.domain.model.Membership;
import reactor.core.publisher.Mono;

public interface IMembershipService {
   public Mono<Membership> createMembership(Membership membership, String profileId);

}
