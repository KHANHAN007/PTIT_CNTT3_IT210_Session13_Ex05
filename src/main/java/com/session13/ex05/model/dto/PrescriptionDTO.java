package com.session13.ex05.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long patientId;
    private String patientCode;
    private String patientName;
    private String patientEmail;
    private LocalDateTime issueDate;
    private List<MedicationDTO> medications = new ArrayList<>();
}

