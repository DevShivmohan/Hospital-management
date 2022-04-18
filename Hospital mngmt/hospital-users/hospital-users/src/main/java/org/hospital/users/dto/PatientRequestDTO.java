package org.hospital.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDTO {
    private String name;
    private String fatherName;
    private int age;
    private String mobile;
    private String email;
    private String village;
    private String district;
    private String address;
}
