package com.mycompany.hospitalsystem;

public class Patient {
    private String patientId;
    private String name;
    private int age;
    private PatientCategory category;

    public Patient(String patientId, String name, int age, PatientCategory category) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.category = category;
    }

    public String getPatientId() { return patientId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public PatientCategory getCategory() { return category; }

    public void displayPatientInfo() {
        System.out.println("ID: " + patientId + ", Name: " + name + ", Age: " + age + ", Category: " + category);
    }
}
