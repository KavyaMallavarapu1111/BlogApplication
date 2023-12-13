package com.blogapplication.controllers;


import com.blogapplication.payload.JwtAuthResponse;
import com.blogapplication.payload.LoginDto;
import com.blogapplication.payload.RegisterDto;
import com.blogapplication.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login","/signin"})
    @Operation(
            summary = "Login REST API",
            description = "Login REST API is used to login"
    )
    @ApiResponse(
            description = "Http Status 200 Success.",
            responseCode = "200"
    )
    //build login rest api
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto)
    {
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/signup","/register"})
    @Operation(
            summary = "Register REST API",
            description = "Register REST API is used to register"
    )
    @ApiResponse(
            description = "Http Status 201 Created.",
            responseCode = "201"
    )
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto)
    {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
