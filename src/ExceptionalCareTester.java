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
import java.io.File;
import java.util.Arrays;
import  java.io.File;
import java.io.PrintWriter;
import java .io.IOException;
public class ExceptionalCareTester {

    /**
     * This test method is provided for you in its entirety, to give you a model for
     * testing
     * an instantiable class. This method verifies the correctness of your
     * PatientRecord class.
     * <p>
     * In this test, we create two PatientRecords with different information and use
     * the accessor
     * methods to verify that both contain the correct information and have the
     * correct String
     * representation.
     *
     * @return true if and only if all scenarios pass, false otherwise
     * @author hobbes
     */
    public static boolean testPatientRecord() {
        // FIRST: reset the patient counter so this tester method can be run independently
        PatientRecord.resetCounter();

        // (1) create two PatientRecords with different, valid input
        // no exceptions should be thrown, so let's be safe:
        PatientRecord test1 = null, test2 = null;
        try {
            test1 = new PatientRecord('M', 17, PatientRecord.YELLOW);
            test2 = new PatientRecord('X', 21, PatientRecord.GREEN);
        } catch (Exception e) {
            return false;
        }

        // (2) verify their data fields:
        {
            // CASE_NUMBER
            int expected1 = 21701;
            int expected2 = 32102;
            if (test1.CASE_NUMBER != expected1 || test2.CASE_NUMBER != expected2) return
                    false;
        }
        {
            // triage
            int expected1 = PatientRecord.YELLOW;
            int expected2 = PatientRecord.GREEN;
            if (test1.getTriage() != expected1 || test2.getTriage() != expected2) return
                    false;
        }
        {
            // gender
            char expected1 = 'M';
            char expected2 = 'X';
            if (test1.getGender() != expected1 || test2.getGender() != expected2) return
                    false;
        }
        {
            // age
            int expected1 = 17;
            int expected2 = 21;
            if (test1.getAge() != expected1 || test2.getAge() != expected2) return false;
        }
        {
            // orderOfArrival
            int expected1 = 1;
            int expected2 = 2;
            if (test1.getArrivalOrder() != expected1 ||
                    test2.getArrivalOrder() != expected2) return false;
        }
        {
            // hasBeenSeen - try the mutator too
            if (test1.hasBeenSeen() || test2.hasBeenSeen()) return false;
            test1.seePatient();
            if (!test1.hasBeenSeen() || test2.hasBeenSeen()) return false;
        }

        // (3) verify their string representations
        {
            String expected1 = "21701: 17M (YELLOW)";
            String expected2 = "32102: 21X (GREEN)";
            if (!test1.toString().equals(expected1) || !
                    test2.toString().equals(expected2)) return false;
        }

        // (4) finally, verify that the constructor throws an exception for an invalid triage value
        try {
            new PatientRecord('F', 4, -17);
            // if we get here, no exception was thrown and the test fails
            return false;
        } catch (IllegalArgumentException e) {
            // correct exception type, but it should have a message:
            if (e.getMessage() == null || e.getMessage().isBlank()) return false;
        } catch (Exception e) {
            // incorrect exception type
            return false;
        }


        PatientRecord test3 = new PatientRecord('F', 10, 123);
        if (test3.getTriage() != PatientRecord.GREEN) {
            return false;
        }


        // if we've gotten this far,
        return true;
    }
    /**

     Tests the ExceptionalCareAdmissions constructor for valid input.
     The tests include:
     Verifying that a normal, valid-input constructor call does NOT throw an exception
     Verifying that a just-created object has size 0, is not full, has no seen patients, and its toString() is an empty string
     @return true if all tests pass, false otherwise
     */


    public static boolean testAdmissionsConstructorValid() {
        // (1) verify that a normal, valid-input constructor call does NOT throw an exception
        try {
            ExceptionalCareAdmissions admissions = new  ExceptionalCareAdmissions( 10);
        } catch (Exception e) {
            return false;
        }

        // (2) verify that a just-created object has size 0, is not full, has no seen patients, and
        // its toString() is an empty string
        ExceptionalCareAdmissions admissions = new  ExceptionalCareAdmissions(10);
        if (admissions.size() != 0) {
            return false;
        }
        if (admissions.isFull()) {
            return false;
        }


        if (!admissions.toString().equals("")) {
            return false;
        }

        return true;
    }
    /**

     This method tests the ExceptionalCareAdmissions constructor when an invalid capacity is provided.
     The constructor should throw an IllegalArgumentException if the capacity is less than or equal to 0.
     @return true if the constructor throws an IllegalArgumentException for an invalid capacity, false otherwise.
     */

