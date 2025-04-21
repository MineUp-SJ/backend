package com.mineup.orchestrator.mappers.spec;

import com.mineup.orchestrator.dto.requests.MembershipDtoRequest;
import com.mineup.orchestrator.dto.responses.MembershipDtoResponse;
import com.mineup.orchestrator.models.Membership;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IMembershipMapper {

    public Membership toEntity(MembershipDtoRequest membershipDtoRequest);
    public MembershipDtoResponse toDto(Membership membership);
}
