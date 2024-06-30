package controller;

import com.bd.Application;
import dao.AppointmentsDao;
import dao.CustomersDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Countries;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ResourceBundle;

/** The ReportViewerController is used to display the following reports: Appointment Count by Month and Type, Appointment Count by Country, and Contact Appointments.
 *
 * @author Brandi Davis
 * */
public class ReportViewerController implements Initializable {
    @FXML
    private TextField apptMonthTypeReportTxt;
    @FXML
    private ComboBox<String> apptTypeCombo;
    @FXML
    private ComboBox<String> monthCombo;
    @FXML
    private ComboBox<Contacts> contactCombo;
    @FXML
    private TableColumn<Integer, Appointments> apptIdCol;
    @FXML
    private TableView<Appointments> contactApptTable;
    @FXML
    private TableColumn<Integer, Appointments> customerIdCol;
    @FXML
    private TableColumn<String, Appointments> descriptionCol;
    @FXML
    private TableColumn<LocalDateTime, Appointments> endCol;
    @FXML
    private TableColumn<LocalDateTime, Appointments> startCol;
    @FXML
    private TableColumn<String, Appointments> titleCol;
    @FXML
    private TableColumn<String, Appointments> typeCol;
    @FXML
    private TextField countCountryTxt;
    @FXML
    private ComboBox<Countries> countryCombo;
    @FXML
    private ComboBox<String> customerTypeCombo;
    @FXML
    private TextField customerTypeTxt;
    @FXML
    private Button contactScheduleBtn;
    /** An Observable list that holds the months of the year.  */
    private static final ObservableList<String> allMonths = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    /** Initializes the ReportViewerController.
     *
     * The lambda will generate the contact schedule by selection the button to generate the report for the selected contact
     * Sets the cell value data for the Contact appointment Tableview
     * Generates a list of appointment types for the Appointment Count by Month and Type report
     * Sets the combo box data for appointment type, month, contact, and country
     * @param url The location used to resolve relative paths for root object
     * @param resourceBundle The resources used to localize the root object
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactScheduleBtn.setOnAction((event) ->{
            if(contactCombo.getSelectionModel().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("A contact must be selected.");
                alert.showAndWait();
                return;
            }

            int selectedContactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();
            try{
                AppointmentsDao.SelectAppointmentByContact(selectedContactId);
            }catch(Exception e){
                System.out.println(e.getMessage());
                return;
            }
            if(Appointments.filteredAppointments.size() > 0){
                contactApptTable.setItems(Appointments.filteredAppointments);
            } else{
                contactApptTable.setPlaceholder(new Label("No results."));
            }
        });

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));

        contactApptTable.setPlaceholder(new Label("Run report to see results."));

        try{
            AppointmentsDao.SelectDistinctApptType();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            CustomersDao.SelectDistinctCustomerType();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        apptTypeCombo.setItems(Appointments.appointmentTypes);
        customerTypeCombo.setItems(Customers.customerTypes);
        monthCombo.setItems(allMonths);
        contactCombo.setItems(Contacts.allContacts);
        countryCombo.setItems(Countries.allCountries);
    }

    /** Generates the Appointment Count by Month and Type report.
     *
     * This method uses a few method from the AppointmentsDao to query the count of the appointments by month and type
     * Clears any prior entries in the text field
     * A message will be displayed in the text field if no results are found
     * @param event on action generate report results button
     * */
    @FXML
    void onActionGenerateMonthType(ActionEvent event) {
        apptMonthTypeReportTxt.clear();

        if(monthCombo.getSelectionModel().isEmpty() || apptTypeCombo.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The type and month must be selected.");
            alert.showAndWait();
            return;
        }

        String selectedMonth = monthCombo.getSelectionModel().getSelectedItem().toUpperCase();
        int monthInt = Month.valueOf(selectedMonth).getValue();
        String selectedType = apptTypeCombo.getSelectionModel().getSelectedItem();

        try{
            AppointmentsDao.SelectMonthAndType(monthInt, selectedType);
            AppointmentsDao.CountAppointmentFilter(selectedType, monthInt);
            if(Appointments.getAppointmentFilterListCount() > 0){
                apptMonthTypeReportTxt.setText(titleCase(selectedMonth) + " - " + selectedType + " - " + Appointments.getAppointmentFilterListCount());
            }else{
                apptMonthTypeReportTxt.setText("No results.");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /** Generates the appointment count from the selected country.
     *
     * Uses the SelectAppointmentCountryCount() method from AppointmentsDao to query the count by country ID
     * Displays a message if no results are found
     * @param event on action generate report results
     * */
    @FXML
    void onActionGenerateCountCountry(ActionEvent event) {
        if(countryCombo.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("A country must be selected.");
            alert.showAndWait();
            return;
        }

        try{
            int countryId = countryCombo.getSelectionModel().getSelectedItem().getCountryId();
            AppointmentsDao.SelectAppointmentCountryCount(countryId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error: Check Country.");
        }

        if(Appointments.getAppointmentFilterListCount() > 0){
            countCountryTxt.setText(countryCombo.getSelectionModel().getSelectedItem().getCountry() + " - Appointments: " + Appointments.getAppointmentFilterListCount());
        }
        else{
            countCountryTxt.setText("No results found.");
        }
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
    @FXML
    void onActionGenerateCustomerType(ActionEvent event) {
        if(customerTypeCombo.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("A customer type must be selected.");
            alert.showAndWait();
            return;
        }
        String customerType = customerTypeCombo.getSelectionModel().getSelectedItem();
        try{
            CustomersDao.CountCustomerTypeFilter(customerType);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        if(Customers.getCustomerFilterListCount() > 0 ){
            customerTypeTxt.setText((titleCase(customerType) + " Customers: " + Customers.getCustomerFilterListCount()));
        }else {
            customerTypeTxt.setText("No results found.");
        }
    }

    /** Converts a string value from whichever case to title case.
     *
     * Used in the onActionGenerateMonthType() action event
     * @param string the string that will be converted
     * */
    public static String titleCase(String string)
    {
        if(string == null || string.length()<=1){ return string;}
        else {
            return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        }
    }
}
