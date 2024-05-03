package controller;

import com.bd.Application;
import dao.AppointmentsDao;
import dao.CustomersDao;
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
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alert = new Alert(Alert.AlertType.NONE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            CustomersDao.selectCustomers();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        customerTable.setItems(Customers.getAllCustomers());

        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

    }



    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("AddCustomer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onActionGoBack(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("NavigationScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }

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
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Error deleting customer.\n" + e.getMessage());
        }
    }

    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Application.class.getResource("UpdateCustomer.fxml"));
        loader.load();

        UpdateCustomerController updateCustomerController = loader.getController();
        updateCustomerController.sendCustomer(customerTable.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }




}

