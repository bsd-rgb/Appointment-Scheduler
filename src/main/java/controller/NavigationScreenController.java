package controller;

import com.bd.Application;
import dao.CustomersDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.Customers;

import java.io.IOException;
import java.sql.SQLException;

public class NavigationScreenController {
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
    void onActionViewAppointments(ActionEvent event) {

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

}

