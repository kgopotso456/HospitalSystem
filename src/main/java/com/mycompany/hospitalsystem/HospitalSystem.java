package com.mycompany.hospitalsystem;

import java.util.ArrayList;
import java.util.Scanner;

public class HospitalSystem {
    public static void main(String[] args) {
        ArrayList<Patient> patients = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- MediCare Hospital System Menu ---");
            System.out.println("1. Add Patient");
            System.out.println("2. Display All Patients");
            System.out.println("3. Search Patient by ID");
            System.out.println("4. Calculate Total Hospital Bill");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");
            
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Patient ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    
                    System.out.print("Enter Ward Number: ");
                    String ward = scanner.nextLine();
                    System.out.print("Enter Days Admitted: ");
                    int days = scanner.nextInt();
                    System.out.print("Enter Daily Rate: ");
                    double rate = scanner.nextDouble();
                    
                    patients.add(new Inpatient(id, name, age, PatientCategory.GENERAL, ward, days, rate));
                    System.out.println("Patient added successfully!");
                    break;

                case 2:
                    if (patients.isEmpty()) {
                        System.out.println("No patients recorded yet.");
                    } else {
                        for (Patient p : patients) {
                            p.displayPatientInfo();
                            System.out.println("--------------------");
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter ID to search: ");
                    String searchId = scanner.nextLine();
                    boolean found = false;
                    for (Patient p : patients) {
                        if (p.getPatientId().equalsIgnoreCase(searchId)) {
                            p.displayPatientInfo();
                            found = true;
                            break;
                        }
                    }
                    if (!found) System.out.println("Patient not found.");
                    break;

                case 4:
                    double totalRevenue = 0;
                    for (Patient p : patients) {
                        if (p instanceof Inpatient) {
                            totalRevenue += ((Inpatient) p).calculateTotalBill();
                        }
                    }
                    System.out.println("Total Hospital Revenue from Bills: R" + totalRevenue);
                    break;

                case 5:
                    System.out.println("Exiting MediCare Hospital System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice, please select between 1 and 5.");
            }
        } while (choice != 5);

        scanner.close();
    }
}
