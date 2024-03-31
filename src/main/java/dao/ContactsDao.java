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
}
