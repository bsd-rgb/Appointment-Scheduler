package controller;

import helper.TimeUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Contacts;
import model.Customers;
import model.Users;

import java.net.URL;
import java.time.LocalTime;
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
    private ComboBox<Customers> apptCustIdCombo;

    @FXML
    private TextField apptLocTxt;

    @FXML
    private TextField apptTitleTxt;

    @FXML
    private TextField apptTypeTxt;

    @FXML
    private ComboBox<Users> apptUserIdCombo;

    @FXML
    private ComboBox<?> endAmPmCombo;

    @FXML
    private DatePicker endDate;

    @FXML
    private ComboBox<LocalTime> endHourCombo;

    @FXML
    private ComboBox<LocalTime> endMinuteCombo;

    @FXML
    private ComboBox<?> startAmPmCombo;

    @FXML
    private DatePicker startDate;

    @FXML
    private ComboBox<LocalTime> startHourCombo;

    @FXML
    private ComboBox<LocalTime> startMinuteCombo;

    @FXML
    void onActionAddAppointment(ActionEvent event) {

    }

    @FXML
    void onActionCancelAppointment(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        startHourCombo.setItems(TimeUtil.businessHours());
        startHourCombo.getSelectionModel().selectFirst();

    }
}
