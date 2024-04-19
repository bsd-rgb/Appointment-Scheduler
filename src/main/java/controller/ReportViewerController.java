package controller;

import dao.AppointmentsDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Appointments;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

public class ReportViewerController implements Initializable {

    @FXML
    private TextField apptMonthTypeReportTxt;

    @FXML
    private ComboBox<String> apptTypeCombo;

    @FXML
    private ComboBox<String> monthCombo;


    private static final ObservableList<String> allMonths = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            AppointmentsDao.SelectDistinctApptType();
            System.out.println(Appointments.appointmentTypes);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        apptTypeCombo.setItems(Appointments.appointmentTypes);
        monthCombo.setItems(allMonths);
    }

    @FXML
    void onActionGenerateReportOne(ActionEvent event) {
        int tempCount = 0;

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
    public static String titleCase(String string)
    {
        if(string == null || string.length()<=1){ return string;}
        else {
            return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        }
    }

}
