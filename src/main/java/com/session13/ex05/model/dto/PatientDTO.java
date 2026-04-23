package com.session13.ex05.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String name;
    private String email;
}

