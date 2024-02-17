module com.bd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.bd to javafx.fxml;
    exports com.bd;
    exports controller;
    opens controller to javafx.fxml;
}