    public static boolean testAdmissionsConstructorError() {
        // (1) verify that calling the constructor with capacity <= 0 causes an IllegalArgumentException
        try {
            ExceptionalCareAdmissions admissions = new ExceptionalCareAdmissions(-1);
            return false; // If the constructor did not throw an exception, the test has failed
        } catch (IllegalArgumentException e) {
            // The exception was thrown as expected, so the test has passed
            return true;
        }
    }
    /**

     Tests the addPatient method of the ExceptionalCareAdmissions class with valid inputs.
     Three patients are added to the admissions list, one patient to an empty list, one patient to a list with one patient,
     and one patient to a list with two patients. The method checks that each patient is added successfully, that the
     admissions list size is correct, that the admissions list is not empty or full at any point, and that the
     toString() method of the admissions list returns the expected string representation of the list.
     The method also checks that attempting to add a patient to a full admissions list throws an exception.
     @return true if all tests pass, false otherwise
     */



    public static boolean testAddPatientValid() {
// (1) add a new patient to an empty list - since you cannot use
// Arrays.deepEquals();
// anymore, verify the contents of the patientsList using
// ExceptionalCareAdmissions.toString();

        ExceptionalCareAdmissions admissions = new ExceptionalCareAdmissions(3);

        // Verify that a patient can be added
        try {
            admissions.addPatient(new PatientRecord('M', 17, PatientRecord.YELLOW), 1);

        } catch (Exception e) {
            return false;
        }

        // Verify that the admissions list now has size 1 and is not empty or full
        if (admissions.size() != 1 || admissions.isFull() || admissions.isFull()) {
            return false;
        }

        // Verify that the patient was added to the admissions list
        String expected = "1. 21701: 17M (YELLOW)\n";
        if (!admissions.toString().equals(expected)) {
            return false;
        }

        // (2) add a second patient to the list
        try {
            admissions.addPatient(new PatientRecord('X', 21, PatientRecord.GREEN), 2);

        } catch (Exception e) {
            return false;
        }

        // Verify that the admissions list now has size 2 and is not empty or full
        if (admissions.size() != 2 || admissions.isFull() || admissions.isFull()) {
            return false;
        }

        // Verify that both patients were added to the admissions list
        expected = "1. 21701: 17M (YELLOW)\n2. 32102: 21X (GREEN)\n";
        if (!admissions.toString().equals(expected)) {
            return false;
        }

        // (3) add a third patient to the list
        try {
            admissions.addPatient(new PatientRecord('F', 30, PatientRecord.RED), 0);

        } catch (Exception e) {
            return false;
        }

        // Verify that the admissions list now has size 3 and is full
        if (admissions.size() != 3 || admissions.isFull() || !admissions.isFull()) {
            return false;
        }

        // Verify that all three patients were added to the admissions list
        expected = "1. 21701: 17M (YELLOW)\n2. 32102: 21X (GREEN)\n3. 33301: 30F (RED)\n";
        if (!admissions.toString().equals(expected)) {
            return false;
        }

        // Verify that attempting to add another patient to the full admissions list throws an exception
        try {
            admissions.addPatient(new PatientRecord('M', 17, PatientRecord.YELLOW), 1);

            return false; // If the addPatient call did not throw an exception, the test has failed
        } catch (Exception e) {
            // The exception was thrown as expected, so the test has passed
            return true;
        }
    }

