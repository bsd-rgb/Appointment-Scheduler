package controller;

import com.bd.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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

    @FXML
    private TableColumn<String, Contacts> apptContactCol;

    @FXML
    private TableColumn<Integer, Appointments> apptCustomerIdCol;

    @FXML
    private TableColumn<String, Appointments> apptDescCol;

    @FXML
    private TableColumn<LocalDateTime, Appointments> apptEndCol;
    @FXML
    private TableColumn<LocalDateTime, Appointments> apptStartCol;

    @FXML
    private TableColumn<Integer, Appointments> apptIdCol;

    @FXML
    private TableColumn<String, Appointments> apptLocationCol;

    @FXML
    private TableColumn<String, Appointments> apptTitleCol;

    @FXML
    private TableColumn<String, Appointments> apptTypeCol;

    @FXML
    private TableColumn<Integer, Appointments> apptUserIdCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        allAppointments.setSelected(true);
        appointmentTable.setItems(Appointments.getAllAppointments());
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
    void onActionModifyAppointment(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Application.class.getResource("UpdateAppointment.fxml"));
        loader.load();

        UpdateAppointmentController updateAppointmentController = loader.getController();
        updateAppointmentController.SendAppointment(appointmentTable.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
