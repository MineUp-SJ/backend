package com.mineup.orchestrator.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProfileDtoResponse {
    private  String id;
    private String name;
    private String description;
    private Boolean isVerified;
}
