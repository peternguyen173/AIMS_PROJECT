package com.example.demo.service.impl;

import com.example.demo.config.secutiry.JwtTokenProvider;
import com.example.demo.dto.request.AuthRequestDTO;
import com.example.demo.dto.request.UserRequestDTO;
import com.example.demo.dto.response.AuthResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.enumModel.ERole;
import com.example.demo.entity.enumModel.UserStatus;
import com.example.demo.exception.InternalServerErrorException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CartService;
import com.example.demo.service.UserService;
import jakarta.mail.MessagingException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.BadCredentialsException;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    //    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    //    @Override
    public User findUserByJwt(String jwt) throws Exception {
        String email = jwtTokenProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("USER NOT FOUND"));
        return user;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .roles(String.valueOf(user.getRole()))
                .build();
    }
    public AuthResponseDTO createUser(UserRequestDTO user) throws InternalServerErrorException {
        String email = user.getEmail();
        String password = user.getPassword();
        String role = user.getRole();
        String verificationToken = UUID.randomUUID().toString();
        if(userRepository.existsByEmail(email)) {
            throw new InternalServerErrorException();
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(ERole.valueOf(role));
        newUser.setStatus(UserStatus.UNVERIFIED);
        newUser = userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return new AuthResponseDTO(null, true);
    }
    public AuthResponseDTO signIn(AuthRequestDTO authRequestDTO) throws BadCredentialsException, BadRequestException {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        String username = authRequestDTO.getEmail();
        String password = authRequestDTO.getPassword();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BadRequestException("User does not exist"));

        // Check if the user is unverified
        if (user.getStatus() == UserStatus.UNVERIFIED) {
            throw new BadRequestException("User account is unverified. Please verify your account to log in.");
        }
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtTokenProvider.generateToken(authentication);
        authResponseDTO.setStatus(true);
        authResponseDTO.setJwt(accessToken);
        authResponseDTO.setEmail(username);
        authResponseDTO.setRole(authentication.getAuthorities().toString());
        return authResponseDTO;
    }
    private Authentication authenticate(String username, String password) throws BadRequestException {
        UserDetails userDetails = loadUserByUsername(username);
        System.out.println(userDetails);
        if(userDetails == null) {
            throw new BadCredentialsException("username.validate.invalid");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("password.validate.invalid");
        }
        User user = userRepository.findByEmail(username).get();

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public void logout() {
        String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        SecurityContextHolder.clearContext();
        System.out.println(SecurityContextHolder.getContext());
    }

    public User changePassword(String jwt, String newPassword) throws Exception {
        User user = findUserByJwt(jwt);
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

//    public String confirmEmail(String token) throws Exception {
//        User user = userRepository.findByVerificationToken(token)
//                .orElseThrow(() -> new NotFoundException(messages.getString("user.validate.not-found")));
//        if(!token.equals(user.getVerificationToken())){
//            userRepository.delete(user);
//            throw new BadRequestException(messages.getString("user.validate.token-invalid"));
//        }
//        user.setStatus(Constants.ENTITY_STATUS.ACTIVE);
//        userRepository.save(user);
//        return messages.getString("email.verify.success");
//    }

    public User deleteUser(Long userId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user not found"));
        userRepository.deleteById(userId);
        return user;
    }
}
