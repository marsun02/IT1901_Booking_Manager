module bookingmanager.core {
    requires transitive bookingmanager.persistence;
    requires transitive bookingmanager.model;

    exports core;
}
