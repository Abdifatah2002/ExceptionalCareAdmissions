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
import  java.io.File;
import java.io.PrintWriter;
import java .io.IOException;
public class PatientRecord {

    //fields
    public static final int RED = 0;//one of the triage levels
    private static int patientCounter = 0 ; // counts the number of patients created. Begins at 1, ad advances to the next value after each caseID is generated
    public static final int YELLOW = 1; // one of the triage levels
    public static final int GREEN = 2; // one of the triage levels
    private int age; // this patients's age
    private char gender; //This patient's single-charcter gender marker
    public final int CASE_NUMBER; // the generated case number associctaed with this patient record.
    private boolean hasBeenSeen; // where this patient has been marked as "see"
    private int orderOfArrival ; //The order in which this patient arrived; taken fro the value of patientCounter when this record was created
    private int triage; // This patient's triage level


    /**
     * Creates a new patient record and assigns it a CASE_NUMBER using the provided information.
     * The orderOfArrival is recorded before generating the case number, as the counter will
     * advance when the static helper method generateCaseNumber() is called.
     *
     * @param gender a single-character representation of this patient's reported gender
     * @param age the age of this patient in years
     * @param triage the triage level of this patient (must be one of RED, YELLOW, or GREEN)
     * @throws IllegalArgumentException if the provided triage value is not one of the class constants
     */
    public PatientRecord(char gender, int age, int triage) throws IllegalStateException {

        // Check that the provided triage value is valid
        if (triage != GREEN && triage != YELLOW && triage != RED) {
            throw new IllegalArgumentException("Invalid triage value: " + triage);
        }
        // Record the order of arrival
        orderOfArrival = patientCounter;
        // Increment the patientCounter before generating the case number
        patientCounter++;
        // Generate the case number using the provided gender and age
        this.CASE_NUMBER = generateCaseNumber(gender, age);
        // Store the other provided information in instance variables
        this.gender = gender;
        this.age = age;
        this.triage = triage;
        // Mark the patient as not having been seen yet
        this.hasBeenSeen = false;
    }

    /**

     Generates a unique case number for a patient based on their gender and age.
     @param gender the gender of the patient, as a character ('F' for female, 'M' for male, 'X' for non-binary, or any other character for unknown)
     @param age the age of the patient, in years
     @return an integer representing the patient's case number, calculated by combining a gender digit, an age digit, and a patient counter
     */

    public static int generateCaseNumber(char gender, int age) {
        int genderDigit;
        switch (gender) {
            case 'F':
                genderDigit = 1;
                break;
            case 'M':
                genderDigit = 2;
                break;
            case 'X':
                genderDigit = 3;
                break;
            default:
                genderDigit = 4;
        }

        int ageDigits = age % 100;

        int caseNumber = genderDigit * 10000 + ageDigits * 100 + patientCounter;

        patientCounter++;
        if (patientCounter > 99) {
            patientCounter = 1;
        }

        return caseNumber;
    }


    /**
     * For tester class purposes only: resents PatientRecord.patientCounter to 1.
     * This method should be called at the beginning of EACH tester method to ensure
     * that the methods are not dependent on being called in a particular order.
     */
    public static void resetCounter() {
        PatientRecord.patientCounter = 1;
    }
    /**
     * Returns the triage level of the patient.
     *
     * @return an integer representing the patient's triage level, as one of the class constants GREEN, YELLOW, or RED
     */

    public int getTriage() {

        return this.triage;
    }
    /**
     * Returns the reported gender of the patient.
     *
     * @return a single-character representation of the patient's reported gender ('F' for female, 'M' for male, 'X' for non-binary, or any other character for unknown)
     */

    public char getGender() {

        return this.gender;

    }
    /**
     * Returns the age of the patient in years.
     *
     * @return an integer representing the age of the patient
     */

    public int getAge() {
        return this.age;
    }

    /**
     * Returns the order of arrival for the patient, relative to other patients seen by this Emergency Room.
     *
     * @return an integer representing the order in which the patient arrived
     */

    public int getArrivalOrder() {
        return this.orderOfArrival;
    }

    /**
     * Returns whether or not the patient has been seen by a medical professional.
     *
     * @return true if the patient has been seen, false otherwise.
     */

    public boolean hasBeenSeen() {
        return this.hasBeenSeen;

    }
    /**

     Returns a formatted string representation of the patient, including their case number, age, gender, and triage level.
     @return a string in the format "CASE_NUMBER: ageGENDER (triage)", where CASE_NUMBER is a 5-digit number,
     age is an integer, GENDER is 'F', 'M', or 'X' (representing female, male, or unknown/other), and triage is one
     of "GREEN", "YELLOW", or "RED".
     */


    public String toString() {
        String genderStr = "";
        if (this.gender == 'F') {
            genderStr = "F";
        } else if (this.gender == 'M') {
            genderStr = "M";
        } else {
            genderStr = "X";
        }
        String triageStr = "";
        if (this.triage == GREEN) {
            triageStr = "GREEN";
        } else if (this.triage == YELLOW) {
            triageStr = "YELLOW";
        } else {
            triageStr = "RED";
        }
        return String.format("%05d: %d%s (%s)", this.CASE_NUMBER, this.age, genderStr, triageStr);
    }

    /**

     Marks this patient as having been seen by a medical professional.
     Once this method is called, the patient is recorded as having been seen and there is no way to undo this action.
     */
    public void seePatient() {
        this.hasBeenSeen = true;
    }
}






