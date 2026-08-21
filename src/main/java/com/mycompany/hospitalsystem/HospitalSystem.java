package com.mycompany.hospitalsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class HospitalSystem {
    public static void main(String[] args) {
        ArrayList<Patient> patients = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- MediCare Hospital System Menu ---");
            System.out.println("1. Register Patient");
            System.out.println("2. Search Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Display All Patients");
            System.out.println("6. Allocate Bed");
            System.out.println("7. Release Bed");
            System.out.println("8. Display Ward Layout");
            System.out.println("9. Display Available Beds");
            System.out.println("10. Display Occupied Beds");
            System.out.println("11. Display Ward Report");
            System.out.println("12. Sort Patients by Surname");
            System.out.println("13. Sort Patients by Patient ID");
            System.out.println("0. Exit");
            System.out.print("Enter your choice (0-13): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1: // Register Patient
                    System.out.print("Enter Patient ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter First Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Surname: ");
                    String surname = scanner.nextLine();
                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    
                    System.out.print("Enter Days Admitted: ");
                    int days = scanner.nextInt();
                    System.out.print("Enter Daily Rate: ");
                    double rate = scanner.nextDouble();
                    scanner.nextLine();

                    patients.add(new Inpatient(id, name, surname, age, PatientCategory.GENERAL, days, rate));
                    System.out.println("Patient registered successfully!");
                    break;

                case 2: // Search Patient
                    System.out.print("Enter Patient ID to search: ");
                    String searchId = scanner.nextLine();
                    Patient foundPatient = findPatientById(patients, searchId);
                    if (foundPatient != null) {
                        foundPatient.displayPatientInfo();
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 3: // Update Patient
                    System.out.print("Enter Patient ID to update: ");
                    String updateId = scanner.nextLine();
                    Patient pToUpdate = findPatientById(patients, updateId);
                    if (pToUpdate != null) {
                        System.out.print("Enter new First Name: ");
                        pToUpdate.setName(scanner.nextLine());
                        System.out.print("Enter new Surname: ");
                        pToUpdate.setSurname(scanner.nextLine());
                        System.out.print("Enter new Age: ");
                        pToUpdate.setAge(scanner.nextInt());
                        scanner.nextLine();
                        System.out.println("Patient information updated successfully!");
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 4: // Delete Patient
                    System.out.print("Enter Patient ID to delete: ");
                    String deleteId = scanner.nextLine();
                    boolean removed = patients.removeIf(p -> p.getPatientId().equalsIgnoreCase(deleteId));
                    if (removed) {
                        System.out.println("Patient deleted successfully.");
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 5: // Display All Patients
                    if (patients.isEmpty()) {
                        System.out.println("No patients registered.");
                    } else {
                        for (Patient p : patients) {
                            p.displayPatientInfo();
                            System.out.println("-----------------------------------");
                        }
                    }
                    break;

                case 6: // Allocate Bed
                    System.out.print("Enter Patient ID for bed allocation: ");
                    String bedId = scanner.nextLine();
                    Patient bedPatient = findPatientById(patients, bedId);
                    if (bedPatient != null) {
                        if (bedPatient.isHasBedAssigned()) {
                            System.out.println("Patient already has a bed assigned in ward: " + bedPatient.getAssignedWard());
                        } else {
                            System.out.print("Enter Ward Name/Number (e.g., Ward A): ");
                            String wardName = scanner.nextLine();
                            bedPatient.setAssignedWard(wardName);
                            bedPatient.setHasBedAssigned(true);
                            System.out.println("Bed successfully allocated in " + wardName);
                        }
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 7: // Release Bed
                    System.out.print("Enter Patient ID to release bed: ");
                    String relId = scanner.nextLine();
                    Patient relPatient = findPatientById(patients, relId);
                    if (relPatient != null) {
                        if (!relPatient.isHasBedAssigned()) {
                            System.out.println("Patient does not currently have a bed assigned.");
                        } else {
                            relPatient.setAssignedWard("Unassigned");
                            relPatient.setHasBedAssigned(false);
                            System.out.println("Bed released successfully.");
                        }
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 8: // Display Ward Layout
                    System.out.println("\n--- Ward Layout ---");
                    if (patients.isEmpty()) {
                        System.out.println("No records to display.");
                    } else {
                        for (Patient p : patients) {
                            System.out.println("Ward: " + p.getAssignedWard() + " -> Patient: " + p.getName() + " " + p.getSurname() + " [" + (p.isHasBedAssigned() ? "Occupied" : "Vacant") + "]");
                        }
                    }
                    break;

                case 9: // Display Available Beds
                    System.out.println("\n--- Available (Unassigned) Beds ---");
                    long availableCount = patients.stream().filter(p -> !p.isHasBedAssigned()).count();
                    System.out.println("Total patients without a bed / available slots context: " + availableCount);
                    for (Patient p : patients) {
                        if (!p.isHasBedAssigned()) {
                            System.out.println("- Patient record ID " + p.getPatientId() + " (" + p.getName() + " " + p.getSurname() + ") is awaiting bed assignment.");
                        }
                    }
                    break;

                case 10: // Display Occupied Beds
                    System.out.println("\n--- Occupied Beds ---");
                    long occupiedCount = patients.stream().filter(Patient::isHasBedAssigned).count();
                    System.out.println("Total beds currently occupied: " + occupiedCount);
                    for (Patient p : patients) {
                        if (p.isHasBedAssigned()) {
                            System.out.println("- Ward " + p.getAssignedWard() + ": " + p.getName() + " " + p.getSurname() + " (ID: " + p.getPatientId() + ")");
                        }
                    }
                    break;

                case 11: // Display Ward Report
                    System.out.println("\n--- Ward Report Summary ---");
                    System.out.println("Total Registered Patients: " + patients.size());
                    long occ = patients.stream().filter(Patient::isHasBedAssigned).count();
                    System.out.println("Total Occupied Beds: " + occ);
                    System.out.println("Total Unassigned: " + (patients.size() - occ));
                    break;

                case 12: // Sort Patients by Surname
                    Collections.sort(patients);
                    System.out.println("Patients sorted alphabetically by surname!");
                    break;

                case 13: // Sort Patients by Patient ID
                    patients.sort(Comparator.comparing(Patient::getPatientId));
                    System.out.println("Patients sorted by Patient ID!");
                    break;

                case 0: // Exit
                    System.out.println("Exiting MediCare Hospital System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice, please select between 0 and 13.");
            }
        } while (choice != 0);

        scanner.close();
    }

    private static Patient findPatientById(ArrayList<Patient> patients, String id) {
        for (Patient p : patients) {
            if (p.getPatientId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }
}