package com.mineup.orchestrator.adapter.out.persistence.mongo.mapper;


import com.mineup.orchestrator.adapter.out.persistence.mongo.document.ProfileDocument;
import com.mineup.orchestrator.domain.model.Profile;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProfilePersistenceMapper {

    Profile toDomain(ProfileDocument profileDocument);

    ProfileDocument toDocument(Profile profile);
}
