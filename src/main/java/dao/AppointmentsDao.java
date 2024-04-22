package dao;

import com.mysql.cj.protocol.Resultset;
import helper.TimeUtil;
import model.Appointments;
import model.Customers;

import javax.xml.transform.Result;
import java.nio.file.ReadOnlyFileSystemException;
import java.sql.*;
import java.time.LocalDateTime;


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


        String sql = "SELECT a.*, c.Contact_Name FROM appointments AS a JOIN contacts AS c ON a.Contact_ID = c.Contact_ID ORDER BY a.Appointment_ID";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Appointments appointmentResult;

        while(rs.next()) {

            int apptId = rs.getInt("Appointment_ID");
            String apptTitle = rs.getString("Title");
            String apptDesc = rs.getString("Description");
            String apptLocation = rs.getString("Location");
            String apptType = rs.getString("Type");
            String contactName = rs.getString("Contact_Name");
            Timestamp apptStart = rs.getTimestamp("Start");
            Timestamp apptEnd = rs.getTimestamp("End");
            int apptCustomerId = rs.getInt("Customer_ID");
            int apptUserId = rs.getInt("User_ID");
            int apptContactId = rs.getInt("Contact_ID");

       appointmentResult = new Appointments(apptId, apptTitle, apptDesc, apptLocation, apptType, apptStart.toLocalDateTime(), apptEnd.toLocalDateTime(),
                    apptCustomerId, apptUserId,apptContactId, contactName);
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

    public static void filterAppointmentWeek() throws SQLException {
        String sql = "SELECT * FROM appointments WHERE week(start) = week(current_date())";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        //I would need to add each appointment to a filtered list
        while(rs.next()){

        }

    }

    public static void SelectDistinctApptType() throws SQLException {

        Appointments.appointmentTypes.clear();
        String sql = "SELECT DISTINCT Type FROM appointments";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        //Appointments.appointmentTypes.add("All");

        while(rs.next()){

            String type = rs.getString("Type");
            Appointments.appointmentTypes.add(type);


        }


    }

    public static Boolean hasAppointment(int customerId) throws SQLException {

        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        //if the result set returns one or more appointments where the customer ID matches, then true else false
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }
    //try returning int to get the count
    public static void SelectMonthAndType(int month, String type) throws SQLException {
        /*SELECT * FROM client_schedule.appointments WHERE Month(start) = 4 AND Type = 'General'*/
        String sql = "SELECT * FROM appointments WHERE Month(Start) = ? AND Type = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, month);
        ps.setString(2, type);
        ResultSet rs = ps.executeQuery();
        Appointments appointmentResult;

        while(rs.next()){

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

            appointmentResult = new Appointments(apptId, apptTitle, apptDesc, apptLocation, apptType, apptStart.toLocalDateTime(), apptEnd.toLocalDateTime(),
                    apptCustomerId, apptUserId,apptContactId);
            Appointments.filteredAppointments.add(appointmentResult);
        }
    }

    public static void CountAppointmentFilter(String type, int month) throws SQLException {

        //SELECT  COUNT(*) AS Count FROM client_schedule.appointments WHERE Type = 'General' AND Month(Start) = 4  GROUP BY Type
        Appointments.setAppointmentFilterListCount(0);

        String sql = "SELECT COUNT(*) AS Count FROM client_schedule.appointments WHERE Type = ?  AND Month(Start) = ? GROUP BY Type";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        ps.setString(1, type);
        ps.setInt(2, month);
        ResultSet rs = ps.executeQuery();


        if(rs.next()){

            int count = rs.getInt("Count");
            Appointments.setAppointmentFilterListCount(count);
        }

    }

    public static void SelectAppointmentCountryCount(int countryId) throws SQLException {

        Appointments.setAppointmentFilterListCount(0);
        String sql = "SELECT cntry.Country,Count(*) as Appointment_Count FROM client_schedule.appointments a JOIN client_schedule.customers c ON a.Customer_ID = c.Customer_ID JOIN client_schedule.first_level_divisions fld ON c.Division_ID = fld.Division_ID JOIN client_schedule.countries cntry ON fld.Country_ID = cntry.Country_ID WHERE cntry.Country_ID = ? GROUP BY cntry.Country";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            int count = rs.getInt("Appointment_Count");
            Appointments.setAppointmentFilterListCount(count);
        }
    }

    public static void SelectAppointmentByContact(int contactId) throws SQLException {
        Appointments.filteredAppointments.clear();

        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();
        Appointments appointmentResult;

        while(rs.next()){
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

            appointmentResult = new Appointments(apptId, apptTitle, apptDesc, apptLocation, apptType, apptStart.toLocalDateTime(), apptEnd.toLocalDateTime(),
                    apptCustomerId, apptUserId,apptContactId);
            Appointments.filteredAppointments.add(appointmentResult);
        }
    }



}
