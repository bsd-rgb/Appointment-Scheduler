package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Countries;
import model.FirstLevelDivisions;

import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateCustId.setDisable(true);

    }



    @FXML
    void onActionCancel(ActionEvent event) {

    }

    @FXML
    void onActionCountrySelected(ActionEvent event) {

    }

    @FXML
    void onActionUpdateCustomer(ActionEvent event) {

    }


}
