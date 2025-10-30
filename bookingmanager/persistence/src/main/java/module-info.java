module bookingmanager.persistence {
    requires com.fasterxml.jackson.databind;
    requires transitive bookingmanager.model;

    opens persistence to com.fasterxml.jackson.databind;
    exports persistence;
}
