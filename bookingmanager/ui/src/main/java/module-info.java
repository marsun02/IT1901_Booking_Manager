module bookingmanager.ui {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires transitive bookingmanager.core;
    requires bookingmanager.persistence;

    opens ui to javafx.fxml;

    exports ui;
}
