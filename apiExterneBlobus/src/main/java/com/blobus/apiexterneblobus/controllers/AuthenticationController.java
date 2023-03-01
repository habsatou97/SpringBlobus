package com.blobus.apiexterneblobus.controllers;

import com.blobus.apiexterneblobus.auth.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ablaye faye
 */
@RestController
@RequestMapping("/api/ewallet/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @Operation(summary = "This operation allow to register in the API")
  @ApiResponse(responseCode = "201",description = "Registered success then an  userId and an userSecret are sucessfully generated")
  @ApiResponse(responseCode = "400",description = "Failed to register")
  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }
  @Operation(summary = "This operation allow to authentificate before ussing the API")
  @ApiResponse(responseCode = "201",description = "Authentification success")
  @ApiResponse(responseCode = "400",description = "Failed to authentificate")
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody(required = true) AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }
}
