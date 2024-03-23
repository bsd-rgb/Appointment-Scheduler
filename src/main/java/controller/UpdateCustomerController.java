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
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

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
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCustId.setDisable(true);
        updateCustCountryCombo.setItems(Countries.allCountries);
        updateCustDivision.setItems(FirstLevelDivisions.selectedDivisions);
    }

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

    @FXML
    void onActionCountrySelected(ActionEvent event) {

        FirstLevelDivisions.clearDivisions();
        Countries selectedCountry = updateCustCountryCombo.getValue();
        System.out.println(selectedCountry);

        try {
            FirstLevelDivisionsDao.selectDivisionFromCountry(CountriesDao.getCountryId(String.valueOf(selectedCountry)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        updateCustDivision.getSelectionModel().selectFirst();
        updateCustDivision.setItems(FirstLevelDivisions.selectedDivisions);
    }

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
                int divisionId = updateCustDivision.getValue().getDivisionId();
                int customerId = Integer.parseInt(updateCustId.getText());
                LocalDateTime lastUpdated = LocalDateTime.now();
                String loggedInUser = Users.getLoggedInUser().getUserName();

                CustomersDao.updateCustomer(customerId, customerName, address, postalCode, phone, lastUpdated, loggedInUser, divisionId);
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
    }


}
