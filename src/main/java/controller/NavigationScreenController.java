package controller;

import com.bd.Application;
import dao.CustomersDao;
import dao.UsersDao;
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
import model.Customers;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class NavigationScreenController implements Initializable {

    @FXML
    private Label appointmentLbl;

    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);

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

    @FXML
    void onActionViewAppointments(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ViewAppointments.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void onActionViewCustomers(ActionEvent event) throws IOException {

        try {
            CustomersDao.selectCustomers();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ViewCustomer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void onActionViewReports(ActionEvent event) {

    }

    public void UserIdAppointments(int userId){

        LocalDateTime currentTime = LocalDateTime.now();
        boolean hasAppointment = false;

        for(Appointments appointment: Appointments.getAllAppointments()){
            if(appointment.getUserId() == userId){
                System.out.println("Appointment found for user");
                hasAppointment = true;

                LocalDateTime appointmentStart = appointment.getStart();
                Long timeDifference = ChronoUnit.MINUTES.between(appointmentStart, currentTime);

               if(timeDifference > 0 && timeDifference <= 15){ //this used to be 15 instead of 2
                    System.out.println("There's an upcoming appointment(s).");
                    String defaultLabelTxt = "Appointment ID: " + appointment.getAppointmentId() + " - Start Date: " + appointment.getStart().toLocalDate() + " - Start Time: " + appointment.getStart().toLocalTime() + "\n";
                    appointmentLbl.setText(appointmentLbl.getText() + defaultLabelTxt);
                }else {
                    System.out.println("No upcoming appointment(s).");
                }
            }
        }
        if(!hasAppointment){
            appointmentLbl.setText("No upcoming appointment(s).");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Navigation page initialized.");
        //System.out.println("Logged in User ID: " + UsersDao.getCurrentUser());
        UserIdAppointments(Users.getLoggedInUser().getUserId());
        //appointmentLbl.setText("Setting test text.");
    }
}

