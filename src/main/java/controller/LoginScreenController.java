package controller;

import dao.UsersDao;
import helper.LocaleHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Users;

import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    @FXML
    private Button exitBtn;

    @FXML
    private Label locationLbl;

    @FXML
    private Label passwordLbl;

    @FXML
    private TextField passwordTxtField;

    @FXML
    private Button submitBtn;

    @FXML
    private Label usernameLbl;

    @FXML
    private TextField usernameTxtField;
    private boolean french = false;
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    ResourceBundle rb = ResourceBundle.getBundle("com.bd/Nat", Locale.getDefault());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //get and set zoneId
        ZoneId zoneId = ZoneId.systemDefault();
        locationLbl.setText(zoneId.toString());


       if(Locale.getDefault().getLanguage().equals("fr")) {
           french = true;
       }

       if(french) {
           System.out.println("This is french");
           try {
               usernameLbl.setText(rb.getString("Username"));
               passwordLbl.setText(rb.getString("Password"));
               exitBtn.setText(rb.getString("Exit"));
               submitBtn.setText(rb.getString("Submit"));
           } catch (Exception e) {
               System.out.println(e.getMessage());
           }
       } else {
           System.out.println("This is not french.");
       }

    }

    @FXML
    void onActionLogin(ActionEvent event) {

            if (usernameTxtField.getText().isEmpty() || passwordTxtField.getText().isEmpty()) {
                errorAlert.setTitle(rb.getString("Error"));
                errorAlert.setContentText(rb.getString("Blank"));
                errorAlert.showAndWait();
                return;
            }
        try {
            String userName = usernameTxtField.getText();
            String password = passwordTxtField.getText();

            if(UsersDao.selectUser(userName,password) != null ) {
                System.out.println(rb.getString("Found"));
            } else {
                //once I have the structure, do this in french.
                    errorAlert.setTitle(rb.getString("Error"));
                    errorAlert.setContentText(rb.getString("Incorrect"));
                    passwordTxtField.clear();
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
        confirmationAlert.setContentText(rb.getString("ExitProgram"));
        confirmationAlert.showAndWait();
        if(confirmationAlert.getResult() == ButtonType.OK) {
            System.exit(0);
        } else return;
    }

}