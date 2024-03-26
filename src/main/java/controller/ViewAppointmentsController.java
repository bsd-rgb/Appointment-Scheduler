package controller;

import com.bd.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewAppointmentsController implements Initializable {

    @FXML
    private RadioButton allAppointments;
    @FXML
    private ToggleGroup appointmentFilterToggle;
    @FXML
    private RadioButton appointmentMonth;
    @FXML
    private RadioButton appointmentWeek;
    @FXML
    private TableView<Appointments> appointmentTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allAppointments.setSelected(true);

    }

    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("AddAppointment.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void onActionDeleteAppointment(ActionEvent event) {

    }

    @FXML
    void onActionGoBack(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("NavigationScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void onActionModifyAppointment(ActionEvent event) {

    }


}
