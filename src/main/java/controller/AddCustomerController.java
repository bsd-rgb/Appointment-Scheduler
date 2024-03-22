package controller;

import com.bd.Application;
import dao.CountriesDao;
import dao.CustomersDao;
import dao.FirstLevelDivisionsDao;
import dao.UsersDao;
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



public class AddCustomerController implements Initializable {

    Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryCombo.setItems(Countries.allCountries);

    }

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

    @FXML
    void onActionAddCustomer(ActionEvent event) {

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
            System.out.println("DivisionID: " + divisionId + " " + divisionCombo.getValue());

            //dialog box that
            informationAlert.setContentText("Customer successfully added.");
            informationAlert.showAndWait();
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ViewCustomer.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println("Error inserting customer.");
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void onActionCountrySelected(ActionEvent event)  {
        divisionCombo.setValue(null);
        FirstLevelDivisions.clearDivisions();
        Countries selectedCountry = countryCombo.getValue();
        System.out.println(selectedCountry);

        try {
            FirstLevelDivisionsDao.selectDivisionFromCountry(CountriesDao.getCountryId(String.valueOf(selectedCountry)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        divisionCombo.getSelectionModel().selectFirst();
        divisionCombo.setItems(FirstLevelDivisions.selectedDivisions);
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

}
