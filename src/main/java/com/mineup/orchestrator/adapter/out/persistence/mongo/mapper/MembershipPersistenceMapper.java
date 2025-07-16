package com.mineup.orchestrator.adapter.out.persistence.mongo.mapper;

import com.mineup.orchestrator.adapter.out.persistence.mongo.document.MembershipDocument;

import com.mineup.orchestrator.domain.model.Membership;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MembershipPersistenceMapper {

    MembershipDocument toDocument(Membership membership);

    Membership toDomain(MembershipDocument membershipDocument);

}
