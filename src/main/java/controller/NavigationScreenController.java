package controller;

import com.bd.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationScreenController {

    @FXML
    void onActionLogOff(ActionEvent event) {

    }

    @FXML
    void onActionViewAppointments(ActionEvent event) {

    }

    @FXML
    void onActionViewCustomers(ActionEvent event) throws IOException {

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

