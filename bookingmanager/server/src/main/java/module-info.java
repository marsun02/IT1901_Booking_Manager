module bookingmanager.server {
    requires transitive bookingmanager.model;
    requires transitive bookingmanager.persistence;
    exports server;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires spring.context;
}
