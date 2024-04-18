package controller;

import dao.AppointmentsDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Appointments;

import java.net.URL;
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
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        apptTypeCombo.setItems(Appointments.appointmentTypes);
        monthCombo.setItems(allMonths);
        monthCombo.getSelectionModel().selectFirst();

    }


}
