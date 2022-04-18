package org.hospital.users.service.impl;

import org.hospital.users.PatientRepository;
import org.hospital.users.enitity.Patient;
import org.hospital.users.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public ResponseEntity<?> savePatient(Patient patient) {
        return null;
    }

    @Override
    public ResponseEntity<?> updatePatient(String patientId,Patient patient) {
        return null;
    }

    @Override
    public ResponseEntity<?> deletePatient(String patientId) {
        return null;
    }

    @Override
    public ResponseEntity<?> fetchPatients() {
        return null;
    }

    @Override
    public ResponseEntity<?> fetchPatientsByName() {
        return null;
    }

    @Override
    public ResponseEntity<?> fetchPatientsByAge(int age) {
        return null;
    }

    @Override
    public ResponseEntity<?> fetchPatientsByAgeGreater(int age) {
        return null;
    }

    @Override
    public ResponseEntity<?> fetchPatientsByAgeLower(int age) {
        return null;
    }

    @Override
    public ResponseEntity<?> fetchPatientsByMobile(String mobile) {
        return null;
    }

    @Override
    public ResponseEntity<?> fetchPatientByEmail(String email) {
        return null;
    }

    @Override
    public ResponseEntity<?> fetchPatientByPatientId(String patientId) {
        return null;
    }

    @Override
    public ResponseEntity<?> fetchPatientsDescByDate() {
        return null;
    }

    @Override
    public ResponseEntity<?> fetchPatientsAscByDate() {
        return null;
    }
}
