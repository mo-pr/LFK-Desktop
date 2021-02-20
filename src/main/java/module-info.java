module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.media;
    requires json;

    opens org.preining to javafx.fxml;
    exports org.preining;
}