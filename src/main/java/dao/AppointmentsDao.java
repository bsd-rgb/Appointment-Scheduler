package dao;

import model.Appointments;

import java.sql.*;
import java.time.LocalDateTime;

/** Handles data manipulation and retrieval from the Appointments database table.
 *
 * @author Brandi Davis
 * */
public class AppointmentsDao {

    /** Inserts appointment record into database.
     *
     * @param title the title of the appointment
     * @param description the description of the appointment
     * @param location the location of the appointment
     * @param type the type of appointment
     * @param start the local start date and time of the appointment
     * @param end the local end date and time of the appointment
     * @param createDate the local date and time of the appointment creation
     * @param createdBy the user who created the appointment
     * @param lastUpdated the local date time of when the appointment is updated
     * @param lastUpdatedBy the user that last updated the appointment record
     * @param customerId the customer ID associated with the appointment
     * @param userId the user ID associated with the appointment
     * @param contactId the contact ID associated with the appointment
     * @throws SQLException in the event of an error when executing the insert statement
     * */
    public static void insertAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end
                                        , LocalDateTime createDate, String createdBy, LocalDateTime lastUpdated, String lastUpdatedBy, int customerId, int userId, int contactId) throws SQLException {

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
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11, customerId);
        ps.setInt(12, userId);
        ps.setInt(13, contactId);
        ps.executeUpdate();
    }

    /** Selects all appointments in the database and creates appointment objects.
     *
     * The appointments are added to the all appointments Observable list in the Appointments Class
     * @throws SQLException in the event of an error when executing the query
     * */
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

    /** Updates an appointment record in the database.
     *
     * @param appointmentId the selected appointment ID to update
     * @param title the title of the appointment
     * @param description the description of the appointment
     * @param location the location of the appointment
     * @param type the type of appointment
     * @param start the local start date and time of the appointment
     * @param end the local end date and time of the appointment
     * @param lastUpdated the local date time of when the appointment is updated
     * @param lastUpdatedBy the user who last updated the appointment
     * @param customerId the customer ID associated with the appointment
     * @param userId the user ID associated with the appointment
     * @param contactId the contact ID associated with the appointment
     * @throws SQLException in the event of an error executing the update statement
     * */
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

    /** Deletes appointment record from the database.
     *
     * @param appointmentId the appointment to be deleted
     * @throws SQLException in the event of an error executing the delete statement
     * */
    public static void DeleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        ps.executeUpdate();
    }

    /** Selects distinct appointment types for the report that counts appointments by month and type.
     *
     * @throws SQLException in the event of an error executing the query
     * */
    public static void SelectDistinctApptType() throws SQLException {
        Appointments.appointmentTypes.clear();
        String sql = "SELECT DISTINCT Type FROM appointments";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String type = rs.getString("Type");
            Appointments.appointmentTypes.add(type);
        }
    }

    /** Determines if a customer has an appointment.
     *
     * @param customerId the customer to query
     * @return boolean, true or false value if an appointment is found
     * @throws SQLException in the event of an error executing the query
     * */
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

    /** Selects appointments with a specified month and type.
     *
     * Creates appointment object(s) and adds to filtered list
     * @param month the month of the appointment to query
     * @param type the type of the appointment to query
     * @throws SQLException in the event of an error executing the query
     * */
    public static void SelectMonthAndType(int month, String type) throws SQLException {
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

    /** Counts the number of appointments of a specified month and type.
     *
     * @param type the type of appointment to query
     * @param month the month of the appointment to query
     * @throws SQLException in the event of an error executing the query
     * */
    public static void CountAppointmentFilter(String type, int month) throws SQLException {
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

    /** Counts the appointment(s) in the specified Country.
     *
     * @param countryId the Country ID to query for the appointment count
     * @throws SQLException in the event of an error executing the query
     * */
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

    /** Selects the appointments of the specified contact.
     *
     * Creates appointment object(s) and adds to filtered list
     * @param contactId the contact ID to query
     * @throws SQLException in the event of an error executing the query
     * */
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
