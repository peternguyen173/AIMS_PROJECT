package com.example.demo.controller;

import com.example.demo.config.secutiry.JwtTokenProvider;
import com.example.demo.dto.request.AuthRequestDTO;
import com.example.demo.dto.request.UserRequestDTO;
import com.example.demo.dto.response.AuthResponseDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserService userService;
//    @PostMapping("/signup")
//    @Transactional
//    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDTO user) throws Exception {
//        AuthResponseDTO authResponseDTO= userService.createUser(user);
//        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
//    }
    @GetMapping
    public String hello() {
        return "Hello World";
    }
    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDTO authRequestDTO) throws Exception{
        try {
            AuthResponseDTO authResponseDTO= userService.signIn(authRequestDTO);
            System.out.println(SecurityContextHolder.getContext());
            return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        try{
            userService.logout();
            return ResponseEntity.ok().body("Đăng xuất thành công");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
