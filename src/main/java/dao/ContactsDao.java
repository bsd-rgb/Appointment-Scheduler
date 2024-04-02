package dao;

import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsDao {

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
