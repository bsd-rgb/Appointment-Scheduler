package controller;

import com.bd.Application;
import dao.CountriesDao;
import dao.CustomersDao;
import dao.FirstLevelDivisionsDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Countries;
import model.FirstLevelDivisions;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/** The AddCustomerController is used to add a customer to the database.
 *
 * @author Brandi Davis
 * */
public class AddCustomerController implements Initializable {
    @FXML
    private TextField addressTxtField;
    @FXML
    private ComboBox<Countries> countryCombo;
    @FXML
    private TextField customerNameTxtField;
    @FXML
    private ComboBox<FirstLevelDivisions> divisionCombo;
    @FXML
    private TextField phoneTxtField;
    @FXML
    private TextField postalCodeTxtField;
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);


    /** Initializes the AddCustomerController and sets the items in the Country combo box.
     *
     * @param url The location used to resolve relative paths for root object
     * @param resourceBundle The resources used to localize the root object
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(Countries.allCountries);
    }


    /** Attempts to add a customer to the database.
     *
     * An error will display if any of the fields are empty
     * Uses the insertCustomer() method from the CustomersDao
     * Navigates to the ViewCustomersController on success
     * @param event on action add customer button
     * */
    @FXML
    void onActionAddCustomer(ActionEvent event) {
        //add the statement to see if the text fields are empty
        if (customerNameTxtField.getText().isEmpty() || addressTxtField.getText().isEmpty() || phoneTxtField.getText().isEmpty() || postalCodeTxtField.getText().isEmpty()
                || divisionCombo.getValue() == null) {

            errorAlert.setContentText("ERROR: One or more fields are empty. Please check values and try again.");
            errorAlert.showAndWait();
        } else {
            try {
                String customerName = customerNameTxtField.getText();
                String address = addressTxtField.getText();
                String phone = phoneTxtField.getText();
                String postalCode = postalCodeTxtField.getText();
                int divisionId = divisionCombo.getValue().getDivisionId();
                LocalDateTime createdDate = LocalDateTime.now();
                LocalDateTime lastUpdated = LocalDateTime.now();
                String loggedInUser = Users.getLoggedInUser().getUserName();

                CustomersDao.insertCustomer(customerName, address, postalCode, phone, createdDate, loggedInUser, lastUpdated, loggedInUser, divisionId);
                CustomersDao.selectCustomers();

                informationAlert.setContentText("Customer successfully added.");
                informationAlert.showAndWait();
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ViewCustomer.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                errorAlert.setContentText("Error adding customer.");
                errorAlert.showAndWait();
                System.out.println(e.getMessage());
            }
        }
    }

    /** Used to select the appropriate First Level Divisions for the selected Country.
     *
     * Uses the selectDivisionFromCountry() method from the FirstLevelDivisionsDao
     * Displays the first item in the Division combo box
     * @param event on action select country from combo box
     * */
    @FXML
    void onActionCountrySelected(ActionEvent event)  {
        divisionCombo.setValue(null);
        FirstLevelDivisions.clearDivisions();
        Countries selectedCountry = countryCombo.getValue();

        try {
            FirstLevelDivisionsDao.selectDivisionFromCountry(CountriesDao.getCountryId(String.valueOf(selectedCountry)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        divisionCombo.getSelectionModel().selectFirst();
        divisionCombo.setItems(FirstLevelDivisions.selectedDivisions);
    }

    /** Used to cancel the input and navigate back to the ViewCustomersController.
     *
     * There is a confirmation alert to confirm cancellation
     * @param event on action cancel button
     * @throws IOException  from FXMLLoader in the event of an error loading the ViewCustomersController
     * */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        confirmationAlert.setContentText("Are you sure you want to cancel? All changes will be lost.");
        confirmationAlert.showAndWait();
        if(confirmationAlert.getResult() == ButtonType.OK) {
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ViewCustomer.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }else {
            return;
        }
    }
}
