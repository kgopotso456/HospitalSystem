package com.mycompany.hospitalsystem;

public class Patient implements Comparable<Patient> {
    private String patientId;
    private String name;
    private String surname;
    private int age;
    private PatientCategory category;
    private String assignedWard;
    private boolean hasBedAssigned;

    public Patient(String patientId, String name, String surname, int age, PatientCategory category) {
        this.patientId = patientId;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.category = category;
        this.assignedWard = "Unassigned";
        this.hasBedAssigned = false;
    }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public PatientCategory getCategory() { return category; }
    public void setCategory(PatientCategory category) { this.category = category; }

    public String getAssignedWard() { return assignedWard; }
    public void setAssignedWard(String assignedWard) { this.assignedWard = assignedWard; }

    public boolean isHasBedAssigned() { return hasBedAssigned; }
    public void setHasBedAssigned(boolean hasBedAssigned) { this.hasBedAssigned = hasBedAssigned; }

    public void displayPatientInfo() {
        System.out.println("ID: " + patientId + ", Name: " + name + " " + surname + ", Age: " + age + 
                           ", Category: " + category + ", Ward: " + assignedWard + ", Bed Assigned: " + (hasBedAssigned ? "Yes" : "No"));
    }

    @Override
    public int compareTo(Patient other) {
        return this.surname.compareToIgnoreCase(other.surname);
    }
}