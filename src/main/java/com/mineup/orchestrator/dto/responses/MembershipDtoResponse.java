package com.mineup.orchestrator.dto.responses;

import com.mineup.orchestrator.enums.CurrencyTypeEnum;
import com.mineup.orchestrator.enums.MembershipDurationEnum;
import com.mineup.orchestrator.enums.MembershipTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MembershipDtoResponse {
    private String name;
    private String description;
    private MembershipTypeEnum type;
    private BigDecimal price;
    private CurrencyTypeEnum currency;
    private MembershipDurationEnum duration;
    private Integer upToTeamMembers;
    private List<String> features;
    private Boolean isVerified;
    private String profileId;
}
