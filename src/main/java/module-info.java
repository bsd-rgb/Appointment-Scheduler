module com.bd_c195 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bd_c195 to javafx.fxml;
    exports com.bd_c195;
    exports controller;
    opens controller to javafx.fxml;
}