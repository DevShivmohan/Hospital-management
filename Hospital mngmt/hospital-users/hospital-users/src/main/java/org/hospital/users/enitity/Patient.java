package org.hospital.users.enitity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Patient {
    @Id
    private UUID uuid;

    private String patientId;

    @Size(min = 5,max = 20,message = "Name should be max 20 and min 5 character")
    private String name;

    @Size(min = 5,max = 20,message = "Father name should be max 20 and min 5 character")
    private String fatherName;

    @NotBlank(message = "Age required")
    private int age;

    @ManyToOne
    @JoinColumn(name = "patient_contact_id")
    private PatientContact patientContact;

    @ManyToOne
    @JoinColumn(name = "patient_address_id")
    private PatientAddress patientAddress;

    private Date date;
}
