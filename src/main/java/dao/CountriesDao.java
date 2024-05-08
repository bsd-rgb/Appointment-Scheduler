package dao;

import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Handles data retrieval from the Countries database table.
 *
 * @author Brandi Davis
 * */
public class CountriesDao {

    /** Selects all countries from the database.
     *
     * The countries are added to the all countries Observable list in the Countries class
     * @throws SQLException in the event of an error when executing the query
     * */
    public static void selectAllCountries() throws SQLException {
        String sql = "SELECT Country, Country_ID FROM countries";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Countries countryResult;

        while(rs.next()) {
            int countryId = rs.getInt("Country_ID");
            String country = rs.getString("Country");

            countryResult = new Countries(countryId, country);
            Countries.loadCountries(countryResult);
        }
    }

    /** Retrieves the country ID from the country name.
     *
     * @param selectedCountry the country name to query
     * @return the country ID if found and 0 if not found
     * @throws SQLException in the event of an error when executing the query
     * */
    public static int getCountryId(String selectedCountry) throws SQLException {
        String sql = "SELECT * FROM countries WHERE Country = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, selectedCountry);
        ResultSet rs = ps.executeQuery();
        int countryId;

        while(rs.next()) {
            countryId = rs.getInt("Country_ID");
            return countryId;
        }
        return 0;
    }
}
