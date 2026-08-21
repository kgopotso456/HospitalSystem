package com.mycompany.hospitalsystem;

public class Inpatient extends Patient {
    private int daysAdmitted;
    private double dailyRate;

    public Inpatient(String patientId, String name, String surname, int age, PatientCategory category, int daysAdmitted, double dailyRate) {
        super(patientId, name, surname, age, category);
        this.daysAdmitted = daysAdmitted;
        this.dailyRate = dailyRate;
    }

    public int getDaysAdmitted() { return daysAdmitted; }
    public void setDaysAdmitted(int daysAdmitted) { this.daysAdmitted = daysAdmitted; }

    public double getDailyRate() { return dailyRate; }
    public void setDailyRate(double dailyRate) { this.dailyRate = dailyRate; }

    public double calculateTotalBill() {
        return daysAdmitted * dailyRate;
    }

    @Override
    public void displayPatientInfo() {
        super.displayPatientInfo();
        System.out.println("Days Admitted: " + daysAdmitted + ", Daily Rate: R" + dailyRate + ", Total Bill: R" + calculateTotalBill());
    }
}