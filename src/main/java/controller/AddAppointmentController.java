package controller;

import dao.CustomersDao;
import helper.TimeUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import model.Contacts;
import model.Customers;
import model.Users;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class AddAppointmentController implements Initializable {

    /*
    *
    *   LocalTime localTimeStart = LocalTime.parse(addAppointmentStartTime.getValue(), minHourFormat);
        LocalTime LocalTimeEnd = LocalTime.parse(addAppointmentEndTime.getValue(), minHourFormat);
        *
        * Check out this github for guidance:  https://github.com/Lydia-Strough/Lydia-Strough_Java-SQL_Customer-Apt/tree/master
        *
        *   startTimeComboBx.setItems(TimeManager.dynamicBusinessHoursInit(osZId, businessZId, startTime, workHours));
        * endTimeComboBx.setItems(TimeManager.dynamicBusinessHoursInit(osZId, businessZId, LocalTime.of(9, 0), workHours))
    *
    * */

    @FXML
    private TextArea appDescTxt;

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


    @FXML
    void onActionAddAppointment(ActionEvent event) {

    }

    @FXML
    void onActionCancelAppointment(ActionEvent event) {

    }

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
        apptContactCombo.setItems(Contacts.allContacts);
        apptCustIdCombo.setItems(Customers.getCustomerIds());
        apptUserIdCombo.setItems(Users.getUserIds());





    }
}
