package dao;

//See sample files for context

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query {

    private static String sql;
    private static PreparedStatement ps;
    private static ResultSet result;

    public static void createQuery(String sqlQuery)  {
        sql = sqlQuery;
        try {

            if (sql.toLowerCase().startsWith("select")) {
                result = ps.executeQuery();
            }
            if (sql.toLowerCase().startsWith("delete") || sql.toLowerCase().startsWith("update") || sql.toLowerCase().startsWith("insert")) {
                ps.executeUpdate();
            }
        }
        catch(Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
    }

    public static ResultSet getResult() {
        return result;
    }
}
