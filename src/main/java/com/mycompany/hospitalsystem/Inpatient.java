package com.mycompany.hospitalsystem;

public class Inpatient extends Patient {
    private String wardNumber;
    private int daysAdmitted;
    private double dailyRate;

    public Inpatient(String patientId, String name, int age, PatientCategory category, String wardNumber, int daysAdmitted, double dailyRate) {
        super(patientId, name, age, category);
        this.wardNumber = wardNumber;
        this.daysAdmitted = daysAdmitted;
        this.dailyRate = dailyRate;
    }

    public double calculateTotalBill() {
        return daysAdmitted * dailyRate;
    }

    @Override
    public void displayPatientInfo() {
        super.displayPatientInfo();
        System.out.println("Ward: " + wardNumber + ", Days: " + daysAdmitted + ", Total Bill: R" + calculateTotalBill());
    }
}