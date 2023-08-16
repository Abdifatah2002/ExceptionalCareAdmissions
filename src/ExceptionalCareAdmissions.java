//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P04 Exceptional Care Admissions
// Course:   CS 300 Spring 2023
//
// Author:   Abdifatah Abdi
// Email:    aaabdi2@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    N/A
// Partner Email:   N/A
// Partner Lecturer's Name: N/A
/// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//
////   _X__ Write-up states that pair programming is allowed for this assignment.
//
////   _X__ We have both read and understand the course Pair Programming Policy.
//
////   _X__ We have registered our team prior to the team registration deadline.
//
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
//// Persons:         TA: TA Snehal Wadhwani  help with little help on ExceptionalCareAdmissions
// TA: Yiwei Zhang help with little help on patientRecord


//// Online Sources:  i used the https://cs300-www.cs.wisc.edu/wp/wp-content/uploads/2020/12/sp2023/p4/javadocs/PatientRecord.html
//
// i also used https://www.w3schools.com to refresh my meomry past content i forget how to do it

//
///////////////////////////////////////////////////////////////////////////////
import java.util.ArrayList;
import  java.io.File;
import java.io.PrintWriter;
import java .io.IOException;

public class ExceptionalCareAdmissions {
    //fields
    private PatientRecord[] patientsList; // An oversize array containing the PateientRecord currently acive in this object
    private int size = 0; // The number of values in the oversize array

    // constructor

    /**

     * Constructs a new instance of ExceptionalCareAdmissions with the specified capacity.
     * @param capacity the maximum number of patients that can be admitted to the ExceptionalCareAdmissions.
     * @throws IllegalArgumentException if the capacity is less than or equal to zero.
     * */

    public ExceptionalCareAdmissions(int capacity) throws IllegalArgumentException {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        patientsList = new PatientRecord[capacity];
        size = 0;
    }


    //methods

    /**

     Checks if the admission list is full.
     @return true if the admission list is full, false otherwise.
     */
    public boolean isFull() {
        if ((size == patientsList.length)) return true;
        else return false;
    }
    /**

     Returns the number of patient records in the admissions list.
     Also prints the number of patient records in the admissions list to the console.
     @return the number of patient records in the admissions list
     */

    public int size() {
        int numPatients = size;
        System.out.println("There are currently " + numPatients + " patient records in the admissions list.");
        return numPatients;
    }
    /**

     Returns the number of patients in the patient list who have been seen by a doctor.
     @return the number of patients who have been seen
      */

