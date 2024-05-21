module it.polimi.ingsw.gc31 {
    requires transitive javafx.graphics;
    requires transitive java.desktop;
    requires transitive com.google.gson;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.rmi;
    requires MaterialFX;
    requires org.fusesource.jansi;

    opens it.polimi.ingsw.gc31.view.gui to javafx.fxml;
    opens it.polimi.ingsw.gc31.view.gui.controllers to javafx.fxml;

    exports it.polimi.ingsw.gc31;
    exports it.polimi.ingsw.gc31.controller;
    exports it.polimi.ingsw.gc31.exceptions;
    exports it.polimi.ingsw.gc31.view;
    exports it.polimi.ingsw.gc31.view.interfaces;
    exports it.polimi.ingsw.gc31.view.tui;
    exports it.polimi.ingsw.gc31.view.gui;
    exports it.polimi.ingsw.gc31.view.gui.controllers;
    exports it.polimi.ingsw.gc31.client_server.interfaces;
    exports it.polimi.ingsw.gc31.client_server.queue.clientQueue;
    exports it.polimi.ingsw.gc31.client_server.queue.serverQueue;
    exports it.polimi.ingsw.gc31.client_server.tcp;
    exports it.polimi.ingsw.gc31.client_server.rmi;
    exports it.polimi.ingsw.gc31.model;
    exports it.polimi.ingsw.gc31.model.enumeration;
    exports it.polimi.ingsw.gc31.model.card;
    exports it.polimi.ingsw.gc31.model.strategies;
    exports it.polimi.ingsw.gc31.model.player;

}