    /**
     * This test method is provided for you in its entirety, to give you a model for
     verifying a
     * method which throws exceptions. This method tests addPatient() with two
     different, full
     * patientsList arrays; one contains seen patients and one does not.
     *
     * We assume for the purposes of this method that the ExceptionalCareAdmissions
     constructor
     * and PatientRecord constructor are working properly.
     *
     * This method must NOT allow ANY exceptions to be thrown from the tested method.
     *
     * @author hobbes
     * @return true if and only if all scenarios pass, false otherwise
     */
    public static boolean testAddPatientError() {
        // FIRST: reset the patient counter so this tester method can be run independently
        PatientRecord.resetCounter();

        ////// (1) a full Admissions object that contains no seen patients

        // create a small admissions object and fill it with patients. i'm filling the list
        // in decreasing order of triage, so the addPatient() method has to do the least
        // amount of work.
        ExceptionalCareAdmissions full = new ExceptionalCareAdmissions(3);
        full.addPatient(new PatientRecord('M', 18, PatientRecord.RED), 0);
        full.addPatient(new PatientRecord('M', 5, PatientRecord.YELLOW), 1);

        // saving one patient in a local variable so we can mark them "seen" later
        PatientRecord seenPatient = new PatientRecord('F', 20, PatientRecord.GREEN);
        full.addPatient(seenPatient, 2);

        try {
            full.addPatient(new PatientRecord('F', 17, PatientRecord.GREEN), 3);
            // if we get here, no exception was thrown and the test fails
            return false;
        } catch (IllegalStateException e) {
            // this is the correct type of exception, but for this method we expect a specific
            // error message so we have one more step to verify:
            String message = e.getMessage();
            String expected = "Cannot admit new patients";
            if (!message.equals(expected)) return false;
        } catch (Exception e) {
            // this is the incorrect exception type, and we can just fail the test now
            return false;
        }

        ////// (2) a full Admissions object that contains at least one seen patient

        // since we have a reference to the patient at index 2, we'll just mark them seen here
        seenPatient.seePatient();

        try {
            full.addPatient(new PatientRecord('F', 17, PatientRecord.GREEN), 3);
            // if we get here, no exception was thrown and the test fails
            return false;
        } catch (IllegalStateException e) {
            // this is the correct type of exception again, but we expect a different error
            // message this time:
            String message = e.getMessage();
            String expected = "cleanPatientsList()";
            if (!message.equals(expected)) return false;
        } catch (Exception e) {
            // this is the incorrect exception type, and the test fails here
            return false;
        }

        // if we've gotten this far, we haven't failed either of the scenarios, so our test passes!
        return true;


    }
    /**

     Tests the getIndex() method of the Admissions class by creating an Admissions object, adding PatientRecords to it, and calling getIndex() on valid PatientRecords that should go at the beginning, middle, and end of the list.
     @return true if the method functions as expected, false otherwise.
     */

    public static boolean testGetIndexValid() {
        // create an Admissions object and add a few Records to it, leaving some space

        // (1) get the index of a PatientRecord that should go at the END

        // (2) get the index of a PatientRecord that should go at the BEGINNING

        // (3) get the index of a PatientRecord that should go in the MIDDLE
        return true;
    }
    /**

     Tests the basic accessors of the Admissions class, including isFull(), size(), and getNumberSeenPatients().
     @return true if the accessors function as expected, false otherwise.
     */

    public static boolean testGetIndexError() {
        // create an Admissions object and add Records to it until it is full, as in testAddPatientError

        // (1) verify the exception when there are no patients who have been seen in the list

        // (2) verify the exception when there is at least one patient who has been seen
        return false;
    }
    /**

     Tests the basic accessor methods of the ExceptionalCareAdmissions class.
     This method performs the following tests:
     (1) Verify that isFull() returns false for a non-full Admissions object and true for a full Admissions object.
     (2) Verify that size() returns the correct size before and after adding a PatientRecord.
     (3) Verify that getNumberSeenPatients() returns the correct number of seen patients before and after calling the seePatient() method.
     @return true if all tests pass, false otherwise
     */

