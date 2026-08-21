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

        // 2D Array representing a 4-row by 5-column ward bed system (Total 20 beds)
        // Storing the Patient Name or ID if occupied, or "[Available]" if vacant
        String[][] wardGrid = new String[4][5];
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                wardGrid[r][c] = "[Available]";
            }
        }

        do {
            System.out.println("==============================================");
            System.out.println("           MEDICARE HOSPITAL SYSTEM           ");
            System.out.println("==============================================");
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
            System.out.println("12. Sort Patients by Last Name");
            System.out.println("13. Sort Patients by Patient ID");
            System.out.println("0. Exit");
            System.out.println("==============================================");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                System.out.print("Enter your choice: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println("\nREGISTER PATIENT");
                    System.out.println("----------------------------------------------");
                    System.out.print("Enter Patient ID: ");
                    String id = scanner.nextLine().trim();

                    if (findPatientById(patients, id) != null) {
                        System.out.println("REGISTRATION ERROR: Patient ID already exists.");
                        break;
                    }

                    System.out.print("Enter First Name: ");
                    String fName = scanner.nextLine();
                    System.out.print("Enter Last Name: ");
                    String lName = scanner.nextLine();
                    System.out.print("Enter Age: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid age. Enter a number:");
                        scanner.next();
                    }
                    int age = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Gender: ");
                    String gender = scanner.nextLine();
                    System.out.print("Enter Medical Condition: ");
                    String condition = scanner.nextLine();

                    System.out.println("\nSelect Patient Category");
                    System.out.println("1. Inpatient");
                    System.out.println("2. Outpatient");
                    System.out.println("3. Emergency");
                    System.out.print("Enter category: ");
                    
                    String catInput = scanner.nextLine().trim();
                    PatientCategory category;
                    String wardNum = "";

                    if (catInput.equals("1")) {
                        category = PatientCategory.INPATIENT;
                        System.out.print("Enter Ward Number: ");
                        wardNum = scanner.nextLine();
                        patients.add(new Inpatient(id, fName, lName, age, gender, condition, category, wardNum, ""));
                    } else if (catInput.equals("2")) {
                        category = PatientCategory.OUTPATIENT;
                        patients.add(new Patient(id, fName, lName, age, gender, condition, category));
                    } else if (catInput.equals("3")) {
                        category = PatientCategory.EMERGENCY;
                        patients.add(new Patient(id, fName, lName, age, gender, condition, category));
                    } else {
                        System.out.println("REGISTRATION ERROR: Category must be 1, 2 or 3.");
                        break;
                    }
                    System.out.println("\nPatient registered successfully.");
                    break;

                case 2:
                    System.out.println("\nSEARCH PATIENT");
                    System.out.println("----------------------------------------------");
                    System.out.print("Enter Patient ID: ");
                    String searchId = scanner.nextLine();
                    Patient pFound = findPatientById(patients, searchId);
                    if (pFound != null) {
                        System.out.println();
                        pFound.displayDetails();
                        if (pFound instanceof Inpatient) {
                            String assignedBed = ((Inpatient) pFound).getBedNumber();
                            System.out.println("Bed Number: " + (assignedBed.isEmpty() ? "Not allocated" : assignedBed));
                        }
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 3:
                    System.out.println("\nUPDATE PATIENT");
                    System.out.println("----------------------------------------------");
                    System.out.print("Enter Patient ID to update: ");
                    String updateId = scanner.nextLine();
                    Patient pUpdate = findPatientById(patients, updateId);
                    
                    if (pUpdate == null) {
                        System.out.println("Patient not found.");
                        break;
                    }

                    int updateChoice;
                    do {
                        System.out.println("\nWHAT WOULD YOU LIKE TO CHANGE?");
                        System.out.println("----------------------------------------------");
                        System.out.println("1. First Name");
                        System.out.println("2. Last Name");
                        System.out.println("3. Age");
                        System.out.println("4. Gender");
                        System.out.println("5. Medical Condition");
                        if (pUpdate instanceof Inpatient) {
                            System.out.println("6. Ward Number");
                        }
                        System.out.println("0. Finish Update");
                        System.out.println("----------------------------------------------");
                        System.out.print("Enter your choice: ");
                        
                        while(!scanner.hasNextInt()) {
                            scanner.next();
                            System.out.print("Invalid input. Enter choice: ");
                        }
                        updateChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (updateChoice) {
                            case 1:
                                System.out.print("Enter new First Name: ");
                                pUpdate.setFirstName(scanner.nextLine());
                                System.out.println("Updated successfully.");
                                break;
                            case 2:
                                System.out.print("Enter new Last Name: ");
                                pUpdate.setLastName(scanner.nextLine());
                                System.out.println("Updated successfully.");
                                break;
                            case 3:
                                System.out.print("Enter new Age: ");
                                pUpdate.setAge(scanner.nextInt());
                                scanner.nextLine();
                                System.out.println("Updated successfully.");
                                break;
                            case 4:
                                System.out.print("Enter new Gender: ");
                                pUpdate.setGender(scanner.nextLine());
                                System.out.println("Updated successfully.");
                                break;
                            case 5:
                                System.out.print("Enter new Medical Condition: ");
                                pUpdate.setMedicalCondition(scanner.nextLine());
                                System.out.println("Updated successfully.");
                                break;
                            case 6:
                                if (pUpdate instanceof Inpatient) {
                                    System.out.print("Enter new Ward Number: ");
                                    ((Inpatient) pUpdate).setWardNumber(scanner.nextLine());
                                    System.out.println("Updated successfully.");
                                }
                                break;
                            case 0:
                                System.out.println("Exiting update menu.");
                                break;
                            default:
                                System.out.println("Invalid option.");
                        }
                    } while (updateChoice != 0);
                    break;

                case 4:
                    System.out.println("\nDELETE PATIENT");
                    System.out.println("----------------------------------------------");
                    System.out.print("Enter Patient ID to delete: ");
                    String delId = scanner.nextLine();
                    Patient target = findPatientById(patients, delId);
                    if (target != null) {
                        if (target instanceof Inpatient) {
                            String bed = ((Inpatient) target).getBedNumber();
                            int[] coords = getBedCoordinates(bed);
                            if (coords != null) {
                                wardGrid[coords[0]][coords[1]] = "[Available]";
                            }
                        }
                        patients.remove(target);
                        System.out.println("Patient deleted successfully.");
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 5:
                    System.out.println("\nDISPLAY ALL PATIENTS");
                    System.out.println("----------------------------------------------");
                    if (patients.isEmpty()) {
                        System.out.println("No records found.");
                    } else {
                        for (Patient p : patients) {
                            p.displayDetails();
                            if (p instanceof Inpatient) {
                                String b = ((Inpatient) p).getBedNumber();
                                System.out.println("Bed Number: " + (b.isEmpty() ? "Not allocated" : b));
                            }
                            System.out.println("----------------------------------------------");
                        }
                    }
                    break;

                case 6:
                    System.out.println("\nALLOCATE BED");
                    System.out.println("----------------------------------------------");
                    System.out.print("Enter Patient ID: ");
                    String allocId = scanner.nextLine();
                    Patient pat = findPatientById(patients, allocId);

                    if (pat == null) {
                        System.out.println("Patient not found.");
                    } else if (!(pat instanceof Inpatient)) {
                        System.out.println("Error: Only Inpatients can be allocated a bed.");
                    } else {
                        Inpatient inPat = (Inpatient) pat;
                        if (!inPat.getBedNumber().isEmpty()) {
                            System.out.println("Patient already has bed " + inPat.getBedNumber() + ".");
                            break;
                        }

                        System.out.print("Enter Bed Number (e.g., B01 to B20): ");
                        String bedCode = scanner.nextLine().toUpperCase().trim();
                        int[] coords = getBedCoordinates(bedCode);

                        if (coords == null) {
                            System.out.println("Invalid bed code format. Use B01 through B20.");
                        } else if (!wardGrid[coords[0]][coords[1]].equals("[Available]")) {
                            System.out.println("Error: Bed " + bedCode + " is already occupied.");
                        } else {
                            wardGrid[coords[0]][coords[1]] = inPat.getFirstName() + " " + inPat.getLastName();
                            inPat.setBedNumber(bedCode);
                            System.out.println("Bed " + bedCode + " successfully allocated to " + inPat.getFirstName() + ".");
                        }
                    }
                    break;

                case 7:
                    System.out.println("\nRELEASE BED");
                    System.out.println("----------------------------------------------");
                    System.out.print("Enter Bed Number: ");
                    String relBed = scanner.nextLine().toUpperCase().trim();
                    int[] relCoords = getBedCoordinates(relBed);

                    if (relCoords == null || wardGrid[relCoords[0]][relCoords[1]].equals("[Available]")) {
                        System.out.println("Bed could not be released. The bed may be invalid or already available.");
                    } else {
                        // Clear bed from Inpatient record
                        for (Patient p : patients) {
                            if (p instanceof Inpatient) {
                                Inpatient ip = (Inpatient) p;
                                if (ip.getBedNumber().equalsIgnoreCase(relBed)) {
                                    ip.setBedNumber("");
                                }
                            }
                        }
                        wardGrid[relCoords[0]][relCoords[1]] = "[Available]";
                        System.out.println("Bed " + relBed + " released successfully.");
                    }
                    break;

                case 8:
                    System.out.println("\nBED LAYOUT");
                    System.out.println("----------------------------------------------");
                    for (int r = 0; r < 4; r++) {
                        for (int c = 0; c < 5; c++) {
                            int bedNum = (r * 5) + c + 1;
                            String label = (bedNum < 10) ? "B0" + bedNum : "B" + bedNum;
                            String status = wardGrid[r][c].equals("[Available]") ? "[Available]" : "[" + wardGrid[r][c] + "]";
                            System.out.print(status + "\t");
                        }
                        System.out.println();
                    }
                    break;

                case 9:
                    System.out.println("\nAVAILABLE BEDS");
                    System.out.println("----------------------------------------------");
                    int availCount = 0;
                    for (int r = 0; r < 4; r++) {
                        for (int c = 0; c < 5; c++) {
                            if (wardGrid[r][c].equals("[Available]")) {
                                int bedNum = (r * 5) + c + 1;
                                String label = (bedNum < 10) ? "B0" + bedNum : "B" + bedNum;
                                System.out.print(label + " ");
                                availCount++;
                            }
                        }
                    }
                    System.out.println("\nTotal Available: " + availCount);
                    break;

                case 10:
                    System.out.println("\nOCCUPIED BEDS");
                    System.out.println("----------------------------------------------");
                    int occCount = 0;
                    for (int r = 0; r < 4; r++) {
                        for (int c = 0; c < 5; c++) {
                            if (!wardGrid[r][c].equals("[Available]")) {
                                int bedNum = (r * 5) + c + 1;
                                String label = (bedNum < 10) ? "B0" + bedNum : "B" + bedNum;
                                System.out.println(label + " -> Occupied by: " + wardGrid[r][c]);
                                occCount++;
                            }
                        }
                    }
                    System.out.println("Total Occupied: " + occCount);
                    break;

                case 11:
                    System.out.println("\nWARD REPORT");
                    System.out.println("----------------------------------------------");
                    int totalOccupied = 0;
                    for (int r = 0; r < 4; r++) {
                        for (int c = 0; c < 5; c++) {
                            if (!wardGrid[r][c].equals("[Available]")) totalOccupied++;
                        }
                    }
                    System.out.println("Total Registered Patients: " + patients.size());
                    System.out.println("Total Occupied Beds: " + totalOccupied + " / 20");
                    System.out.println("Total Available Beds: " + (20 - totalOccupied));
                    System.out.printf("Occupancy Rate: %.1f%%\n", (totalOccupied / 20.0) * 100);
                    break;

                case 12:
                    Collections.sort(patients);
                    System.out.println("\nPatients sorted alphabetically by Last Name.");
                    break;

                case 13:
                    patients.sort(Comparator.comparing(Patient::getPatientId));
                    System.out.println("\nPatients sorted by Patient ID.");
                    break;

                case 0:
                    System.out.println("Exiting system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please choose between 0 and 13.");
            }
            System.out.println();
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

    // Maps strings like "B01" -> row 0, col 0 up to "B20" -> row 3, col 4
    private static int[] getBedCoordinates(String bedCode) {
        try {
            if (bedCode.startsWith("B") && bedCode.length() == 3) {
                int num = Integer.parseInt(bedCode.substring(1));
                if (num >= 1 && num <= 20) {
                    int index = num - 1;
                    int row = index / 5;
                    int col = index % 5;
                    return new int[]{row, col};
                }
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return null;
    }
}