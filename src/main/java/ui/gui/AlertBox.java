package ui.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class that helps with displaying messages to the user.
 * Source <a href="https://www.youtube.com/watch?v=HFAsMWkiLvg">https://www.youtube.com/watch?v=HFAsMWkiLvg</a>
 */
public class AlertBox {

    public static void display(String title, String message){

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(400);
        window.setHeight(100);

        Label label = new Label();
        label.setText(message);
        label.setFont(new Font(20));

        VBox vBox = new VBox(label);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();

    }

}
