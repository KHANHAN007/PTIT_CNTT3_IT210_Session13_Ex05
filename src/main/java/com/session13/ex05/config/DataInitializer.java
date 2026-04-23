package com.session13.ex05.config;

import com.session13.ex05.model.entity.Medication;
import com.session13.ex05.model.entity.Patient;
import com.session13.ex05.model.entity.Prescription;
import com.session13.ex05.repository.PatientRepository;
import com.session13.ex05.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (patientRepository.findAll().isEmpty()) {
            initializeSampleData();
        }
    }

    private void initializeSampleData() {
        // Create sample patients
        Patient patient1 = new Patient();
        patient1.setCode("BN001");
        patient1.setName("Nguyễn Văn A");
        patient1.setEmail("nguyen.van.a@email.com");
        patientRepository.save(patient1);

        Patient patient2 = new Patient();
        patient2.setCode("BN002");
        patient2.setName("Trần Thị B");
        patient2.setEmail("tran.thi.b@email.com");
        patientRepository.save(patient2);

        Patient patient3 = new Patient();
        patient3.setCode("BN003");
        patient3.setName("Lê Văn C");
        patient3.setEmail("le.van.c@email.com");
        patientRepository.save(patient3);

        Patient patient4 = new Patient();
        patient4.setCode("BN004");
        patient4.setName("Phạm Thị D");
        patient4.setEmail("pham.thi.d@email.com");
        patientRepository.save(patient4);

        Patient patient5 = new Patient();
        patient5.setCode("BN005");
        patient5.setName("Vũ Văn E");
        patient5.setEmail("vu.van.e@email.com");
        patientRepository.save(patient5);


        Prescription prescription1 = new Prescription();
        prescription1.setPatient(patient1);
        prescription1.setIssueDate(LocalDateTime.now().minusDays(5));

        Medication med1 = new Medication();
        med1.setName("Aspirin 500mg");
        med1.setQuantity(10);
        med1.setPrice(50000.0);
        prescription1.addMedication(med1);

        Medication med2 = new Medication();
        med2.setName("Paracetamol 250mg");
        med2.setQuantity(20);
        med2.setPrice(25000.0);
        prescription1.addMedication(med2);

        prescriptionRepository.save(prescription1);

        // Prescription 2 - for patient 2
        Prescription prescription2 = new Prescription();
        prescription2.setPatient(patient2);
        prescription2.setIssueDate(LocalDateTime.now().minusDays(2));

        Medication med3 = new Medication();
        med3.setName("Amoxicillin 500mg");
        med3.setQuantity(15);
        med3.setPrice(75000.0);
        prescription2.addMedication(med3);

        Medication med4 = new Medication();
        med4.setName("Vitamin C 1000mg");
        med4.setQuantity(30);
        med4.setPrice(35000.0);
        prescription2.addMedication(med4);

        prescriptionRepository.save(prescription2);

        // Prescription 3 - for patient 1 (second prescription)
        Prescription prescription3 = new Prescription();
        prescription3.setPatient(patient1);
        prescription3.setIssueDate(LocalDateTime.now().minusDays(1));

        Medication med5 = new Medication();
        med5.setName("Omeprazole 20mg");
        med5.setQuantity(14);
        med5.setPrice(120000.0);
        prescription3.addMedication(med5);

        prescriptionRepository.save(prescription3);

        // Prescription 4 - for patient 3
        Prescription prescription4 = new Prescription();
        prescription4.setPatient(patient3);
        prescription4.setIssueDate(LocalDateTime.now());

        Medication med6 = new Medication();
        med6.setName("Ibuprofen 400mg");
        med6.setQuantity(12);
        med6.setPrice(55000.0);
        prescription4.addMedication(med6);

        Medication med7 = new Medication();
        med7.setName("Cetirizine 10mg");
        med7.setQuantity(10);
        med7.setPrice(40000.0);
        prescription4.addMedication(med7);

        Medication med8 = new Medication();
        med8.setName("Metformin 500mg");
        med8.setQuantity(60);
        med8.setPrice(150000.0);
        prescription4.addMedication(med8);

        prescriptionRepository.save(prescription4);

        // Prescription 5 - for patient 4
        Prescription prescription5 = new Prescription();
        prescription5.setPatient(patient4);
        prescription5.setIssueDate(LocalDateTime.now().minusDays(3));

        Medication med9 = new Medication();
        med9.setName("Azithromycin 250mg");
        med9.setQuantity(6);
        med9.setPrice(180000.0);
        prescription5.addMedication(med9);

        Medication med10 = new Medication();
        med10.setName("Loratadine 10mg");
        med10.setQuantity(30);
        med10.setPrice(45000.0);
        prescription5.addMedication(med10);

        prescriptionRepository.save(prescription5);

        // Prescription 6 - for patient 5
        Prescription prescription6 = new Prescription();
        prescription6.setPatient(patient5);
        prescription6.setIssueDate(LocalDateTime.now().minusDays(7));

        Medication med11 = new Medication();
        med11.setName("Lisinopril 10mg");
        med11.setQuantity(28);
        med11.setPrice(95000.0);
        prescription6.addMedication(med11);

        prescriptionRepository.save(prescription6);

        System.out.println("✓ Sample data initialized successfully!");
        System.out.println("✓ Created 5 patients and 6 prescriptions with 11 medications");
    }
}

