package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentsDao {

    public static void insertAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end
                                        , LocalDateTime createDate, String createdBy, LocalDateTime lastUpdated, String updatedBy, int customerId, int userId, int contactId) throws SQLException {


        final String timePattern = "yyyy-mm-dd HH:mm:ss";
        final DateTimeFormatter patternFormatter = DateTimeFormatter.ofPattern(timePattern);
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By" +
                ", Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        //ps.setTimestamp(5,start);
        //Use Timestamp for now
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setTimestamp(7, Timestamp.valueOf(createDate));
        ps.setString(8, createdBy);
        ps.setTimestamp(9, Timestamp.valueOf(lastUpdated));
        ps.setString(10, updatedBy);
        ps.setInt(11, customerId);
        ps.setInt(12, userId);
        ps.setInt(13, contactId);
        ps.executeUpdate();
    }
}
