module com.example.queuemanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.queuemanager to javafx.fxml;
    exports com.example.queuemanager;
}