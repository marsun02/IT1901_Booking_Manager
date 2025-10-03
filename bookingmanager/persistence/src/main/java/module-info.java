module bookingmanager.persistence {
    requires com.fasterxml.jackson.databind;

    opens persistence to com.fasterxml.jackson.databind;
    exports persistence;
}
