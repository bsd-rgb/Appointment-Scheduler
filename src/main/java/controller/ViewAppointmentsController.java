package controller;

import com.bd.Application;
import dao.AppointmentsDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ResourceBundle;

public class ViewAppointmentsController implements Initializable {

    @FXML
    private RadioButton allAppointments;
    @FXML
    private ToggleGroup appointmentFilterToggle;
    @FXML
    private RadioButton appointmentMonth;
    @FXML
    private RadioButton appointmentWeek;
    @FXML
    private TableView<Appointments> appointmentTable;
    @FXML
    private TableColumn<String, Contacts> apptContactCol;
    @FXML
    private TableColumn<Integer, Appointments> apptCustomerIdCol;
    @FXML
    private TableColumn<String, Appointments> apptDescCol;
    @FXML
    private TableColumn<LocalDateTime, Appointments> apptEndCol;
    @FXML
    private TableColumn<LocalDateTime, Appointments> apptStartCol;
    @FXML
    private TableColumn<Integer, Appointments> apptIdCol;
    @FXML
    private TableColumn<String, Appointments> apptLocationCol;
    @FXML
    private TableColumn<String, Appointments> apptTitleCol;
    @FXML
    private TableColumn<String, Appointments> apptTypeCol;
    @FXML
    private TableColumn<Integer, Appointments> apptUserIdCol;

    private ObservableList<Appointments> filteredAppointments = FXCollections.observableArrayList();
    Alert viewAppointmentsAlert = new Alert(Alert.AlertType.NONE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        allAppointments.setSelected(true);
        try {
            AppointmentsDao.SelectAppointments();

        }catch(Exception e) {
            System.out.println("Error getting appointments. " + e.getMessage());
        }
        appointmentTable.setItems(Appointments.getAllAppointments());
    }
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("AddAppointment.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void onActionDeleteAppointment(ActionEvent event) {

        Appointments selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        viewAppointmentsAlert.setAlertType(Alert.AlertType.CONFIRMATION);
        viewAppointmentsAlert.setContentText("Are you sure you want to delete the selected appointment?");
        ((Button) viewAppointmentsAlert.getDialogPane().lookupButton(ButtonType.OK)).setText("Delete");
        viewAppointmentsAlert.showAndWait();
        if(viewAppointmentsAlert.getResult().equals(ButtonType.OK)) {

            try {
                AppointmentsDao.DeleteAppointment(selectedAppointment.getAppointmentId());
                AppointmentsDao.SelectAppointments();
                appointmentTable.setItems(Appointments.getAllAppointments());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            viewAppointmentsAlert.setAlertType(Alert.AlertType.INFORMATION);
            viewAppointmentsAlert.setContentText("Appointment ID " + selectedAppointment.getAppointmentId() + " of Type " + selectedAppointment.getType() + " has been deleted.");
            viewAppointmentsAlert.showAndWait();

        } else {
            return;
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

    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Application.class.getResource("UpdateAppointment.fxml"));
        loader.load();

        UpdateAppointmentController updateAppointmentController = loader.getController();
        updateAppointmentController.SendAppointment(appointmentTable.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    void appointmentFilter() {

        filteredAppointments.clear();

        LocalDate currentDate = LocalDate.now();
        DayOfWeek startDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        DayOfWeek lastDayOfWeek = startDayOfWeek.plus(6);
        LocalDate startCurrentWeek = currentDate.with(TemporalAdjusters.previousOrSame(startDayOfWeek));
        LocalDate endCurrentWeek = currentDate.with(TemporalAdjusters.nextOrSame(lastDayOfWeek));

        System.out.println("First Day of week: " + startDayOfWeek);
        System.out.println("Current Week (Start): " + startCurrentWeek);
        System.out.println("Current Week (End): " + endCurrentWeek);

        if(allAppointments.isSelected()){
            System.out.println("All appointments selected.");
            appointmentTable.setItems(Appointments.getAllAppointments());
        }

     for(Appointments appointments: Appointments.getAllAppointments()){
         if(appointmentWeek.isSelected()){
             if(appointments.getStart().isAfter(startCurrentWeek.atStartOfDay())){
                 filteredAppointments.add(appointments);
                 appointmentTable.setItems(filteredAppointments);
             }
         }
         if(appointmentMonth.isSelected()){
             if(appointments.getStart().getMonth().equals(currentDate.getMonth())) {
                 filteredAppointments.add(appointments);
                 appointmentTable.setItems(filteredAppointments);
             }
         }
     }
    }





/*        System.out.println("First Day of week: " + startDayOfWeek);
        System.out.println("Current Week (Start): " + startCurrentWeek);
        System.out.println("Current Week (End): " + endCurrentWeek);*/
/*        for (Appointments appointment : Appointments.getAllAppointments()) {
            if (appointmentFilterToggle.getSelectedToggle() == appointmentMonth) {

                if (appointment.getStart().getMonth().equals(currentDate.getMonth())) {
                    System.out.println("There are appointment this month: " + currentDate.getMonth());
                    filteredAppointments.add(appointment);
                    appointmentTable.setItems(filteredAppointments);

                } else if (appointmentFilterToggle.getSelectedToggle().isSelected(app)) {

                    System.out.println("Appointment week selected.");

                    System.out.println("Appointment Week Test: " + appointment.getStart().toLocalDate().with(startCurrentWeek));
                   *//* if (appointment.getStart().isAfter(startCurrentWeek.atStartOfDay()) && appointment.getStart().isBefore(endCurrentWeek.atTime(23, 0))) {
                        System.out.println("The appointment (" + appointment.getAppointmentId() + ") is within the current week.");
                        filteredAppointments.add(appointment);
                        appointmentTable.setItems(filteredAppointments);*//*

                    }
                    if (appointmentFilterToggle.getSelectedToggle() == allAppointments) {
                        appointmentTable.setItems(Appointments.getAllAppointments());
                    }
                }
            }*/




    @FXML
    void onActionRadioSelected(ActionEvent event) {

        appointmentFilter();

    }
}
