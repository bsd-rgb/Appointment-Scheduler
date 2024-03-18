package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CustomersDao {


    public static void selectCustomers() throws SQLException {
        String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Customers customerResult;

        while(rs.next()) {

            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");

            customerResult = new Customers(customerId,customerName, customerAddress, postalCode, phoneNumber, divisionId);
            Customers.addCustomer(customerResult);
        }

    }


    public static void updateCustomer(int id, Customers newCustomer) {

    }

    public static boolean deleteCustomer(Customers selectedCustomer) {
        return false;
    }

    public static void insertCustomer (String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {

        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        /*ps.setTimestamp(5, Timestamp.valueOf(createDate));
        ps.setTimestamp(6, Timestamp.valueOf(lastUpdated));*/
        ps.setInt(5, divisionId);
        ps.executeUpdate();


      /*  int rowsAffected = ps.executeUpdate();
        return rowsAffected;*/
    }


}
