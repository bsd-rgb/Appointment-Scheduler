package dao;

import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Handles data retrieval from the First Level Divisions database table.
 *
 * @author Brandi Davis
 * */
public class FirstLevelDivisionsDao {

    /** Selects division(s) for the specified country.
     *
     * @param countryId the country ID to query
     * @throws SQLException in the event of an error when executing the query
     * */
    public static void selectDivisionFromCountry(int countryId) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        FirstLevelDivisions divisionResult;

        while(rs.next()) {
            String division = rs.getString("Division");
            int divisionId = rs.getInt("Division_ID");
            int newCountryId = rs.getInt("Country_ID");

            divisionResult = new FirstLevelDivisions(divisionId, division, newCountryId);
            FirstLevelDivisions.addDivisions(divisionResult);
        }
    }

/*   public static int getDivisionId(String selectedDivision) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, selectedDivision);
        ResultSet rs = ps.executeQuery();
        int countryId;

        //get the country id, but then what next?
        while(rs.next()) {
            countryId = rs.getInt("Country_ID");
            return countryId;

        }
        return 0;
    }*/

    /** Selects the country ID from the division ID.
     *
     * @param divisionId the division ID to query
     * @return the country ID if found and 0 if not found
     * @throws SQLException in the event of an error when executing the query
     * */
    public static int getCountryIdFromDivision(int divisionId) throws SQLException {
        //query against first level divisions to get the country ID
        String sql = "SELECT Country_ID FROM first_level_divisions where Division_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        int countryId;

        while(rs.next()) {
            countryId = rs.getInt("Country_ID");
            return countryId;
        }
        return 0;
    }
}
