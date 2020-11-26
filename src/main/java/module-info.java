module org.mp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.swing;
    requires java.sql;
    requires mysql.connector.java;

    opens org.mp to javafx.fxml;
    exports org.mp;
}