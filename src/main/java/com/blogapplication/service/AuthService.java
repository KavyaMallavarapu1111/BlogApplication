package com.blogapplication.service;

import com.blogapplication.payload.LoginDto;
import com.blogapplication.payload.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
