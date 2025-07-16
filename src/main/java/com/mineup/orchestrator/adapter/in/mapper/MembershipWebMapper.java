package com.mineup.orchestrator.adapter.in.mapper;


import com.mineup.orchestrator.domain.model.Membership;
import com.mineup.orchestrator.adapter.in.dto.ProfileDtoRequest;
import com.mineup.orchestrator.adapter.in.dto.MembershipDtoResponse;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MembershipWebMapper {

    MembershipDtoResponse toDto(Membership membership);

    Membership toDomain(ProfileDtoRequest profileDtoRequest);
}
