package dao;

import helper.TimeUtil;
import model.Appointments;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentsDao {

    public static void insertAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end
                                        , LocalDateTime createDate, String createdBy, LocalDateTime lastUpdated, String updatedBy, int customerId, int userId, int contactId) throws SQLException {

        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By" +
                ", Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        //ps.setTimestamp(5,start);
        //Use Timestamp for now
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setTimestamp(7, Timestamp.valueOf(createDate));
        ps.setString(8, createdBy);
        ps.setTimestamp(9, Timestamp.valueOf(lastUpdated));
        ps.setString(10, updatedBy);
        ps.setInt(11, customerId);
        ps.setInt(12, userId);
        ps.setInt(13, contactId);
        ps.executeUpdate();
    }


    public static void SelectAppointments() throws SQLException {

        Appointments.allAppointments.clear();

        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Appointments appointmentResult;

        while(rs.next()) {

            int apptId = rs.getInt("Appointment_ID");
            String apptTitle = rs.getString("Title");
            String apptDesc = rs.getString("Description");
            String apptLocation = rs.getString("Location");
            String apptType = rs.getString("Type");
            Timestamp apptStart = rs.getTimestamp("Start");
            Timestamp apptEnd = rs.getTimestamp("End");
            int apptCustomerId = rs.getInt("Customer_ID");
            int apptUserId = rs.getInt("User_ID");
            int apptContactId = rs.getInt("Contact_ID");

            ZonedDateTime apptStartUTC = apptStart.toLocalDateTime().atZone(ZoneId.of("UTC"));
            ZonedDateTime apptEndUTC = apptEnd.toLocalDateTime().atZone(ZoneId.of("UTC"));
            ZonedDateTime apptStartLocal = TimeUtil.ToLocal(apptStartUTC);
            ZonedDateTime apptEndLocal = TimeUtil.ToLocal(apptEndUTC);

       appointmentResult = new Appointments(apptId, apptTitle, apptDesc, apptLocation, apptType, apptStartLocal.toLocalDateTime(), apptEndLocal.toLocalDateTime(),
                    apptCustomerId, apptUserId,apptContactId);
            Appointments.addAppointment(appointmentResult);

        }

    }

    public static void UpdateAppointment(int appointmentId, String title, String description, String location, String type
    ,LocalDateTime start, LocalDateTime end, LocalDateTime lastUpdated, String lastUpdatedBy, int customerId, int userId, int contactId) throws SQLException {

        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?" +
                ", Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setTimestamp(7, Timestamp.valueOf(lastUpdated));
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, customerId);
        ps.setInt(10, userId);
        ps.setInt(11, contactId);
        ps.setInt(12, appointmentId);
        ps.executeUpdate();
    }

    public static void DeleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        ps.executeUpdate();

    }
}
