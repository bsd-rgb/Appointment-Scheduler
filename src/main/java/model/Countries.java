package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Countries {

    private int countryId;
    private String country;
    public static ObservableList<Countries> allCountries = FXCollections.observableArrayList();

    public static void loadCountries(Countries country) {

        allCountries.add(country);

    }
    @Override
    public String toString() {
        return country;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Countries(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }
}
