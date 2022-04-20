package com.shiv.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private String name;
    private String email;
    private String password;
    private String role;
    private String mobile;
}
