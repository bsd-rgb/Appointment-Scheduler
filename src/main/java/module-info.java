module com.bd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.bd to javafx.fxml;
    exports com.bd;
    exports controller;
    opens controller to javafx.fxml;
    opens model to javafx.fxml;
    exports model;
}