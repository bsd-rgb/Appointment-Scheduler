package controller;

import dao.AppointmentsDao;
import dao.ContactsDao;
import dao.CustomersDao;
import helper.TimeUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import model.Contacts;
import model.Customers;
import model.Users;

import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;


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


    @FXML
    void onActionAddAppointment(ActionEvent event) {



        //Create a method in the DAO to insert the appointment
        //Call that method here
        //Test


        try{
            LocalDate apptDateStart = startDate.getValue();
            LocalDate apptDateEnd = endDate.getValue();
            LocalTime apptStartTime = startTimeCombo.getValue();
            LocalTime apptEndTime = endTimeCombo.getValue();
            String contactName = apptContactCombo.getValue().getContactName();
            ZoneId zoneId = ZoneId.of(TimeZone.getDefault().getID());

            LocalDateTime appointmentStart = LocalDateTime.of(apptDateStart, apptStartTime);
            LocalDateTime appointmentEnd = LocalDateTime.of(apptDateEnd, apptEndTime);
            ZonedDateTime startUTC = TimeUtil.localToUTC(appointmentStart, zoneId);
            ZonedDateTime endUTC = TimeUtil.localToUTC(appointmentEnd, zoneId);
            Timestamp startTimestampUTC = Timestamp.valueOf(startUTC.toLocalDateTime());
            Timestamp endTimestampUTC = Timestamp.valueOf(endUTC.toLocalDateTime());

            
            String title = apptTitleTxt.getText();
            String description = apptDescTxt.getText();
            String location = apptLocTxt.getText();
            String type = apptTypeTxt.getText();
            int contactId = ContactsDao.getContactIdFromName(contactName);
            int customerId = apptCustIdCombo.getValue();
            int userId = apptUserIdCombo.getValue();
            LocalDateTime createdDate = LocalDateTime.now();
            LocalDateTime lastUpdated = LocalDateTime.now();
            String loggedInUser = Users.getLoggedInUser().getUserName();

            AppointmentsDao.insertAppointment(title, description, location, type, startTimestampUTC, endTimestampUTC, createdDate, loggedInUser
                   ,lastUpdated,loggedInUser,customerId,userId,contactId);

            informationAlert.setContentText("Appointment added successfully!");
            informationAlert.showAndWait();

        }catch (Exception e) {
            System.out.println("Error: adding appointment.");
            System.out.println(e.getMessage());
        }
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
        endTimeCombo.getSelectionModel().selectLast();
        apptContactCombo.setItems(Contacts.allContacts);
        apptCustIdCombo.setItems(Customers.getCustomerIds());
        apptUserIdCombo.setItems(Users.getUserIds());

    }
}
