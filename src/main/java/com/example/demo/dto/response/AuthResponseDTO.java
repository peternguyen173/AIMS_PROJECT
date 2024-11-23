package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String jwt;
    private String email;
    public  String name;
    public  String role;

    private boolean status;

    public AuthResponseDTO(String jwt , boolean status) {
        this.status = status;
        this.jwt = jwt;
    }


}