    public int getNumberSeenPatients() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (patientsList[i].hasBeenSeen()) {
                count++;
            }

        }
        return count;
    }
    /**

     Finds the appropriate index to admit a patient with the given triage level, based on the current patients in the list.
     @param rec the PatientRecord object representing the patient to be admitted
     @return the index at which the patient should be admitted in the list
     @throws IllegalStateException if the patientsList is already full and there are no empty slots, or if all patients have already been seen
     */

    public int getAdmissionIndex(PatientRecord rec) throws IllegalStateException {
        int index = 0;
        int triage = rec.getTriage();

        // Check if patientsList is full
        if (size == patientsList.length) {
            if (hasSeenPatients()) {
                throw new IllegalStateException("cleanPatientsList()");
            }
            throw new IllegalStateException("Cannot admit new patients");
        }

        // Count number of patients of each triage level
        int numRed = 0;
        int numYellow = 0;
        int numGreen = 0;
        for (int i = 0; i < size; i++) {
            if (patientsList[i] == null) {
                continue;
            }
            int patientTriage = patientsList[i].getTriage();
            if (patientTriage == 0) {
                numRed++;
            } else if (patientTriage == 1) {
                numYellow++;
            } else if (patientTriage == 2) {
                numGreen++;
            }
        }

        // Determine index based on triage level
        if (triage == 0) { // Red patient
            if (numRed > 0) {
                index = findNextIndex(0, size, patientsList, numRed);
            }
        } else if (triage == 1) { // Yellow patient
            if (numYellow > 0) {
                index = findNextIndex(numRed, size, patientsList, numYellow);
            } else if (numRed > 0) {
                index = findNextIndex(0, size, patientsList, numRed);
            }
        } else if (triage == 2) { // Green patient
            if (numGreen > 0) {
                index = findNextIndex(numRed + numYellow, size, patientsList, numGreen);
            } else if (numYellow > 0) {
                index = findNextIndex(numRed, size, patientsList, numYellow);
            } else if (numRed > 0) {
                index = findNextIndex(0, size, patientsList, numRed);
            }
        }

        if (index == -1) {
            throw new IllegalStateException("Cannot admit new patients");
        }

        return index;
    }
    /**

     Checks if any patient record in the list has been marked as "seen".
     @return true if at least one patient has been seen, false otherwise.
     */

    private boolean hasSeenPatients() {
        for (int i = 0; i < size; i++) {
            if (patientsList[i] == null) {
                continue;
            }
            if (patientsList[i].hasBeenSeen()) {
                return true;
            }
        }
        return false;
    }


    // Helper method to find the next available index in patientsList
    /**

     Finds the next available index in the specified range of the patientsList array
     where a patient can be added.
     @param start the starting index of the range to search (inclusive)
     @param end the ending index of the range to search (exclusive)
     @param patientsList the array of PatientRecord objects to search for an available index
     @param count the number of consecutive null indices to look for
     @return the index of the first consecutive null element found in the specified range,
     */
    private static int findNextIndex(int start, int end, PatientRecord[] patientsList, int count) {
        int index = -1;
        for (int i = start; i < end; i++) {
            if (patientsList[i] == null) {
                count--;
                if (count == 0) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
    /**

     Adds a new patient record to the patient list at the specified index.
     @param rec the patient record to add
     @param index the index at which to add the patient record
     @throws IllegalStateException if the patient list is full and contains seen patients, or if new patients cannot be admitted for any other reason
     @throws IllegalArgumentException if the specified index is out of bounds
     */
    public void addPatient(PatientRecord rec, int index) throws IllegalStateException, IllegalArgumentException {
        if (size == patientsList.length) {
            // The patientsList is full
            if (hasSeenPatients()) {
                throw new IllegalStateException("cleanPatientsList()");
            } else {
                throw new IllegalStateException("Cannot admit new patients");
            }
        } else if (index < 0 || index > size) {
            // The index is invalid
            throw new IllegalArgumentException("Invalid index: " + index);
        } else {
            // Move the existing patients to make room for the new patient
            for (int i = size - 1; i >= index; i--) {
                patientsList[i + 1] = patientsList[i];
            }
            // Add the new patient to the specified index
            patientsList[index] = rec;
            // Update the size field
            size++;

        }
    }
    /**

     Marks a patient as seen based on their case ID.
     @param caseID the unique case ID of the patient to mark as seen
     @throws IllegalArgumentException if the provided case ID is less than 1 or if no patient record with the given case ID exists
     @throws IllegalStateException if the patient list is currently empty
     */

    public void seePatient(int caseID) throws IllegalStateException, IllegalArgumentException {
        if (caseID < 1) {
            throw new IllegalArgumentException("Invalid case ID: " + caseID);
        }
        if (size == 0) {
            throw new IllegalStateException("The patient list is currently empty.");
        }

        for (PatientRecord patient : patientsList) {
            if (patient.CASE_NUMBER == caseID) {
                patient.seePatient();
                return;
            }
        }

        throw new IllegalArgumentException("No patient record with case ID " + caseID + " was found.");
    }



    /**

     Returns a summary of the patient records in the PatientsList object.
     The summary includes the total number of patient records, the number of patients
     in each triage category (red, yellow, green), and the number of patients that have been seen.
     @return a String containing the summary of the patient records
      */
    public String getSummary() {
        int numRed = 0;
        int numYellow = 0;
        int numGreen = 0;
        int numSeen = 0;

        for (int i = 0; i < size; i++) {
            if (patientsList[i].hasBeenSeen()) {
                numSeen++;
            }

            switch (patientsList[i].getTriage()) {
                case 0:
                    numRed++;
                    break;
                case 1:
                    numYellow++;
                    break;
                case 2:
                    numGreen++;
                    break;
            }
        }

        String summary = "Summary of Patient Records:\n";
        summary += "Total number of patient records: " + size + "\n";
        summary += "Number of red patients: " + numRed + "\n";
        summary += "Number of yellow patients: " + numYellow + "\n";
        summary += "Number of green patients: " + numGreen + "\n";
        summary += "Number of seen patients: " + numSeen;

        return summary;
    }

    /**

     This method cleans the patientsList by recording any seen patients to a given file and removing them from the list.
     If no seen patients are found, it records a summary to the file. The method also updates the size of the patientsList
     to match the number of unseen patients after removal of seen patients.
     @param file a File object representing the file to write the seen patients to
     */


    public void cleanPatientsList(File file) {
        try {
            PrintWriter writer = new PrintWriter(file);

            // Step 1: Record any SEEN patients to the file
            int seenCount = 0;
            for (PatientRecord patient : patientsList) {
                if (patient.hasBeenSeen()) {
                    writer.println(patient.toString());
                    seenCount++;
                }
            }

            // Step 2: If no SEEN patients are found, record the patientsList summary
            if (seenCount == 0) {
                writer.println("Total number seen: 0");
            }

            // Step 3: Close the PrintWriter object
            writer.close();

            // Step 4: Create a new ArrayList to store any UNSEEN patients
            ArrayList<PatientRecord> unseenPatients = new ArrayList<PatientRecord>();

            // Step 5: Iterate through the patientsList again and add any UNSEEN patients to the new ArrayList
            for (PatientRecord patient : patientsList) {
                if (!patient.hasBeenSeen()) {
                    unseenPatients.add(patient);
                }
            }

            // Step 6: Set the patientsList to the new ArrayList
            patientsList = unseenPatients.toArray(new PatientRecord[0]);

            // Step 7: Update the size of the patientsList to match the number of UNSEEN patients
            Object numPatients = patientsList.toString();
        } catch (IOException e) {
            // Step 9: Catch any IOExceptions that may occur while writing to the file and do not modify the patientsList in that case

            return;
        }
    }


    /**
     * For testing purposes: this method creates and returns a string representation of the
     * patientsList, as the in-order string representation of each patient in the list on a
     * separate line. If patientsList is empty, returns an empty string.
     *
     * @return a string representation of the contents of patientsList
     */
    public String toString() {
        String returnValue = "";
        for (PatientRecord r : patientsList) {
            returnValue += (r != null) ? r.toString() + "\n" : "";
        }
        return returnValue.trim();
    }
}





