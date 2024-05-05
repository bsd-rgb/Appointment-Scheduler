package controller;

import com.bd.Application;
import dao.AppointmentsDao;
import dao.ContactsDao;
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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


/** The AddAppointmentController is used add appointments to the database. 
 *
 * @author Brandi Davis
 * */
public class AddAppointmentController implements Initializable {
    @FXML
    private TextArea apptDescTxt;
    @FXML
    private ComboBox<Contacts> apptContactCombo;
    @FXML
    private ComboBox<Integer> apptCustIdCombo;
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
    private ComboBox<LocalTime> startTimeCombo;
    @FXML
    private ComboBox<LocalTime> endTimeCombo;
    Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
    Alert addAppointmentAlert = new Alert(Alert.AlertType.NONE);

    /** Initializes the AddAppointmentController.
     *
     * Sets the items in the time, userId, customerId, and contact combo boxes
     * The setConverter method is used to update the format of the time in the start time and end time combo boxes
     * @param url The location used to resolve relative paths for root object
     * @param resourceBundle The resources used to localize the root object
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        startTimeCombo.setItems(TimeUtil.businessHours());
        endTimeCombo.setItems(TimeUtil.businessHours());
        startTimeCombo.getSelectionModel().selectFirst();
        endTimeCombo.getSelectionModel().selectLast();
        apptContactCombo.setItems(Contacts.allContacts);
        apptCustIdCombo.setItems(Customers.getCustomerIds());
        apptUserIdCombo.setItems(Users.getUserIds());
    }

    /** Saves the appointment information by calling the insert method from the AppointmentsDao.
     *
     *  There are multiple error alerts for invalid input
     *  Performs verification check for overlapping appointment(s) when attempting to save
     *  On success, navigates back to the ViewAppointmentsController
     * @param event on action save appointment button
     * @throws IOException from FXMLLoader in the event of an error loading the ViewAppointmentsController
     * */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        try{
            String contactName = apptContactCombo.getValue().getContactName();

            LocalDateTime appointmentStart = LocalDateTime.of(startDate.getValue(), startTimeCombo.getValue());
            LocalDateTime appointmentEnd = LocalDateTime.of(endDate.getValue(), endTimeCombo.getValue());
            LocalDateTime lastUpdated = LocalDateTime.now();
            LocalDateTime createDate = LocalDateTime.now();

            String title = apptTitleTxt.getText();
            String description = apptDescTxt.getText();
            String location = apptLocTxt.getText();
            String type = apptTypeTxt.getText();
            int contactId = ContactsDao.getContactIdFromName(contactName);
            int customerId = apptCustIdCombo.getValue();
            int userId = apptUserIdCombo.getValue();
            String loggedInUser = Users.getLoggedInUser().getUserName();
            boolean hasAppointment = false;

            if(AppointmentsDao.hasAppointment(customerId)){
                for(Appointments appointment: Appointments.getAllAppointments()){
                    if(appointment.getCustomerId() == customerId){
                        if(Appointments.isOverlap(customerId, appointment.getStart(),appointment.getEnd(), appointmentStart, appointmentEnd)) {
                            addAppointmentAlert.setAlertType(Alert.AlertType.ERROR);
                            addAppointmentAlert.setContentText("Unable to add appointment for customer due to overlap with another appointment.");
                            addAppointmentAlert.showAndWait();
                            hasAppointment = true;
                            break;
                        }
                    }
                }
            }

            if(!hasAppointment) {

                if (apptTitleTxt.getText().isEmpty() || apptDescTxt.getText().isEmpty() || apptTypeTxt.getText().isEmpty() || apptLocTxt.getText().isEmpty()) {
                    addAppointmentAlert.setAlertType(Alert.AlertType.ERROR);
                    addAppointmentAlert.setContentText("One or more fields are empty.");
                    addAppointmentAlert.showAndWait();
                    return;
                }
                if (apptContactCombo.getSelectionModel().isEmpty() || apptCustIdCombo.getSelectionModel().isEmpty() || apptUserIdCombo.getSelectionModel().isEmpty() ||
                        endTimeCombo.getSelectionModel().isEmpty() || startTimeCombo.getSelectionModel().isEmpty() || startDate.getValue() == null || endDate.getValue() == null) {
                    addAppointmentAlert.setAlertType(Alert.AlertType.ERROR);
                    addAppointmentAlert.setContentText("One or more selections are empty.");
                    addAppointmentAlert.showAndWait();
                    return;
                }

                if(appointmentEnd.isBefore(appointmentStart)){
                    addAppointmentAlert.setAlertType(Alert.AlertType.ERROR);
                    addAppointmentAlert.setContentText("The end time cannot be before the start time.");
                    addAppointmentAlert.showAndWait();
                    return;
                }

                if(appointmentStart.isAfter(appointmentEnd)){
                    addAppointmentAlert.setAlertType(Alert.AlertType.ERROR);
                    addAppointmentAlert.setContentText("The start time cannot be after the end time.");
                    addAppointmentAlert.showAndWait();
                    return;
                }

                AppointmentsDao.insertAppointment(title, description, location, type, appointmentStart, appointmentEnd, createDate, loggedInUser
                    ,lastUpdated,loggedInUser,customerId,userId,contactId);

                informationAlert.setContentText("Appointment added successfully!");
                informationAlert.showAndWait();
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ViewAppointments.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            }

        }catch (Exception e) {
            System.out.println("Error: adding appointment.");
            System.out.println(e.getMessage());
        }

    }

    /** Displays confirmation dialog and either stays on the AddAppointmentController or navigates to ViewAppointmentsController.
     *
     * @param event on action cancel button
     * @throws IOException from FXMLLoader in the event of an error loading the ViewAppointmentsController
     * */
    @FXML
    void onActionCancelAppointment(ActionEvent event) throws IOException {
        addAppointmentAlert.setAlertType(Alert.AlertType.CONFIRMATION);
        addAppointmentAlert.setContentText("Are you sure you want to cancel and go back? All entered information will be lost.");
        addAppointmentAlert.showAndWait();
        if(addAppointmentAlert.getResult() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ViewAppointments.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }else {
            return;
        }
    }


    /** When the start date is selected, it automatically sets the end date to the same date.
     *
     * @param event on action start date selected
     * */
    @FXML
    void onActionStartDateSelected(ActionEvent event) {
        endDate.setValue(startDate.getValue());
    }
}