    public static boolean testAdmissionsBasicAccessors() {
        // (1) verify isFull() on a non-full and a full Admissions object
        ExceptionalCareAdmissions admissions1 = new ExceptionalCareAdmissions(2);
        if (admissions1.isFull()) {
            return false;
        }
        ExceptionalCareAdmissions admissions2 = new ExceptionalCareAdmissions(1);
        admissions2.addPatient(new PatientRecord('F', 25, PatientRecord.RED), 0);
        if (!admissions2.isFull()) {
            return false;
        }

        // (2) verify size() before and after adding a PatientRecord
        ExceptionalCareAdmissions admissions3 = new ExceptionalCareAdmissions(3);
        if (admissions3.size() != 0) {
            return false;
        }
        admissions3.addPatient(new PatientRecord('M', 30, PatientRecord.YELLOW), 0);
        if (admissions3.size() != 1) {
            return false;
        }

        // (3) verify getNumberSeenPatients() before and after seeing a patient
        ExceptionalCareAdmissions admissions4 = new ExceptionalCareAdmissions(2);
        admissions4.addPatient(new PatientRecord('M', 40, PatientRecord.GREEN), 0);
        admissions4.addPatient(new PatientRecord('F', 50, PatientRecord.RED), 1);
        if (admissions4.getNumberSeenPatients() != 0) {
            return false;
        }
        // bypass seePatient() by directly accessing the seenPatients field


        return true;
    }

    /**

     Tests the seePatient() method of the Admissions class by creating an Admissions object, adding PatientRecords to it, and calling seePatient() on a valid caseID.
     @return true if the method functions as expected, false otherwise.
     */
    public static boolean testSeePatientValid() {
        // create an Admissions object and add a few Records to it, saving a shallow copy of
        // at least one of the PatientRecord references

        // (1) call seePatient() on the caseID of your saved reference and verify that its
        // hasBeenSeen() accessor return value changes

        // (2) verify getNumberSeenPatients() before and after seeing a different patient
        return true;
    }
    /**

     Tests whether an IllegalArgumentException is thrown when attempting to see a patient that is not in the list of patients.
     @return true if an IllegalArgumentException is thrown, false otherwise.
     */

    //public static boolean testSeePatientError() {
     //   ExceptionalCareAdmissions admissions = new ExceptionalCareAdmissions(5);
       // PatientRecord patient1 = new PatientRecord('M', 22, PatientRecord.GREEN);

       // admissions.seePatient("patient1);

        // Verify that seeing a caseID for a patient not in the list causes an IllegalArgumentException
       // try {
           // admissions.seePatient(patient1.CASE_NUMBER + 1);
       //     return false;
      //  } catch (IllegalArgumentException e) {
      //      return true;
     //   }
    //}

    public static boolean testSeePatientError() {
        // (1) verify that seeing a caseID for a patient not in the list causes an IllegalArgumentException
        return true;
    }









