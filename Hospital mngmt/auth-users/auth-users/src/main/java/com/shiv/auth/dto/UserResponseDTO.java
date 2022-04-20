package com.shiv.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String name;
    private String email;
    private String role;
    private String mobile;
    private boolean isActive;
    private Date createdOn;
}
