module bookingmanager.core {
    requires transitive bookingmanager.model;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    exports core;
}
