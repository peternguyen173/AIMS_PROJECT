package com.example.demo.entity;

import com.example.demo.entity.enumModel.ERole;
import com.example.demo.entity.enumModel.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private ERole role;
    private UserStatus status;
    private String password;
    private String address;
    private String phone;

    public User() {

    }
}
