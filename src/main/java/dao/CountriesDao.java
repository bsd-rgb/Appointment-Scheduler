package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesDao {

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

    public static int getCountryId(String selectedCountry) throws SQLException {
        String sql = "SELECT * FROM countries WHERE Country = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, selectedCountry);
        ResultSet rs = ps.executeQuery();
        int countryId;

        //get the country id, but then what next?
        while(rs.next()) {
            countryId = rs.getInt("Country_ID");
            return countryId;

        }
        return 0;
    }


}
