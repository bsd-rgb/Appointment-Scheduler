package controller;

import com.bd.Application;
import dao.CountriesDao;
import dao.FirstLevelDivisionsDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCustId.setDisable(true);
        updateCustCountryCombo.setItems(Countries.allCountries);
        updateCustDivision.setItems(FirstLevelDivisions.selectedDivisions);
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        //Create confirmation dialog
        //confirmationAlert.setContentText("Cancel this customer update and go back?");

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ViewCustomer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void onActionCountrySelected(ActionEvent event) {

    }

    @FXML
    void onActionUpdateCustomer(ActionEvent event) {

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
                   System.out.println("Match found! " + division.getDivisionId());
                   updateCustDivision.setValue(tempDivisions.get(division.getDivisionId()));
               }
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
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
