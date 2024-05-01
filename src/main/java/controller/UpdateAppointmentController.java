package controller;

import com.bd.Application;
import dao.AppointmentsDao;
import helper.TimeUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/** The UpdateAppointmentController is used to update the selected appointment from the ViewAppointmentsController.
 *
 * @author Brandi Davis
 * */

public class UpdateAppointmentController implements Initializable {

    @FXML
    private ComboBox<Contacts> apptContactCombo;
    @FXML
    private ComboBox<Integer> apptCustIdCombo;
    @FXML
    private TextArea apptDescTxt;
    @FXML
    private TextField apptIdTxt;
    @FXML
    private TextField apptLocTxt;
    @FXML
    private TextField apptTitleTxt;
    @FXML
    private TextField apptTypeTxt;
    @FXML
    private ComboBox<Integer> apptUserIdCombo;
    @FXML
    private DatePicker endDate;
    @FXML
    private DatePicker startDate;
    @FXML
    private ComboBox<LocalTime> endTimeCombo;
    @FXML
    private ComboBox<LocalTime> startTimeCombo;
    Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);

    //Next java doc here
    /** Initializes the UpdateAppointmentController, disables ID field, and sets combo box data for time, contact, customer, and user.
     *
     * The setConverter method is used to update the format of the time in the start time and end time combo boxes.
     * @param url The location used to resolve relative paths for root object
     * @param resourceBundle The resources used to localize the root object
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptIdTxt.setDisable(true);
        startTimeCombo.setItems(TimeUtil.businessHours());
        endTimeCombo.setItems(TimeUtil.businessHours());
        apptContactCombo.setItems(Contacts.allContacts);
        apptCustIdCombo.setItems(Customers.getCustomerIds());
        apptUserIdCombo.setItems(Users.getUserIds());

        final String timePattern = "hh:mm a";
        final DateTimeFormatter patternFormatter = DateTimeFormatter.ofPattern(timePattern);
        startTimeCombo.setConverter(new StringConverter<LocalTime>() {
            @Override
            public String toString(LocalTime localTime) {
                if(localTime == null) {
                    return "";
                }
                return patternFormatter.format(localTime);
            }
            @Override
            public LocalTime fromString(String s) {
                if(s == null || s.isEmpty()) {
                    return null;
                }
                return LocalTime.parse(s, patternFormatter);
            }
        });
        endTimeCombo.setConverter(new StringConverter<LocalTime>() {
            @Override
            public String toString(LocalTime localTime) {
                if(localTime == null) {
                    return "";
                }
                return patternFormatter.format(localTime);
            }
            @Override
            public LocalTime fromString(String s) {
                if(s == null || s.isEmpty()) {
                    return null;
                }
                return LocalTime.parse(s, patternFormatter);
            }
        });
    }
    @FXML
    void onActionCancelAppointment(ActionEvent event) throws IOException {
        confirmationAlert.setContentText("Are you sure you want to cancel and go back? All entered information will be lost.");
        confirmationAlert.showAndWait();
        if(confirmationAlert.getResult() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ViewAppointments.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }else {
            return;
        }
    }

    /** Updates the appointment information by using methods from AppointmentsDao and Appointment class. Returns to the ViewAppointmentsController.
     *
     * There are multiple error checks for empty fields and incorrect time inputs. Uses the hasAppointment() method from AppointmentsDao and isOverlap method from Appointments class to check for customer appointment overlap.
     * Uses updateAppointment() method from AppointmentsDao class.
     * @param event on action update button
     * @throws IOException from FXMLLoader in the event of an error loading the ViewAppointmentsController
     * */
    @FXML
    void onActionUpdateAppointment(ActionEvent event) throws IOException {

        try {

            LocalDateTime appointmentStart = LocalDateTime.of(startDate.getValue(), startTimeCombo.getValue());
            LocalDateTime appointmentEnd = LocalDateTime.of(endDate.getValue(), endTimeCombo.getValue());
            LocalDateTime lastUpdate = LocalDateTime.now();

            int id = Integer.parseInt(apptIdTxt.getText());
            String title = apptTitleTxt.getText();
            String description = apptDescTxt.getText();
            String location = apptLocTxt.getText();
            String type = apptTypeTxt.getText();
            String lastUpdatedBy = Users.getLoggedInUser().getUserName();
            int customerId = apptCustIdCombo.getValue();
            int userId = apptUserIdCombo.getValue();
            int contactId = apptContactCombo.getValue().getContactId();
            boolean hasAppointment = false;

            if(AppointmentsDao.hasAppointment(customerId)){
                for(Appointments appointment: Appointments.getAllAppointments()){
                    if((appointment.getCustomerId() == customerId) && (appointment.getAppointmentId() != id)){
                        if(Appointments.isOverlap(customerId, appointment.getStart(),appointment.getEnd(), appointmentStart, appointmentEnd)) {
                            System.out.println("There is an overlap.");
                            errorAlert.setAlertType(Alert.AlertType.ERROR);
                            errorAlert.setContentText("Unable to add appointment for customer due to overlap with another appointment.");
                            errorAlert.showAndWait();
                            hasAppointment = true;
                            break;
                        }
                        else{
                            System.out.println("No overlap.");
                        }
                    }
                }
            }
            if(!hasAppointment) {


                if (apptTitleTxt.getText().isEmpty() || apptDescTxt.getText().isEmpty() || apptTypeTxt.getText().isEmpty() || apptLocTxt.getText().isEmpty()) {
                    errorAlert.setContentText("One or more fields are empty.");
                    errorAlert.showAndWait();
                    return;
                }
                if (apptContactCombo.getSelectionModel().isEmpty() || apptCustIdCombo.getSelectionModel().isEmpty() || apptUserIdCombo.getSelectionModel().isEmpty() ||
                        endTimeCombo.getSelectionModel().isEmpty() || startTimeCombo.getSelectionModel().isEmpty() || startDate.getValue() == null || endDate.getValue() == null) {
                    errorAlert.setContentText("One or more selections are empty.");
                    errorAlert.showAndWait();
                    return;
                }
                if(appointmentEnd.isBefore(appointmentStart)){
                    errorAlert.setContentText("The end time cannot be before the start time.");
                    errorAlert.showAndWait();
                    return;
                }
                if(appointmentStart.isAfter(appointmentEnd)){
                    errorAlert.setContentText("The start time cannot be after the end time.");
                    errorAlert.showAndWait();
                    return;
                }
                AppointmentsDao.UpdateAppointment(id, title, description, location, type, appointmentStart, appointmentEnd, lastUpdate, lastUpdatedBy
                        , customerId, userId, contactId);

                infoAlert.setContentText("Appointment updated successfully.");
                infoAlert.showAndWait();

                Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ViewAppointments.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            }
    } catch(Exception e){
        System.out.println(e.getMessage());
        }
    }

    /**  Sends the selected appointment information from the ViewAppointmentController to the Update Appointment Screen.
     *
     * @param appointment the appointment that will be sent to the Update Appointment Screen
     * */
    public void SendAppointment(Appointments appointment) {

        /*ZonedDateTime apptStartUTC = ZonedDateTime.of(appointment.getStart().toLocalDate(), appointment.getStart().toLocalTime(), ZoneId.of("UTC"));
        ZonedDateTime apptEndUTC = ZonedDateTime.of(appointment.getEnd().toLocalDate(), appointment.getEnd().toLocalTime(), ZoneId.of("UTC"));
        ZonedDateTime apptStartLocal = apptStartUTC.withZoneSameInstant(TimeUtil.getLocalZoneId());
        ZonedDateTime apptEndLocal = TimeUtil.ToLocal(apptEndUTC);*/

        LocalDateTime appointmentStart = LocalDateTime.of(appointment.getStart().toLocalDate(), appointment.getStart().toLocalTime());
        LocalDateTime appointmentEnd = LocalDateTime.of(appointment.getEnd().toLocalDate(), appointment.getEnd().toLocalTime());

        apptIdTxt.setText(String.valueOf(appointment.getAppointmentId()));
        apptTitleTxt.setText(appointment.getTitle());
        apptDescTxt.setText(appointment.getDescription());
        apptLocTxt.setText(appointment.getLocation());
        apptTypeTxt.setText(appointment.getType());
        startDate.setValue(appointmentStart.toLocalDate());
        startTimeCombo.setValue(appointmentStart.toLocalTime());
        endDate.setValue(appointmentEnd.toLocalDate());
        endTimeCombo.setValue(appointmentEnd.toLocalTime());
        apptCustIdCombo.setValue(appointment.getCustomerId());
        apptUserIdCombo.setValue(appointment.getUserId());

        for(Contacts contact : Contacts.allContacts) {
            if(appointment.getContactId() == contact.getContactId()){
                apptContactCombo.setValue(contact);
            }
        }
    }

}
