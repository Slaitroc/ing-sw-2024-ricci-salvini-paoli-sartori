module it.polimi.ingsw.gc31 {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires com.google.gson;
    requires java.rmi;
    requires MaterialFX;

    opens it.polimi.ingsw.gc31.view.gui to javafx.fxml;
    opens it.polimi.ingsw.gc31.view.gui.controllers to javafx.fxml;

    exports it.polimi.ingsw.gc31.view;
    exports it.polimi.ingsw.gc31.view.gui;
    exports it.polimi.ingsw.gc31.view.gui.controllers;

    exports it.polimi.ingsw.gc31;
    exports it.polimi.ingsw.gc31.controller;
    exports it.polimi.ingsw.gc31.exceptions;
    exports it.polimi.ingsw.gc31.client_server.interfaces;
    exports it.polimi.ingsw.gc31.client_server.tcp;
    exports it.polimi.ingsw.gc31.client_server.rmi;
    exports it.polimi.ingsw.gc31.model.enumeration;

}
