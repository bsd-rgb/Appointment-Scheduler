package dao;

import com.mysql.cj.protocol.Resultset;
import model.CommercialCustomer;
import model.Customers;
import model.ResidentialCustomer;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/** Handles data manipulation and retrieval from the Customers database table.
 *
 * @author Brandi Davis
 * */
public class CustomersDao {

    /** Selects all customers from the database.
     *
     * Adds allCustomers and customer IDs in Observable lists in the Customers class
     * @throws SQLException in the event of an error when executing the query
     * */
    public static void selectCustomers() throws SQLException {
        Customers.allCustomers.clear();

        String sql = "SELECT * FROM customers";
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
            String customerType = rs.getString("Customer_Type");

            if(customerType.equalsIgnoreCase("residential")){
                String defaultPackage = "Basic";
                customerResult = new ResidentialCustomer(customerId,customerName, customerAddress, postalCode, phoneNumber, divisionId, customerType, defaultPackage);
                Customers.addCustomer(customerResult);
            }else if(customerType.equalsIgnoreCase("commercial")){
                String defaultContract = "Annual";
                customerResult = new CommercialCustomer(customerId,customerName, customerAddress, postalCode, phoneNumber, divisionId, customerType, defaultContract);
                Customers.addCustomer(customerResult);
            }

            Customers.addCustomerId(customerId);
        }
    }

    /** Updates customer information in the database.
     *
     * @param id the customer ID to update
     * @param customerName the name of the customer
     * @param address the address of the customer
     * @param postalCode the postal code of the customer
     * @param phone the phone number of the customer
     * @param lastUpdated the local date time when the customer record was last updated
     * @param updatedBy the user who last updated the customer record
     * @param divisionId the division ID of the customer record
     * @throws SQLException in the event of an error when executing update statement
     * */
    public static void updateCustomer(int id, String customerName, String address, String postalCode,String phone,
                                      LocalDateTime lastUpdated, String updatedBy, int divisionId, String customerType) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ?, Customer_Type = ? WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.valueOf(lastUpdated));
        ps.setString(6, updatedBy);
        ps.setInt(7, divisionId);
        ps.setString(8, customerType);
        ps.setInt(9, id);
        ps.executeUpdate();
    }

    /** Inserts customer record into the database.
     *
     * @param customerName the name of the customer
     * @param address the address of the customer
     * @param postalCode the postal code of the customer
     * @param phone the phone number of the customer
     * @param createDate the creation date and time of the customer record
     * @param createdBy the user that created the customer record
     * @param lastUpdated the date and time of when the customer record was last updated
     * @param updatedBy the user that last updated the customer record
     * @param divisionId the division ID of the customer record
     * @throws SQLException in the event of an error when executing insert statement
     * */
    public static void insertCustomer (String customerName, String address, String postalCode,String phone, LocalDateTime createDate,
                                      String createdBy, LocalDateTime lastUpdated, String updatedBy, int divisionId, String customerType) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID, Customer_Type) VALUES(?, ?, ?, ?, ?,?,?,?,?,?)";
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
        ps.setString(10, customerType);
        ps.executeUpdate();
    }

    /** Deletes customer record from the database.
     *
     * @param customerId the customer ID to be deleted
     * @throws SQLException in the event of an error when executing delete statement
     * */
    public static void deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.executeUpdate();
    }

    public static void SelectDistinctCustomerType() throws SQLException {
        Customers.customerTypes.clear();
        String sql = "SELECT DISTINCT Customer_Type from customers";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String type = rs.getString("Customer_Type");
            Customers.customerTypes.add(type);
        }
    }
    public static void CountCustomerTypeFilter(String customerType) throws SQLException {
        Customers.setCustomerFilterListCount(0);

        String sql = "SELECT COUNT(*) AS Count FROM client_schedule.customers WHERE Customer_Type = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, customerType);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            int count = rs.getInt("Count");
            Customers.setCustomerFilterListCount(count);
        }
    }

}
