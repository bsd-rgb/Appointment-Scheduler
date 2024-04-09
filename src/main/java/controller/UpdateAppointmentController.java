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
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

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

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ViewAppointments.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void onActionUpdateAppointment(ActionEvent event) {

    try {
        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();
        LocalTime startTime = startTimeCombo.getValue();
        LocalTime endTime = endTimeCombo.getValue();
        ZonedDateTime apptStartUTC = ZonedDateTime.of(start, startTime, ZoneId.of("UTC"));
        ZonedDateTime apptEndUTC = ZonedDateTime.of(end, endTime, ZoneId.of("UTC"));
        ZonedDateTime lastUpdatedUTC = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));

        /*Do something similar for the time:
        *
        *
        * LocalDateTime appointmentStart = LocalDateTime.of(apptDateStart, apptStartTime);
            LocalDateTime appointmentEnd = LocalDateTime.of(apptDateEnd, apptEndTime);
            ZonedDateTime startUTC = TimeUtil.localToUTC(appointmentStart, zoneId);
            ZonedDateTime endUTC = TimeUtil.localToUTC(appointmentEnd, zoneId);
            Timestamp startTimestampUTC = Timestamp.valueOf(startUTC.toLocalDateTime());
            Timestamp endTimestampUTC = Timestamp.valueOf(endUTC.toLocalDateTime());*/

        int id = Integer.parseInt(apptIdTxt.getText());
        String title = apptTitleTxt.getText();
        String description = apptDescTxt.getText();
        String location = apptLocTxt.getText();
        String type = apptTypeTxt.getText();
        Timestamp startTimestampUTC = Timestamp.valueOf(apptStartUTC.toLocalDateTime());
        Timestamp endTimestampUTC = Timestamp.valueOf(apptEndUTC.toLocalDateTime());
        Timestamp lastUpdatedTimestamp = Timestamp.valueOf(lastUpdatedUTC.toLocalDateTime());
        String lastUpdatedBy = Users.getLoggedInUser().getUserName();
        int customerId = apptCustIdCombo.getValue();
        int userId = apptUserIdCombo.getValue();
        int contactId = apptContactCombo.getValue().getContactId();

       AppointmentsDao.UpdateAppointment(id, title, description, location, type, startTimestampUTC, endTimestampUTC, lastUpdatedTimestamp, lastUpdatedBy
               ,customerId, userId, contactId);

       infoAlert.setContentText("Appointment updated successfully.");
       infoAlert.showAndWait();

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ViewAppointments.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    } catch(Exception e){
        System.out.println(e.getMessage());
    }









    }

    public void SendAppointment(Appointments appointment) {

        ZonedDateTime apptStartUTC = ZonedDateTime.of(appointment.getStart().toLocalDate(), appointment.getStart().toLocalTime(), ZoneId.of("UTC"));
        ZonedDateTime apptEndUTC = ZonedDateTime.of(appointment.getEnd().toLocalDate(), appointment.getEnd().toLocalTime(), ZoneId.of("UTC"));
        ZonedDateTime apptStartLocal = apptStartUTC.withZoneSameInstant(TimeUtil.getLocalZoneId());
        ZonedDateTime apptEndLocal = TimeUtil.ToLocal(apptEndUTC);

        apptIdTxt.setText(String.valueOf(appointment.getAppointmentId()));
        apptTitleTxt.setText(appointment.getTitle());
        apptDescTxt.setText(appointment.getDescription());
        apptLocTxt.setText(appointment.getLocation());
        apptTypeTxt.setText(appointment.getType());
        startDate.setValue(apptStartLocal.toLocalDate());
        startTimeCombo.setValue(apptStartLocal.toLocalTime());
        endDate.setValue(apptEndLocal.toLocalDate());
        endTimeCombo.setValue(apptEndLocal.toLocalTime());
        apptCustIdCombo.setValue(appointment.getCustomerId());
        apptUserIdCombo.setValue(appointment.getUserId());

        for(Contacts contact : Contacts.allContacts) {
            if(appointment.getContactId() == contact.getContactId()){
                apptContactCombo.setValue(contact);
            }
        }
    }

}
