package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The First Level Divisions class provides methods for retrieving and setting First Level Division information.
 *
 * @author Brandi Davis
 * */
public class FirstLevelDivisions {

    /** The first level division ID. */
    private int divisionId;

    /** The first level division name. */
    private String division;

    /** The country ID associated with the first level division. */
    private int countryId;

    /** An Observable list to hold the selected first level divisions.
     *
     * The divisions are selected from the associated country throughout the program
     * */
    public static ObservableList<FirstLevelDivisions> selectedDivisions = FXCollections.observableArrayList();

    /** Overrides the toString() method to return the division name instead of the object's name.
     *
     * @return the first level division name
     * */
    @Override
    public String toString() {
        return division;
    }

    /** The constructor for the first level divisions class.
     *
     * @param divisionId the ID of the first level division
     * @param division the name of the first level division
     * @param countryId the country ID associated with the first level division
     * */
    public FirstLevelDivisions(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /** Adds first level division to the selected divisions Observable list.
     *
     * @param division the first level division to be added
     * */
    public static void addDivisions(FirstLevelDivisions division) {
        selectedDivisions.add(division);
    }

    /** Clears the selected divisions Observable list. */
    public static void clearDivisions() {
        selectedDivisions.clear();
    }

    /** Retrieves the first level division ID.
     *
     * @return the division ID
     * */
    public int getDivisionId() {
        return divisionId;
    }
}
