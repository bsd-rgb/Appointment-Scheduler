package controller;

import dao.UsersDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Users;

import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    @FXML
    private TextField passwordTxtField;
    @FXML
    private Label locationLbl;
    @FXML
    private TextField usernameTxtField;
    private boolean french = false;
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ZoneId zoneId = ZoneId.systemDefault();
        locationLbl.setText("Location: " + zoneId.toString());

    }

    @FXML
    void onActionLogin(ActionEvent event) {

        try {
            String userName = usernameTxtField.getText();
            String password = passwordTxtField.getText();
            if(UsersDao.selectUser(userName,password) != null ) {
                System.out.println("User Found!");
            } else {
                System.out.println("User not found");
                errorAlert.setTitle("Login Error");
                errorAlert.setContentText("Error: Invalid Username / Password.");
                errorAlert.showAndWait();
                return;
            }
            usernameTxtField.clear();
            passwordTxtField.clear();

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void onActionExitProgram(ActionEvent event) {

        ((Button) confirmationAlert.getDialogPane().lookupButton(ButtonType.OK)).setText("Exit");
        confirmationAlert.setContentText("Are you sure you would like to exit the program?");
        confirmationAlert.showAndWait();
        if(confirmationAlert.getResult() == ButtonType.OK) {
            System.exit(0);
        } else return;
    }

    @FXML
    void onActionClearFields(ActionEvent event) {

        confirmationAlert.setContentText("Are you sure you want to clear the fields? Click OK to confirm.");
        confirmationAlert.showAndWait();
        if(confirmationAlert.getResult() == ButtonType.OK) {
            usernameTxtField.clear();
            passwordTxtField.clear();
        } else return;

    }




}