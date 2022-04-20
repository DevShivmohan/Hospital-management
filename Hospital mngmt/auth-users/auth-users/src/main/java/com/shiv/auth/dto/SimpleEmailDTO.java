package com.shiv.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SimpleEmailDTO {
    private String email;
    private String subject;
    private String message;
}
