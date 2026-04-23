package com.session13.ex05.service;

import com.session13.ex05.model.dto.PatientDTO;
import com.session13.ex05.model.entity.Patient;
import com.session13.ex05.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public PatientDTO getPatientById(Long id) {
        return patientRepository.findById(id)
            .map(this::convertToDTO)
            .orElse(null);
    }

    public PatientDTO getPatientByCode(String code) {
        return patientRepository.findByCode(code)
            .map(this::convertToDTO)
            .orElse(null);
    }

    public PatientDTO createPatient(String code, String name, String email) {
        if (patientRepository.findByCode(code).isPresent()) {
            throw new IllegalArgumentException("Patient code already exists: " + code);
        }

        Patient patient = new Patient();
        patient.setCode(code);
        patient.setName(name);
        patient.setEmail(email);

        Patient savedPatient = patientRepository.save(patient);
        return convertToDTO(savedPatient);
    }

    public PatientDTO updatePatient(Long id, String code, String name, String email) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + id));

        patient.setCode(code);
        patient.setName(name);
        patient.setEmail(email);

        Patient updatedPatient = patientRepository.save(patient);
        return convertToDTO(updatedPatient);
    }

    public void deletePatient(Long id) {
        patientRepository.delete(id);
    }

    private PatientDTO convertToDTO(Patient patient) {
        return new PatientDTO(
            patient.getId(),
            patient.getCode(),
            patient.getName(),
            patient.getEmail()
        );
    }
}

