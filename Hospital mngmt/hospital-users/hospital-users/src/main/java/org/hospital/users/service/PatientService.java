package org.hospital.users.service;

import org.hospital.users.enitity.Patient;
import org.springframework.http.ResponseEntity;

public interface PatientService {
    public ResponseEntity<?> savePatient(Patient patient);
    public ResponseEntity<?> updatePatient(String patientId,Patient patient);
    public ResponseEntity<?> deletePatient(String patientId);
    public ResponseEntity<?> fetchPatients();
    public ResponseEntity<?> fetchPatientsByName();
    public ResponseEntity<?> fetchPatientsByAge(int age);
    public ResponseEntity<?> fetchPatientsByAgeGreater(int age);
    public ResponseEntity<?> fetchPatientsByAgeLower(int age);
    public ResponseEntity<?> fetchPatientsByMobile(String mobile);
    public ResponseEntity<?> fetchPatientByEmail(String email);
    public ResponseEntity<?> fetchPatientByPatientId(String patientId);
    public ResponseEntity<?> fetchPatientsDescByDate();
    public ResponseEntity<?> fetchPatientsAscByDate();
}
