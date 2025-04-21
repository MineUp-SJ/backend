package com.mineup.orchestrator.controllers;

import com.mineup.orchestrator.dto.requests.MineralDtoRequest;
import com.mineup.orchestrator.dto.responses.MineralDtoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mineup.orchestrator.services.impl.MineralService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/minerals")
public class MineralController {

    private final MineralService mineralService;

    public MineralController(MineralService mineralService) {
        this.mineralService = mineralService;
    }

    @Operation(summary = "Get all minerals", description = "Retrieve a list of all minerals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Flux<MineralDtoResponse>> getMinerals() {
        return ResponseEntity.ok(mineralService.findAll());
    }

  /*  @GetMapping("/{id}")
    public ResponseEntity<Mineral> getMineral(@PathVariable Long id) {
        return ResponseEntity.ok(mineralService.getMineral(id));
    }
*/
  @Operation(summary = "Post a mineral", description = "Post a mineral")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "210", description = "Successfully mineral created"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
    @PostMapping
    public ResponseEntity<Mono<MineralDtoResponse>> createMineral(@RequestBody MineralDtoRequest mineral) {
        return ResponseEntity.ok(mineralService.createMineral(mineral));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMineral(@PathVariable String id) {
        mineralService.deleteMineral(id);
        return ResponseEntity.noContent().build();
    }
/*
    @PutMapping("/{id}")
    public ResponseEntity<Mineral> updateMineral(@PathVariable Long id, @RequestBody Mineral mineral) {
        return ResponseEntity.ok(mineralService.updateMineral(id, mineral));
    }


    }*/
}
