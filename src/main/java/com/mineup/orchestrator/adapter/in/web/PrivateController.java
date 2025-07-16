package com.mineup.orchestrator.adapter.in.web;

import org.springframework.http.ResponseEntity;
 import com.mineup.orchestrator.adapter.in.dto.auth.MessageDto;
 import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrivateController {

    @GetMapping("/private")
    public ResponseEntity<MessageDto> privateMessages(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(new MessageDto("Hello " + jwt.getClaim("name")));
    }
}