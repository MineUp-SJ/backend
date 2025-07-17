package com.mineup.orchestrator.adapter.in.web;

import com.mineup.orchestrator.adapter.in.exceptions.RequestBodyPathException;
import com.mineup.orchestrator.adapter.in.mapper.MineralWebMapper;
import com.mineup.orchestrator.domain.model.Mineral;
import com.mineup.orchestrator.adapter.in.dto.MineralDtoRequest;
import com.mineup.orchestrator.adapter.in.dto.MineralDtoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.mineup.orchestrator.application.service.MineralService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/minerals")
public class MineralController {

    private final MineralService mineralService;
    private final MineralWebMapper mineralWebMapper;

    public MineralController(MineralService mineralService, MineralWebMapper mineralWebMapper) {
        this.mineralService = mineralService;
        this.mineralWebMapper = mineralWebMapper;
    }

    @Operation(summary = "Get all minerals", description = "Retrieve a list of all minerals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public Flux<MineralDtoResponse> getMinerals() {
        return mineralService.findAll()
                .map(mineralWebMapper::toDto);
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
    public Mono<MineralDtoResponse> createMineral(@Valid @RequestBody MineralDtoRequest mineral) {
      if (mineral == null || mineral.getName() == null || mineral.getName().isEmpty()) {
          return Mono.error(new RequestBodyPathException("Mineral name must not be null or empty"));
      }
      Mineral request = mineralWebMapper.toDomain(mineral);
        return mineralService.createMineral(request)
                .map(mineralWebMapper::toDto);
    }
    @DeleteMapping("/{id}")
    public void deleteMineral(@Valid @PathVariable String id) {
      if(id == null || id.isEmpty()) {
          throw new RequestBodyPathException("Mineral ID must not be null or empty");
      }
      mineralService.deleteMineral(id);
    }
/*
    @PutMapping("/{id}")
    public ResponseEntity<Mineral> updateMineral(@PathVariable Long id, @RequestBody Mineral mineral) {
        return ResponseEntity.ok(mineralService.updateMineral(id, mineral));
    }


    }*/
}
