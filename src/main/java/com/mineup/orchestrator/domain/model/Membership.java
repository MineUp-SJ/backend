package com.mineup.orchestrator.domain.model;

import com.mineup.orchestrator.enums.CurrencyTypeEnum;
import com.mineup.orchestrator.enums.MembershipDurationEnum;
import com.mineup.orchestrator.enums.MembershipTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Membership {

    private String id;
    private String name;
    private String description;
    private MembershipTypeEnum type;
    private BigDecimal price;
    private CurrencyTypeEnum currency;
    private MembershipDurationEnum duration;
    private Integer upToTeamMembers;
    private List<String> features;
    private String profileId;
    private Boolean isVerified;
    private Boolean isDeleted;
}
