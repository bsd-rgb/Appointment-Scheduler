package controller;

import dao.CountriesDao;
import dao.FirstLevelDivisionsDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;

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

    public void sendCustomer(Customers customer) throws SQLException {

        updateCustId.setText(String.valueOf(customer.getCustomerId()));
        updateCustName.setText(customer.getCustomerName());
        updateCustAddress.setText(customer.getAddress());
        updateCustPostalCode.setText(customer.getPostalCode());
        updateCustPhone.setText(customer.getPhone());
        //to get the division I'll need to pass the selected customer object
        try {
            updateCustDivision.setValue(FirstLevelDivisions.selectedDivisions.get(customer.getDivisionId()));
            //updateCustDivision.setValue();
            //Maybe try looping through the selected division list to match the division ID to the customer?
        }catch (Exception e) {
            System.out.println("Error with getting the division ID in combobox");
            System.out.println(e.getMessage());

        }

        //to get the Country data I'll need to take the first level division and get the Country from that
        //Function that takes the division ID and turns it into country Data
        //get the division ID
        //query against first level divisions to get the country ID
        //then I can select all countries(from CountriesDao, put that in an observable list and then loop through to to match the country ID
        //kind of like the Auto gen part ID
        try {
            ObservableList<Countries> allCountries = Countries.allCountries;
            int selectedCountryID = FirstLevelDivisionsDao.getCountryIdFromDivision(customer.getDivisionId());
            for (Countries country : allCountries) {

                if (country.getCountryId() == selectedCountryID) {

                    updateCustCountryCombo.setValue(country);
                } else {
                    updateCustCountryCombo.setValue(null);
                }
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }




        /*Here's what I did in the other project:
        *
        *
        *  public void SendPart(Part part) {
            modPartIDTxt.setText(String.valueOf(part.getId()));
            modPartNameTxt.setText(part.getName());
            modPartInvTxt.setText(String.valueOf(part.getStock()));
            modPartPriceTxt.setText(String.valueOf(part.getPrice()));
            modPartMinTxt.setText(String.valueOf(part.getMin()));
            modPartMaxTxt.setText(String.valueOf(part.getMax()));

            if(part instanceof InHouse) {

               modinHouseRadio.setSelected(true);
                modPartMachineId.setText(String.valueOf(((InHouse) part).getMachineId()));
                companyMachineVisibility();

            } else if(part instanceof Outsourced) {
                modOutsourcedRadio.setSelected(true);
                modPartCompanyName.setText(((Outsourced) part).getCompanyName());
                companyMachineVisibility();
            }
            *
            *
            * void onActionModifyPart(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Application.class.getResource("Modify_Part_Screen.fxml"));
        loader.load();

        ModifyPartController modifyPartController = loader.getController();
        modifyPartController.SendPart(partsTable.getSelectionModel().getSelectedItem());


        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }
            *
            *
            * */


    }


}
