package controller;

import com.bd.Application;
import dao.AppointmentsDao;
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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ResourceBundle;

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
    private Button contactScheduleBtn;


    private static final ObservableList<String> allMonths = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        contactScheduleBtn.setOnAction((event) ->{
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
            System.out.println(Appointments.appointmentTypes);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        apptTypeCombo.setItems(Appointments.appointmentTypes);
        monthCombo.setItems(allMonths);
        contactCombo.setItems(Contacts.allContacts);
        countryCombo.setItems(Countries.allCountries);
    }



    @FXML
    void onActionGenerateReportOne(ActionEvent event) {

        //clear any prior entries in the text area
        apptMonthTypeReportTxt.clear();

        //capture the contents of the combo boxes
        String selectedMonth = monthCombo.getSelectionModel().getSelectedItem().toUpperCase();
        int monthInt = Month.valueOf(selectedMonth).getValue();
        String selectedType = apptTypeCombo.getSelectionModel().getSelectedItem();

        System.out.println("Month Int: " + monthInt);

        //run a sql query to get the count of the appointments with the specific appointment type and month
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
            System.out.println("Selected Month: " + selectedMonth);
            System.out.println("Selected Month w Function: "+ titleCase(selectedMonth));
            System.out.println("Type selected: "  + selectedType);
            System.out.println("TEST: " +titleCase(selectedMonth) + " - " + selectedType + " - " + Appointments.getAppointmentFilterListCount());
    }



/*    @FXML
    void onActionGenerateContactSchedule(ActionEvent event) {

        //get the contact from the combo box
        int selectedContactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();
        //query the Appointment DB for appoints with the selected contact ID
        try{
            AppointmentsDao.SelectAppointmentByContact(selectedContactId);

        }catch (Exception e){
            System.out.println(e.getMessage());

            return;
        }

        if(Appointments.filteredAppointments.size() > 0){

            contactApptTable.setItems(Appointments.filteredAppointments);

        } else{
            contactApptTable.setPlaceholder(new Label("No results."));
        }

    }*/

    @FXML
    void onActionGenerateCountCountry(ActionEvent event) {

        try{
            int countryId = countryCombo.getSelectionModel().getSelectedItem().getCountryId();
            AppointmentsDao.SelectAppointmentCountryCount(countryId);

        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error: Check Country.");
        }

        if(Appointments.getAppointmentFilterListCount() > 0){
            countCountryTxt.setText(countryCombo.getSelectionModel().getSelectedItem().getCountry() + " - Appoinments: " + Appointments.getAppointmentFilterListCount());
        }
        else{
            countCountryTxt.setText("No results found.");
        }

    }

    @FXML
    void onActionGoBack(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("NavigationScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }
    public static String titleCase(String string)
    {
        if(string == null || string.length()<=1){ return string;}
        else {
            return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        }
    }

}
