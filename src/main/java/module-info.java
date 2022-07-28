module CardProjekt {

    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    requires R4J;
    requires org.controlsfx.controls;

    exports ui.gui to javafx.graphics;
    opens ui.gui to javafx.base, javafx.fxml;

    exports model.logging;

    exports model.card;
    exports model.card.subclasses;
    opens model.card.subclasses to com.google.gson;
    opens model.card to com.google.gson;
}