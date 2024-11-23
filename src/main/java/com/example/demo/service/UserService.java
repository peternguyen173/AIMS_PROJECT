package com.example.demo.service;

import com.example.demo.dto.request.AuthRequestDTO;
import com.example.demo.dto.request.UserRequestDTO;
import com.example.demo.dto.response.AuthResponseDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void logout();

    AuthResponseDTO signIn(AuthRequestDTO authRequestDTO) throws BadRequestException;

    AuthResponseDTO createUser(UserRequestDTO user);
}
