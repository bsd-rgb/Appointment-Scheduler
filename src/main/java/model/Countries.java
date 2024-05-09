package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The Countries class provides methods for retrieving country information.
 *
 * @author Brandi Davis
 * */
public class Countries {

    /** The ID of the country. */
    private int countryId;

    /** The name of the country. */
    private String country;

    /** An Observable list that holds all countries from the database. */
    public static ObservableList<Countries> allCountries = FXCollections.observableArrayList();

    /** The constructor for the Countries class.
     *
     * @param countryId the ID of the country
     * @param country the name of the country
     * */
    public Countries(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /** Overrides the toString() method to return the country name instead of the object's name.
     *
     * @return the name of the country
     * */
    @Override
    public String toString() {
        return country;
    }

    /** Adds countries to the all countries Observable list.
     *
     * @param country the country to be added
     * */
    public static void loadCountries(Countries country) {
        allCountries.add(country);
    }

    /** Retrieves all countries.
     *
     * @return the all countries observable list items
     * */
    public ObservableList<Countries> getCountries() {
        return allCountries;
    }

    /** Retrieves the country ID.
     *
     * @return the ID of the country
     * */
    public int getCountryId() {
        return countryId;
    }

    /** Retrieves the country name.
     *
     * @return the name of the country
     * */
    public String getCountry() {
        return country;
    }
}
    /*    public void setCountry(String country) {
        this.country = country;
    }*/


