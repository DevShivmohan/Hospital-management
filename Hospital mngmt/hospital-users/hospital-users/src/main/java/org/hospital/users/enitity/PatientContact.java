package org.hospital.users.enitity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class PatientContact {
    @Id
    private int id;
    @Pattern(regexp="^[0-9]{10}",message = "Incorrect mobile")
    private String mobile;
    @Pattern(regexp="(^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$){10}",message = "Incorrect email")
    private String email;
}
