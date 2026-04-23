package com.session13.ex05.service;

import com.session13.ex05.model.dto.MedicationDTO;
import com.session13.ex05.model.dto.PrescriptionDTO;
import com.session13.ex05.model.entity.Medication;
import com.session13.ex05.model.entity.Prescription;
import com.session13.ex05.model.entity.Patient;
import com.session13.ex05.repository.PrescriptionRepository;
import com.session13.ex05.repository.PatientRepository;
import com.session13.ex05.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    public List<PrescriptionDTO> getAllPrescriptions() {
        return prescriptionRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<PrescriptionDTO> searchByPatientCode(String patientCode) {
        return prescriptionRepository.findByPatientCode(patientCode).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public PrescriptionDTO getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id)
            .map(this::convertToDTO)
            .orElse(null);
    }

    public PrescriptionDTO createPrescription(Long patientId, List<MedicationDTO> medicationDTOs) {
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + patientId));

        Prescription prescription = new Prescription();
        prescription.setPatient(patient);
        prescription.setIssueDate(LocalDateTime.now());

        // Validate and add medications
        for (MedicationDTO medDTO : medicationDTOs) {
            if (medDTO.getQuantity() < 0) {
                throw new IllegalArgumentException("Số lượng thuốc không được âm: " + medDTO.getName());
            }
            if (medDTO.getQuantity() == 0) {
                throw new IllegalArgumentException("Số lượng thuốc phải > 0: " + medDTO.getName());
            }

            Medication medication = new Medication();
            medication.setName(medDTO.getName());
            medication.setQuantity(medDTO.getQuantity());
            medication.setPrice(medDTO.getPrice());
            prescription.addMedication(medication);
        }

        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return convertToDTO(savedPrescription);
    }

    public PrescriptionDTO updatePrescription(Long id, List<MedicationDTO> medicationDTOs) {
        Prescription prescription = prescriptionRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Prescription not found: " + id));

        prescription.getMedications().clear();

        for (MedicationDTO medDTO : medicationDTOs) {
            if (medDTO.getQuantity() < 0) {
                throw new IllegalArgumentException("Số lượng thuốc không được âm: " + medDTO.getName());
            }
            if (medDTO.getQuantity() == 0) {
                throw new IllegalArgumentException("Số lượng thuốc phải > 0: " + medDTO.getName());
            }

            Medication medication = new Medication();
            medication.setName(medDTO.getName());
            medication.setQuantity(medDTO.getQuantity());
            medication.setPrice(medDTO.getPrice());
            prescription.addMedication(medication);
        }

        Prescription updatedPrescription = prescriptionRepository.save(prescription);
        return convertToDTO(updatedPrescription);
    }

    public void deletePrescription(Long id) {
        prescriptionRepository.delete(id);
    }

    private PrescriptionDTO convertToDTO(Prescription prescription) {
        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setId(prescription.getId());
        dto.setPatientId(prescription.getPatient().getId());
        dto.setPatientCode(prescription.getPatient().getCode());
        dto.setPatientName(prescription.getPatient().getName());
        dto.setPatientEmail(prescription.getPatient().getEmail());
        dto.setIssueDate(prescription.getIssueDate());

        dto.setMedications(
            prescription.getMedications().stream()
                .map(med -> new MedicationDTO(
                    med.getId(),
                    med.getName(),
                    med.getQuantity(),
                    med.getPrice()
                ))
                .collect(Collectors.toList())
        );

        return dto;
    }
}

