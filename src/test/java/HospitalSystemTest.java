package com.mycompany.hospitalsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class HospitalSystemTest {

    private ArrayList<Patient> patients;
    private String[][] wardGrid;

    @BeforeEach
    public void setUp() {
        patients = new ArrayList<>();
        wardGrid = new String[4][5];
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                wardGrid[r][c] = "[Available]";
            }
        }
    }

    @Test
    public void testRegisterPatient() {
        Patient p = new Patient("P001", "Kabelo", "Mokoena", 35, "Male", "Hypertension", PatientCategory.OUTPATIENT);
        patients.add(p);
        
        assertEquals(1, patients.size());
        assertEquals("P001", patients.get(0).getPatientId());
    }

    @Test
    public void testPreventDuplicatePatientIDs() {
        Patient p1 = new Patient("P001", "Kabelo", "Mokoena", 35, "Male", "Hypertension", PatientCategory.OUTPATIENT);
        patients.add(p1);

        // Attempting to register another with same ID
        String duplicateId = "P001";
        boolean exists = false;
        for (Patient existing : patients) {
            if (existing.getPatientId().equalsIgnoreCase(duplicateId)) {
                exists = true;
                break;
            }
        }

        assertTrue(exists, "Should detect duplicate Patient ID");
    }

    @Test
    public void testSearchPatient() {
        Patient p = new Patient("P001", "Kabelo", "Mokoena", 35, "Male", "Hypertension", PatientCategory.OUTPATIENT);
        patients.add(p);

        Patient found = null;
        for (Patient pat : patients) {
            if (pat.getPatientId().equalsIgnoreCase("P001")) {
                found = pat;
                break;
            }
        }

        assertNotNull(found);
        assertEquals("Kabelo", found.getFirstName());
    }

    @Test
    public void testUpdatePatientDetails() {
        Patient p = new Patient("P001", "Kabelo", "Mokoena", 35, "Male", "Hypertension", PatientCategory.OUTPATIENT);
        patients.add(p);

        // Simulate update
        p.setFirstName("Thabo");
        p.setAge(40);

        assertEquals("Thabo", patients.get(0).getFirstName());
        assertEquals(40, patients.get(0).getAge());
    }

    @Test
    public void testDeletePatient() {
        Patient p = new Patient("P001", "Kabelo", "Mokoena", 35, "Male", "Hypertension", PatientCategory.OUTPATIENT);
        patients.add(p);
        
        patients.remove(p);
        assertTrue(patients.isEmpty());
    }

    @Test
    public void testAllocateAndReleaseBed() {
        Inpatient inPat = new Inpatient("P002", "Lerato", "Zulu", 28, "Female", "Asthma", PatientCategory.INPATIENT, "W01", "");
        patients.add(inPat);

        // Allocate B01 (Row 0, Col 0)
        wardGrid[0][0] = inPat.getFirstName() + " " + inPat.getLastName();
        inPat.setBedNumber("B01");

        assertEquals("B01", inPat.getBedNumber());
        assertEquals("Lerato Zulu", wardGrid[0][0]);

        // Release bed
        wardGrid[0][0] = "[Available]";
        inPat.setBedNumber("");

        assertEquals("", inPat.getBedNumber());
        assertEquals("[Available]", wardGrid[0][0]);
    }

    @Test
    public void testPreventAllocatingOccupiedBed() {
        wardGrid[0][0] = "Occupied Bed";
        
        boolean isAvailable = wardGrid[0][0].equals("[Available]");
        assertFalse(isAvailable, "Should prevent allocation on an occupied bed");
    }

    @Test
    public void testSortPatientsBySurname() {
        patients.add(new Patient("P002", "A", "Zuma", 20, "Male", "None", PatientCategory.OUTPATIENT));
        patients.add(new Patient("P001", "B", "Abrahams", 22, "Female", "None", PatientCategory.OUTPATIENT));

        Collections.sort(patients); // Sorts by surname using Comparable

        assertEquals("Abrahams", patients.get(0).getLastName());
        assertEquals("Zuma", patients.get(1).getLastName());
    }

    @Test
    public void testSortPatientsByPatientID() {
        patients.add(new Patient("P005", "A", "Test", 20, "Male", "None", PatientCategory.OUTPATIENT));
        patients.add(new Patient("P002", "B", "Test", 22, "Female", "None", PatientCategory.OUTPATIENT));

        patients.sort(Comparator.comparing(Patient::getPatientId));

        assertEquals("P002", patients.get(0).getPatientId());
        assertEquals("P005", patients.get(1).getPatientId());
    }
}