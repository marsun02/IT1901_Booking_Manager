module bookingmanager.server {
    requires transitive bookingmanager.model;
    exports server;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires spring.context;
    requires spring.beans;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
}
