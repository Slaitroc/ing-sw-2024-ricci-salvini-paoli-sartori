module it.polimi.ingsw.gc31 {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens it.polimi.ingsw.gc31.View to javafx.fxml;
    exports it.polimi.ingsw.gc31;
}