package com.ledes.desafio.institute_manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcheck")
@Tag(name = "Health Check", description = "API para verificar o status da aplicação")
public class HealthCheckController {

    @GetMapping
    @Operation(summary = "Verificar o status da aplicação", description = "Retorna o status atual da aplicação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A aplicação está funcionando corretamente")
    })
    public ResponseEntity<String> checkHealth() {
        return new ResponseEntity<>("Application is running", HttpStatus.OK);
    }
}
