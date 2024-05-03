package controller;

import com.bd.Application;
import dao.DBConnection;
import dao.UsersDao;
import helper.IOUtil;
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

/** The LoginScreenController is used to log login activity and allows navigation through the program on successful authentication.
 *
 * @author Brandi Davis
 * */
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

    /** Initializes the LoginScreenController.
     *
     * Retrieves the system default Zone ID and displays the timezone
     * Checks if the French language is detected on local OS
     * If OS language is French it will set the main text to French
     * @param url The location used to resolve relative paths for root object
     * @param resourceBundle The resources used to localize the root object
     * */
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

    /** Logs the user into the program if credentials are found and displays error for invalid login.
     *
     * There are error checks for empty fields. Uses the findUser() method from the UsersDao to authenticate login.
     * Writes login result to login_activity.txt file.
     * Navigates to the NavigationScreenController on successful login
     * @param event on action login button
     * */
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
                IOUtil.appendLogin(Users.getLoggedInUser().getUserName(), true);

                Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("NavigationScreen.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            } else {
                IOUtil.appendLogin(userName, false);
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

    /** Exits the program.
     *
     * Uses lambda to exit the program.
     * Displays confirmation dialog to confirm program exit
     * @param event on action exit button
     * */
    @FXML
    void onActionExitProgram(ActionEvent event) {
    //Lambda here
        ((Button) confirmationAlert.getDialogPane().lookupButton(ButtonType.OK)).setText("Exit");
        confirmationAlert.setContentText(rb.getString("ExitProgram"));
        confirmationAlert.showAndWait().ifPresent((response -> {
            if(response == ButtonType.OK){
                DBConnection.closeConnection();
                System.exit(0);
            }else{
                return;
            }
        }));
    }
}