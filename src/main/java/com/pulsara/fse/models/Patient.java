package com.pulsara.fse.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class Patient {

    @Enumerated(EnumType.STRING)
    @Column(name = "patient_type")
    private PatientType patientType;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "instructions")
    private String instructions;

    // Getters and setters
    public PatientType getPatientType() {
        return patientType;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getInstructions() {
        return instructions;
    }
}
