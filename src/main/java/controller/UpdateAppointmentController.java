package controller;

import com.bd.Application;
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

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        apptIdTxt.setDisable(true);

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

    }

    public void SendAppointment(Appointments appointment) {

        apptIdTxt.setText(String.valueOf(appointment.getAppointmentId()));
        apptTitleTxt.setText(appointment.getTitle());
        apptDescTxt.setText(appointment.getDescription());
        apptLocTxt.setText(appointment.getLocation());
        apptTypeTxt.setText(appointment.getType());
        startDate.setValue(appointment.getStart().toLocalDate());
        startTimeCombo.setValue(appointment.getStart().toLocalTime());
        endTimeCombo.setValue(appointment.getEnd().toLocalTime());
        endDate.setValue(appointment.getEnd().toLocalDate());
        apptCustIdCombo.setValue(appointment.getCustomerId());
        apptUserIdCombo.setValue(appointment.getUserId());

        for(Contacts contact : Contacts.allContacts) {
            if(appointment.getContactId() == contact.getContactId()){
                apptContactCombo.setValue(contact);
            }
        }
    }

}