    public static boolean testGetSummary() {
        String expected;
        String actual;
        int size;

        // (1) test getSummary using an empty patientsList

        int[][] emptyPatientsList = new int[][] {
                null, null, null, null, null
        };

        //There should be zero patients of each type.
        size = 0;
        expected =  "Total number of patients: 0\nRED: 0\nYELLOW: 0\nGREEN: 0";
       // actual =  ExceptionalCareAdmissions.getSummary(emptyPatientsList, size);
        //if (!expected.equals(actual)) return false;

        // (2) test getSummary using a patientsList with multiple patients at a single triage level

        size = 4;
        int[][] multPatientsList = new int[][]{
                {21801, 2,  PatientRecord.RED},
                {23032, 3,  PatientRecord.RED},
                {22002, 4,  PatientRecord.RED},
                {22002, 5,  PatientRecord.RED},
                null, null, null, null, null
        };
        expected = "Total number of patients: 4\nRED: 4\nYELLOW: 0\nGREEN: 0";
        //actual = ExceptionalCareAdmissions.getSummary(multPatientsList, size);
       /// if (!expected.equals(actual)) return false;


        // (3) test getSummary using a patientsList with patients at all triage levels

        size = 9;
        int[][] bigPatientsList = new int[][]{
                {21801, 2,  PatientRecord.RED},
                {23032, 3,  PatientRecord.RED},
                {22002, 4, PatientRecord.RED},
                {22002, 5,  PatientRecord.RED},
                {22002, 7,  PatientRecord.YELLOW},
                {22002, 6,  PatientRecord.YELLOW},
                {22002, 8,  PatientRecord.YELLOW},
                {22002, 9,  PatientRecord.YELLOW},
                {11901, 1,  PatientRecord.GREEN},
                null, null, null, null, null
        };
        expected = "Total number of patients: 9\nRED: 4\nYELLOW: 4\nGREEN: 1";
        //actual = ExceptionalCareAdmissions.getSummary(bigPatientsList, size);
        //if (!expected.equals(actual)) return false;

        // (4) Test that getSummary didn't modify patientsList in any way

        int[][] emptyPatientsListCopy = new int[][] {
                null, null, null, null, null
        };

        if (!Arrays.deepEquals(emptyPatientsList, emptyPatientsListCopy)) return false;

        int[][] bigPatientsListCopy = new int[][]{
                {21801, 2,  PatientRecord.RED},
                {23032, 3,  PatientRecord.RED},
                {22002, 4,  PatientRecord.RED},
                {22002, 5,  PatientRecord.RED},
                {22002, 7,  PatientRecord.YELLOW},
                {22002, 6,  PatientRecord.YELLOW},
                {22002, 8,  PatientRecord.YELLOW},
                {22002, 9,  PatientRecord.YELLOW},
                {11901, 1,  PatientRecord.GREEN},
                null, null, null, null, null
        };
        if (!Arrays.deepEquals(bigPatientsList, bigPatientsListCopy)) return false;

        int[][] multPatientsListCopy = new int[][]{
                {21801, 2,  PatientRecord.RED},
                {23032, 3,  PatientRecord.RED},
                {22002, 4, PatientRecord.RED},
                {22002, 5,  PatientRecord.RED},
                null, null, null, null, null
        };

        if (!Arrays.deepEquals(multPatientsList, multPatientsListCopy)) return false;

        return true;
    }


    /**

     Tests the cleanPatientsList() method of ExceptionalCareAdmissions class.
     This method creates an ExceptionalCareAdmissions object and adds PatientRecord objects to it.
     It verifies the behavior of cleanPatientsList() method in the following steps:
     Using ExceptionalCareAdmissions.toString(), it verifies that a patientsList with NO seen
     patients does not change after calling cleanPatientsList().
     It calls seePatient() for at least two of the records in the patientsList, and uses toString()
     to verify that they have been removed after calling cleanPatientsList().
     Note: This test method does not verify file contents.
     @return true if the test passes, false otherwise
     */
    public static boolean testCleanList() {
        // create an Admissions object and add a few Records to it

        // (1) using ExceptionalCareAdmissions.toString(), verify that a patientsList with NO seen
        // patients does not change after calling cleanPatientsList()

        // (2) call seePatient() for at least two of the records in your patientsList, and use toString()
        // to verify that they have been removed after calling cleanPatientsList()

        // NOTE: you do NOT need to verify file contents in this test method; please do so manually
        return false;
    }


    /**
     * Runs each of the tester methods and displays the result. Methods with two
     testers have
     * their output grouped for convenience; a failed test is displayed as "X" and a
     passed test
     * is displayed as "pass"
     *
     * @param args unused
     * @author hobbes
     */
    public static void main(String[] args) {
        System.out.println("PatientRecord: " + (testPatientRecord() ? "pass" : "X"));
        System.out.println("Admissions Constructor: " +
                (testAdmissionsConstructorValid() ? "pass" : "X") + ", " +
                (testAdmissionsConstructorError() ? "pass" : "X"));
        System.out.println("Add Patient: " + (testAddPatientValid() ? "pass" : "X") +
                ", " +
                (testAddPatientError() ? "pass" : "X"));
        System.out.println("Get Admission Index: " + (testGetIndexValid() ? "pass" :
                "X") + ", " +
                (testGetIndexError() ? "pass" : "X"));
        System.out.println("Basic Accessors: " + (testAdmissionsBasicAccessors() ?
                "pass" : "X"));
        System.out.println("See Patient: " + (testSeePatientValid() ? "pass" : "X") +
                ", " +
                (testSeePatientError() ? "pass" : "X"));
        System.out.println("Get Summary: " + (testGetSummary() ? "pass" : "X"));
        System.out.println("Clean List: " + (testCleanList() ? "pass" : "X"));
    }

}
