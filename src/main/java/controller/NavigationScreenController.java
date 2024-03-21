package controller;

import com.bd.Application;
import dao.CustomersDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Customers;

import java.io.IOException;
import java.sql.SQLException;

public class NavigationScreenController {

    @FXML
    void onActionLogOff(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("LoginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onActionViewAppointments(ActionEvent event) {

    }

    @FXML
    void onActionViewCustomers(ActionEvent event) throws IOException {

        try {
            CustomersDao.selectCustomers();
            System.out.println("The try block was successful.");
            System.out.println(Customers.getAllCustomers());
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

