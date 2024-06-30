package controller;

import com.bd.Application;
import dao.CountriesDao;
import dao.CustomersDao;
import dao.FirstLevelDivisionsDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/** The UpdateCustomerController is used to update customer information in the database.
 *
 * @author Brandi Davis
 * */
public class UpdateCustomerController implements Initializable {
    @FXML
    private TextField updateCustAddress;
    @FXML
    private ComboBox<Countries> updateCustCountryCombo;
    @FXML
    private ComboBox<FirstLevelDivisions> updateCustDivision;
    @FXML
    private TextField updateCustId;
    @FXML
    private TextField updateCustName;
    @FXML
    private TextField updateCustPhone;
    @FXML
    private TextField updateCustPostalCode;
    @FXML
    private RadioButton commercialRadio;
    @FXML
    private RadioButton residentialRadio;
    @FXML
    private TextArea customerTypeTxtArea;
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    /** Initializes the UpdateCustomerController.
     *
     * Disables the customer ID field
     * Populates the country and division combo boxes
     * @param url The location used to resolve relative paths for root object
     * @param resourceBundle The resources used to localize the root object
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCustId.setDisable(true);
        updateCustCountryCombo.setItems(Countries.allCountries);
        updateCustDivision.setItems(FirstLevelDivisions.selectedDivisions);
    }

    /** Navigates to the ViewCustomersController when OK is selected on the dialog prompt.
     *
     * Canceling will stay on the current page
     * @param event on action cancel button
     * @throws IOException from FXMLLoader in the event of an error loading the ViewCustomersController
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

    /** Generates First Level Divisions of the selected Country in the Country combo box.
     *
     * Uses the selectDivisionFromCountry() method from the FirstLevelDivisionsDao to query the First Level Divisions of the selected Country
     * @param event on action country selected from combo box
     * */
    @FXML
    void onActionCountrySelected(ActionEvent event) {
        FirstLevelDivisions.clearDivisions();
        Countries selectedCountry = updateCustCountryCombo.getValue();

        try {
            FirstLevelDivisionsDao.selectDivisionFromCountry(CountriesDao.getCountryId(String.valueOf(selectedCountry)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        updateCustDivision.setItems(FirstLevelDivisions.selectedDivisions);
        updateCustDivision.getSelectionModel().selectFirst();
    }

    /** Updates the customer information in the database.
     *
     * There is an error check for any empty fields when attempting to submit the customer information
     * Navigates to the ViewCustomersController on success
     * @param event on action update customer button
     * */
    @FXML
    void onActionUpdateCustomer(ActionEvent event) {
        if (updateCustName.getText().isEmpty() || updateCustAddress.getText().isEmpty() || updateCustPostalCode.getText().isEmpty() ||
                updateCustDivision.getValue() == null || updateCustCountryCombo.getValue() == null) {
            errorAlert.setContentText("ERROR: One or more fields are empty. Please check values and try again.");
            errorAlert.showAndWait();
        } else {

            try {
                String customerName = updateCustName.getText();
                String address = updateCustAddress.getText();
                String postalCode = updateCustPostalCode.getText();
                String phone = updateCustPhone.getText();
                String customerType;
                int divisionId = updateCustDivision.getValue().getDivisionId();
                int customerId = Integer.parseInt(updateCustId.getText());
                LocalDateTime lastUpdated = LocalDateTime.now();
                String loggedInUser = Users.getLoggedInUser().getUserName();
                if(residentialRadio.isSelected()){
                    customerType = residentialRadio.getText();
                    CustomersDao.updateCustomer(customerId, customerName, address, postalCode, phone, lastUpdated, loggedInUser, divisionId, customerType);
                }else if(commercialRadio.isSelected()){
                    customerType = commercialRadio.getText();
                    CustomersDao.updateCustomer(customerId, customerName, address, postalCode, phone, lastUpdated, loggedInUser, divisionId, customerType);
                }

                informationAlert.setContentText("Customer updated successfully.");
                informationAlert.showAndWait();

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ViewCustomer.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**  Sends the selected customer information from the ViewCustomersController to the Update Customer Screen.
     *
     * @param customer the customer that will be sent to the Update Customer Screen
     * @throws SQLException in the event of getting an error when attempting to get the selected Country ID from the First Level Division
     * */
    public void sendCustomer(Customers customer) throws SQLException {
        updateCustId.setText(String.valueOf(customer.getCustomerId()));
        updateCustName.setText(customer.getCustomerName());
        updateCustAddress.setText(customer.getAddress());
        updateCustPostalCode.setText(customer.getPostalCode());
        updateCustPhone.setText(customer.getPhone());
        int selectedCountryID = FirstLevelDivisionsDao.getCountryIdFromDivision(customer.getDivisionId());

        try {
            ObservableList<FirstLevelDivisions> tempDivisions = FirstLevelDivisions.selectedDivisions;
            FirstLevelDivisionsDao.selectDivisionFromCountry(selectedCountryID);
            for(FirstLevelDivisions division :tempDivisions) {
               if(division.getDivisionId() == customer.getDivisionId()) {
                   updateCustDivision.setValue(division);
                   break;
               }
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
        try {
            ObservableList<Countries> allCountries = Countries.allCountries;
            for (Countries country : allCountries) {
                if (country.getCountryId() == selectedCountryID) {
                    updateCustCountryCombo.setValue(country);
                }
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if(customer instanceof ResidentialCustomer residentialCustomer){
            residentialRadio.setSelected(true);
            customerTypeTxtArea.setText("Lawn Care Package: " + residentialCustomer.getLawnCarePackage());
        }else if(customer instanceof CommercialCustomer commercialCustomer){
            commercialRadio.setSelected(true);
            customerTypeTxtArea.setText("Contract Type: " + commercialCustomer.getContractType());
        }
    }

    @FXML
    void onActionCommercialSelected(ActionEvent event) {
        residentialRadio.setSelected(false);
        customerTypeTxtArea.setVisible(false);

    }
    @FXML
    void onActionResidentialSelected(ActionEvent event) {
        commercialRadio.setSelected(false);
        customerTypeTxtArea.setVisible(false);
    }

}
