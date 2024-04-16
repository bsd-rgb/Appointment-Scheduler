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


        for(Appointments appointment: Appointments.getAllAppointments()){
            if(appointment.getUserId() == userId){
                System.out.println("Appointment found for user");

/*                LocalDateTime appointmentStart = appointment.getStart();
                Long timeDifference = ChronoUnit.MINUTES.between(appointmentStart, currentTime);
                System.out.println("Time Difference: "+timeDifference);

                if(timeDifference > 0 && timeDifference <= 15){
                    System.out.println("There's an upcoming appointment");
                }else {
                    System.out.println("No upcoming appointment.");
                }*/
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Navigation page initialized.");
        //System.out.println("Logged in User ID: " + UsersDao.getCurrentUser());
        UserIdAppointments(Users.getLoggedInUser().getUserId());
    }
}

