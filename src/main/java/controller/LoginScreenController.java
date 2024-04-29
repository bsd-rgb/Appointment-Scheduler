package controller;

import com.bd.Application;
import dao.UsersDao;
import helper.LocaleHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
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
    private Label timeZoneLbl;

    @FXML
    private TextField usernameTxtField;
    private boolean french = false;
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    ResourceBundle rb = ResourceBundle.getBundle("com.bd/Nat", Locale.getDefault());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ZoneId zoneId = ZoneId.systemDefault();
        locationLbl.setText(zoneId.toString());

       if(Locale.getDefault().getLanguage().equals("fr")) {
           french = true;
       }

       if(french) {
           try {
               usernameLbl.setText(rb.getString("Username"));
               passwordLbl.setText(rb.getString("Password"));
               exitBtn.setText(rb.getString("Exit"));
               submitBtn.setText(rb.getString("Submit"));
               timeZoneLbl.setText(rb.getString("Timezone"));
           } catch (Exception e) {
               System.out.println(e.getMessage());
           }
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

            if(UsersDao.findUser(userName,password) != null ) {
                System.out.println(rb.getString("Found"));
                UsersDao.setCurrentUser(new Users(UsersDao.findUser(userName,password).getUserId(),UsersDao.findUser(userName,password).getUserName(), UsersDao.findUser(userName,password).getPassword()));
                Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("NavigationScreen.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            } else {
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
    //Lambda here
        ((Button) confirmationAlert.getDialogPane().lookupButton(ButtonType.OK)).setText("Exit");
        confirmationAlert.setContentText(rb.getString("ExitProgram"));
        confirmationAlert.showAndWait().ifPresent((response -> {
            if(response == ButtonType.OK){
                System.exit(0);
            }else{
                return;
            }
        }));
    }
}