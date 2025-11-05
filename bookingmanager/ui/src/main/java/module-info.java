module bookingmanager.ui {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires transitive bookingmanager.core;
    requires transitive bookingmanager.model;

    opens ui to javafx.fxml;

    exports ui;
}
