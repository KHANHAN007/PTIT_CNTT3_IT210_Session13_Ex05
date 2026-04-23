package com.session13.ex05.controller;

import com.session13.ex05.model.dto.MedicationDTO;
import com.session13.ex05.model.dto.PatientDTO;
import com.session13.ex05.model.dto.PrescriptionDTO;
import com.session13.ex05.service.PatientService;
import com.session13.ex05.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PatientService patientService;

    @GetMapping
    public String index() {
        return "redirect:/prescriptions";
    }

    @GetMapping("/prescriptions")
    public String listPrescriptions(
            @RequestParam(value = "patientCode", required = false) String patientCode,
            Model model) {
        try {
            List<PrescriptionDTO> prescriptions;

            if (patientCode != null && !patientCode.trim().isEmpty()) {
                prescriptions = prescriptionService.searchByPatientCode(patientCode.trim());
                model.addAttribute("searchCode", patientCode.trim());
            } else {
                prescriptions = prescriptionService.getAllPrescriptions();
                model.addAttribute("searchCode", "");
            }

            model.addAttribute("prescriptions", prescriptions);
            model.addAttribute("title", "Danh sách Đơn thuốc");
            return "prescriptions/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Lỗi khi tải danh sách đơn thuốc: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/prescriptions/{id}")
    public String viewPrescription(@PathVariable Long id, Model model) {
        try {
            PrescriptionDTO prescription = prescriptionService.getPrescriptionById(id);
            if (prescription == null) {
                model.addAttribute("errorMessage", "Không tìm thấy đơn thuốc với ID: " + id);
                return "error";
            }
            model.addAttribute("prescription", prescription);
            model.addAttribute("title", "Chi tiết Đơn thuốc");
            return "prescriptions/detail";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Lỗi khi tải chi tiết đơn thuốc: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/prescriptions/new")
    public String newPrescriptionForm(Model model) {
        try {
            List<PatientDTO> patients = patientService.getAllPatients();
            model.addAttribute("patients", patients);
            model.addAttribute("prescription", new PrescriptionDTO());
            model.addAttribute("medications", new ArrayList<MedicationDTO>());
            model.addAttribute("title", "Thêm Đơn thuốc mới");
            return "prescriptions/form";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Lỗi khi tải form: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/prescriptions")
    public String createPrescription(
            @RequestParam Long patientId,
            @RequestParam(required = false) List<String> medicationNames,
            @RequestParam(required = false) List<Integer> medicationQuantities,
            @RequestParam(required = false) List<Double> medicationPrices,
            Model model) {
        try {
            if (medicationNames == null || medicationNames.isEmpty()) {
                model.addAttribute("errorMessage", "Phải thêm ít nhất một loại thuốc");
                List<PatientDTO> patients = patientService.getAllPatients();
                model.addAttribute("patients", patients);
                model.addAttribute("prescription", new PrescriptionDTO());
                model.addAttribute("medications", new ArrayList<MedicationDTO>());
                return "prescriptions/form";
            }

            List<MedicationDTO> medications = new ArrayList<>();
            for (int i = 0; i < medicationNames.size(); i++) {
                String name = medicationNames.get(i);
                Integer quantity = medicationQuantities != null && i < medicationQuantities.size() ? medicationQuantities.get(i) : 0;
                Double price = medicationPrices != null && i < medicationPrices.size() ? medicationPrices.get(i) : 0.0;

                if (name == null || name.trim().isEmpty()) {
                    continue;
                }

                MedicationDTO med = new MedicationDTO();
                med.setName(name.trim());
                med.setQuantity(quantity);
                med.setPrice(price);
                medications.add(med);
            }

            if (medications.isEmpty()) {
                model.addAttribute("errorMessage", "Phải thêm ít nhất một loại thuốc");
                List<PatientDTO> patients = patientService.getAllPatients();
                model.addAttribute("patients", patients);
                model.addAttribute("prescription", new PrescriptionDTO());
                model.addAttribute("medications", new ArrayList<MedicationDTO>());
                return "prescriptions/form";
            }

            prescriptionService.createPrescription(patientId, medications);
            return "redirect:/prescriptions?success=true";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            List<PatientDTO> patients = patientService.getAllPatients();
            model.addAttribute("patients", patients);
            model.addAttribute("prescription", new PrescriptionDTO());
            model.addAttribute("medications", new ArrayList<MedicationDTO>());
            return "prescriptions/form";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Lỗi khi tạo đơn thuốc: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/prescriptions/{id}/delete")
    public String deletePrescription(@PathVariable Long id, Model model) {
        try {
            prescriptionService.deletePrescription(id);
            return "redirect:/prescriptions?deleted=true";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Lỗi khi xóa đơn thuốc: " + e.getMessage());
            return "error";
        }
    }
}

