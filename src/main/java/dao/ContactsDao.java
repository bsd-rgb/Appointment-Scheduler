package dao;

import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Handles data retrieval from the Contacts database table.
 *
 * @author Brandi Davis
 * */
public class ContactsDao {

    /** Selects all contacts from the database.
     *
     * The contacts are added to the all contacts Observable list in the Contacts class
     * @throws SQLException in the event of an error when executing the query
     * */
    public static void selectContacts() throws SQLException {
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Contacts contactResult;

        while(rs.next()) {
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");

            contactResult = new Contacts(contactId, contactName, contactEmail);
            Contacts.addContact(contactResult);
        }
    }

    /** Retrieves contact ID from the contact name.
     *
     * @param contactName the name of the contact to query
     * @return the contact ID if found and 0 if not found
     * @throws SQLException in the event of an error when executing the query
     * */
    public static int getContactIdFromName(String contactName) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE Contact_Name = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();
        int contactId;

        while(rs.next()) {
            contactId = rs.getInt("Contact_ID");
            return contactId;
        }
        return 0;
    }
}
