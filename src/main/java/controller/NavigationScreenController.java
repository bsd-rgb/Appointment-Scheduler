package controller;

import com.bd.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Appointments;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

/** The NavigationScreenController is used to navigate to the customer page, appointment page, or report page after successful login.
 *
 * @author Brandi Davis
 * */
public class NavigationScreenController implements Initializable {
    @FXML
    private Label appointmentLbl;
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);

    /** Initializes the NavigationScreenController.
     *
     * Runs the UserIdAppointment method (see method information for details)
     * @param url The location used to resolve relative paths for root object
     * @param resourceBundle The resources used to localize the root object
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserIdAppointments(Users.getLoggedInUser().getUserId());
    }

    /** Used to log off and return to the LoginScreenController.
     *
     * Renames the OK button to "Log off"
     * @param event on action log off button
     * @throws IOException from FXMLLoader in the event of an error loading the LoginScreenController
     * */
    @FXML
    void onActionLogOff(ActionEvent event) throws IOException {
        confirmationAlert.setContentText("Are you sure you would like to log off?");
        confirmationAlert.showAndWait();
        ((Button) confirmationAlert.getDialogPane().lookupButton(ButtonType.OK)).setText("Log Off");
        if(confirmationAlert.getResult() == ButtonType.OK) {
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("LoginScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } else {
            return;
        }
    }

    /** Navigates to the ViewAppointmentsController.
     *
     * @param event on action view appointments button
     * @throws IOException from FXMLLoader in the event of an error loading the ViewAppointmentsController
     * */
    @FXML
    void onActionViewAppointments(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ViewAppointments.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }

    /** Navigates to the ViewCustomersController.
     *
     * @param event on action view customers button
     * @throws IOException from FXMLLoader in the event of an error loading the ViewCustomersController
     * */
    @FXML
    void onActionViewCustomers(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ViewCustomer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /** Navigates to the ReportViewerController.
     *
     * @param event on action view reports button
     * @throws IOException from FXMLLoader in the event of an error loading the ReportViewerController
     * */
    @FXML
    void onActionViewReports(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ReportViewer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /** This method is used to determine if there is an upcoming appointment for the user that is logged in.
     *
     * If there is an upcoming appointment, the appointment information will be displayed in a label on the screen. If there are no upcoming appointments, it will display that in the label.
     * @param userId the user ID of the user that is logged in
     * */
    public void UserIdAppointments(int userId){
        LocalDateTime currentTime = LocalDateTime.now();
        boolean hasAppointment = false;

        for(Appointments appointment: Appointments.getAllAppointments()){
            if(appointment.getUserId() == userId){
                LocalDateTime appointmentStart = appointment.getStart();
                Long timeDifference = ChronoUnit.MINUTES.between(appointmentStart, currentTime);
                timeDifference = timeDifference * (-1);
                if(currentTime.isBefore(appointmentStart)) {
                    if (timeDifference > 0 && timeDifference <= 15) {
                        String defaultLabelTxt = "Appointment ID: " + appointment.getAppointmentId() + " - Start Date: " + appointment.getStart().toLocalDate() + " - Start Time: " + appointment.getStart().toLocalTime() + "\n";
                        appointmentLbl.setText(appointmentLbl.getText() + defaultLabelTxt);
                        hasAppointment = true;
                    }
                }
            }
        }
        if(!hasAppointment){
            appointmentLbl.setText("No upcoming appointment(s).");
        }
    }
}

