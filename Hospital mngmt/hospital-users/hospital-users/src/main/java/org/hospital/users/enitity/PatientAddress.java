package org.hospital.users.enitity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class PatientAddress {
    @Id
    private int id;
    @Size(min = 5,max = 20,message = "village should be max 20 and min 5 character")
    private String village;
    @Size(min = 5,max = 20,message = "district should be max 20 and min 5 character")
    private String district;
    @Size(min = 5,max = 40,message = "address should be max 40 and min 5 character")
    private String address;
}
