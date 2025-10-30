module bookingmanager.model {

    exports model;
    requires com.fasterxml.jackson.annotation;

    opens model to com.fasterxml.jackson.databind;
}
