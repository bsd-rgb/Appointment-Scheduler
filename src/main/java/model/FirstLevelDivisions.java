package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FirstLevelDivisions {

    private int divisionId;
    private String division;
    private int countryId;

    public static ObservableList<FirstLevelDivisions> selectedDivisions = FXCollections.observableArrayList();

    public static void addDivisions(FirstLevelDivisions division) {
        selectedDivisions.add(division);
    }

    public static void clearDivisions() {
        selectedDivisions.clear();
    }
    @Override
    public String toString() {
        return division;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }



    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public FirstLevelDivisions(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }


}
