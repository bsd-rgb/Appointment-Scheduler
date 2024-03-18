package dao;

import model.FirstLevelDivisions;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionsDao {

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

    public static int getDivisionId(String selectedDivision) throws SQLException {
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
    }


}
