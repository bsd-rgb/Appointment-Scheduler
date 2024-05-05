package controller;

import com.bd.Application;
import dao.AppointmentsDao;
import helper.TimeUtil;
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
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/** The ViewAppointmentsController displays appointment information in a Tableview.
 *
 * The page contains buttons to add, edit, and delete appointments.
 * @author Brandi Davis
 * */
public class ViewAppointmentsController implements Initializable {
    @FXML
    private RadioButton allAppointments;
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

    /** An Observable list that holds filtered appointment data. */
    private ObservableList<Appointments> filteredAppointments = FXCollections.observableArrayList();
    Alert viewAppointmentsAlert = new Alert(Alert.AlertType.NONE);

    /** Initializes the ViewAppointmentsController and sets the cell value data for the appointment Tableview.
     *
     * Sets the items for the appointment Tableview
     * Uses lambda functions for report filtering using the on action events for each radio button
     * @param url The location used to resolve relative paths for root object
     * @param resourceBundle The resources used to localize the root object
     * */
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

        try {
            AppointmentsDao.SelectAppointments();
        }catch(Exception e) {
            System.out.println("Error getting appointments. " + e.getMessage());
        }
        appointmentTable.setItems(Appointments.getAllAppointments());
        allAppointments.setSelected(true);

        appointmentWeek.setOnAction(event ->{
            filteredAppointments.clear();
            for(Appointments appointments: Appointments.getAllAppointments()) {
                if (appointments.getStart().isAfter(TimeUtil.getStartCurrentWeek().atStartOfDay()) && (appointments.getStart().isBefore(TimeUtil.getEndCurrentWeek().atTime(23, 59)) || appointments.getStart().isEqual((TimeUtil.getEndCurrentWeek().atTime(23, 59))))) {
                    filteredAppointments.add(appointments);
                }
            }
            appointmentTable.setItems(filteredAppointments);
        });

        allAppointments.setOnAction((event -> {
            appointmentTable.setItems(Appointments.getAllAppointments());
        }));

         appointmentMonth.setOnAction(actionEvent -> {
             filteredAppointments.clear();
             for(Appointments appointments: Appointments.getAllAppointments()){
                 if(appointments.getStart().getMonth().equals(TimeUtil.getLocalCurrentDate().getMonth())) {
                     filteredAppointments.add(appointments);
                 }
             }
             appointmentTable.setItems(filteredAppointments);
         });
    }

    /** Navigates to the AddAppointmentController.
     *
     * @param event on action add button
     * @throws IOException from FXMLLoader in the event of an error loading the AddAppointmentController
     * */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("AddAppointment.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /** Deletes the selected appointment from the database.
     *
     * Confirms deletion with confirmation prompt and updates Tableview with current information
     * Displays information dialog with the deleted appointment ID and Type
     * @param event on action delete button
     * */
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

    /** Gathers the information for the selected appointment and navigates to the UpdateAppointmentController.
     *
     * @param event on action modify button
     * @throws IOException from FXMLLoader in the event of an error loading the UpdateAppointmentController
     * */
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
}

