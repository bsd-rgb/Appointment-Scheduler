package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The Customers class provides methods for retrieving customer information.
 *
 * @author Brandi Davis
 * */
public class Customers {

    /** The ID of the customer. */
    private int customerId;

    /** The name of the customer. */
    private String customerName;

    /** The address of the customer. */
    private String address;

    /** The customer's postal code. */
    private String postalCode;

    /** The customer's phone number. */
    private String phone;

    /** The division ID of the customer. */
    private int divisionId;

    /** An Observable list to hold all customers. */
    public static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

    /** An Observable list to hold all customer IDs. */
    public static ObservableList<Integer> customerIds = FXCollections.observableArrayList();

    /** A constructor for the Customers class used for customer retrieval.
     *
     * @param customerId the ID of the customer
     * @param customerName the name of the customer
     * @param address the address of the customer
     * @param postalCode the customer's postal code
     * @param phone the customer's phone number
     * @param divisionId the first level division ID of the customer
     * */
    public Customers(int customerId, String customerName, String address, String postalCode, String phone, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /** A constructor for the Customers class used for customer creation.
     *
     * @param customerName the name of the customer
     * @param address the address of the customer
     * @param postalCode the customer's postal code
     * @param phone the customer's phone number
     * @param divisionId the first level division ID of the customer
     * */
    public Customers( String customerName, String address, String postalCode, String phone, int divisionId) {
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /** Retrieves customer phone number.
     *
     * @return the phone number
     * */
    public String getPhone() {
        return phone;
    }

    /** Returns the ID of the customer.
     *
     * @return the customer ID
     * */
    public int getCustomerId() {
        return customerId;
    }

    /** Returns the name of the customer.
     *
     * @return the customer name
     * */
    public String getCustomerName() {
        return customerName;
    }

    /** Returns the customer address.
     *
     * @return the address of the customer
     * */
    public String getAddress() {
        return address;
    }

    /** Returns the customer's postal code.
     *
     * @return the postal code
     * */
    public String getPostalCode() {
        return postalCode;
    }

    /** Returns the first level division ID of the customer.
     *
     * @return the division ID
     * */
    public int getDivisionId() {
        return divisionId;
    }

    /** Adds a customer to the all customers Observable list.
     *
     * @param newCustomer the customer to be added
     * */
    public static void addCustomer(Customers newCustomer) {
        allCustomers.add(newCustomer);
    }

    /** Retrieves all customers from the all customers Observable list.
     *
     * @return all customers Observable list
     * */
    public static ObservableList<Customers> getAllCustomers() {
        return allCustomers;
    }

    /** Adds customer ID to the all customer IDs Observable list.
     *
     * @param id the customer ID to be added
     * */
    public static void addCustomerId(int id) {
        customerIds.add(id);
    }

    /** Retrieves all customer IDs from the all customer IDs Observable list.
     *
     * @return all customer IDs Observable list
     * */
    public static ObservableList<Integer> getCustomerIds() {
        return customerIds;
    }
}
