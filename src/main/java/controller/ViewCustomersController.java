package controller;

import com.bd.Application;
import dao.AppointmentsDao;
import dao.CustomersDao;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** The ViewCustomersController displays customer information in a Tableview.
 *
 * The page contains button links to add, modify, and delete customers.
 * @author Brandi Davis
 * */
public class ViewCustomersController implements Initializable {
    @FXML
    private TableColumn<Customers, String> customerNameCol;
    @FXML
    private TableView<Customers> customerTable;
    @FXML
    private TableColumn<Customers, String> addressCol;
    @FXML
    private TableColumn<Customers, Integer> customerIdCol;
    @FXML
    private TableColumn<Customers, Integer> divisionCol;
    @FXML
    private TableColumn<Customers, String> phoneCol;
    @FXML
    private TableColumn<Customers, String> postalCodeCol;
    @FXML
    private TableColumn<Customers, String> customerTypeCol;

    @FXML
    private TextField customerSearchTxt;
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alert = new Alert(Alert.AlertType.NONE);

    /** Initializes the ViewCustomersController and sets the cell value data for the customer Tableview.
     *
     * Sets the items for the customer Tableview
     * @param url The location used to resolve relative paths for root object
     * @param resourceBundle The resources used to localize the root object
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            CustomersDao.selectCustomers();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        customerTable.setItems(Customers.getAllCustomers());
        SearchCustomer();
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        customerTypeCol.setCellValueFactory(new PropertyValueFactory<>("customerType"));
    }



    /** Navigates to the AddCustomerController.
     *
     * @param event on action add customer button
     * @throws IOException from FXMLLoader in the event of an error loading the AddCustomerController
     * */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("AddCustomer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /** Navigates to the NavigationScreenController.
     *
     * @param event on action back button
     * @throws IOException from FXMLLoader in the event of an error loading the NavigationScreenController
     * */
    @FXML
    void onActionGoBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("NavigationScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /** Deletes the selected customer from the database.
     *
     * Verifies if the customer has any appointments and will display an error prompt if they do
     * Confirm deletion with confirmation prompt and refreshes the Customer Tableview
     * @param event on action delete button
     * */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) {
        Customers customer = customerTable.getSelectionModel().getSelectedItem();

        try {
            if (AppointmentsDao.hasAppointment(customer.getCustomerId())) {
                System.out.println("The customer has an appointment");
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Can not delete customer. Delete customer appointments first and then try again.");
                alert.showAndWait();
                return;
            } else {
                confirmationAlert.setContentText("Are you sure you would like to delete the selected customer?");
                confirmationAlert.showAndWait();

                if (confirmationAlert.getResult() == ButtonType.OK) {
                CustomersDao.deleteCustomer(customer.getCustomerId());
                CustomersDao.selectCustomers();
                customerTable.setItems(Customers.getAllCustomers());
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Customer successfully deleted.");
                alert.showAndWait();
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Error deleting customer.\n" + e.getMessage());
        }
    }

    /** Gathers the information for the selected customer and navigates to the UpdateCustomerController.
     *
     * @param event on action modify button
     * @throws IOException from FXMLLoader in the event of an error loading the UpdateCustomerController
     * */
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Application.class.getResource("UpdateCustomer.fxml"));
        loader.load();
        UpdateCustomerController updateCustomerController = loader.getController();

        try {
            updateCustomerController.sendCustomer(customerTable.getSelectionModel().getSelectedItem());
        }catch (Exception e){
            System.out.println("Error sending customer information:\n" + e.getMessage());
        }

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void SearchCustomer(){
        FilteredList<Customers> filteredList = new FilteredList<>(Customers.getAllCustomers());

        customerSearchTxt.textProperty().addListener((observable, oldValue, newValue) -> {filteredList.setPredicate(customer -> {

            if(newValue == null || newValue.isEmpty()){
                return true;
            }
            if(String.valueOf(customer.getCustomerId()).contains(newValue)){
                return true;
            } else if(customer.getCustomerName().contains(newValue) || customer.getCustomerName().toLowerCase().contains(newValue)) {
                return true;
            } else if(customer.getAddress().contains(newValue) || customer.getAddress().toLowerCase().contains(newValue)){
                return true;
            } else if(customer.getPostalCode().contains(newValue) || customer.getPostalCode().toLowerCase().contains(newValue)){
                return true;
            }else if(customer.getCustomerType().contains(newValue) || customer.getCustomerType().toLowerCase().contains(newValue)) {
                return true;
            }else if(customer.getPhone().contains(newValue)) {
                return true;
            }
            return false;
        });});
        //create sorted list here
        SortedList<Customers> sortedCustomers = new SortedList<>(filteredList);
        sortedCustomers.comparatorProperty().bind(customerTable.comparatorProperty());
        customerTable.setItems(sortedCustomers);
    }
}

