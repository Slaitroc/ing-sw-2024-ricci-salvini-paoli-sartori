module it.polimi.ingsw.gc31 {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires com.google.gson;
    requires MaterialFX;

    opens it.polimi.ingsw.gc31.view to javafx.fxml;

    exports it.polimi.ingsw.gc31; // Esporta il tuo package principale
}
