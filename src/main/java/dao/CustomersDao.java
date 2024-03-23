package dao;

import controller.AddCustomerController;
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

        Customers.customers.clear();

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


    public static void updateCustomer(int id, String customerName, String address, String postalCode,String phone, LocalDateTime lastUpdated, String updatedBy, int divisionId) throws SQLException {

        //String sql = "UPDATE fruits SET Fruit_Name = ? WHERE Fruit_ID = ?";
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.valueOf(lastUpdated));
        ps.setString(6, updatedBy);
        ps.setInt(7, divisionId);
        ps.setInt(8, id);
        ps.executeUpdate();
    }

    public static boolean deleteCustomer(Customers selectedCustomer) {
        return false;
    }

        //add the created by and last updated by fields and put that into the project.
    public static int insertCustomer (String customerName, String address, String postalCode,String phone, LocalDateTime createDate,
                                      String createdBy, LocalDateTime lastUpdated, String updatedBy, int divisionId) throws SQLException {

        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?,?,?,?,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.valueOf(createDate));
        ps.setString(6, createdBy);
        ps.setTimestamp(7, Timestamp.valueOf(lastUpdated));
        ps.setString(8, updatedBy);
        ps.setInt(9, divisionId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }

    public static void deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.executeUpdate();
    }


}
