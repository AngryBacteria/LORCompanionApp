package ui.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.card.CardService;
import model.logging.Logger;

import java.io.IOException;
import java.util.Objects;

/**
 * Main GUI-Application that launches the JavaFX Application. It also holds important fields such as the Logger and
 * CardService. The Card field is used by the different subscenes to communicate with each other.
 */
public class App extends Application {

    private static Scene scene;
    private static final CardService cardService = new CardService(false);
    private static final GuiHelper guiHelper = new GuiHelper();
    public static Logger logger = new Logger("CardGUILog.txt");

    //These methods are mostly taken from our HospitalApp
    @Override
    public void start(Stage stage) throws IOException {

        guiHelper.initiateImageViews();
        scene = new Scene(loadFXML("/MainMenu"));
        stage.setScene(scene);
        stage.setTitle("Legends of Runeterra Companion");
        stage.setHeight(720);
        stage.setWidth(1280);
        stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("/css/icons/lorIcon.png"))));
        stage.show();

        //Logging
        stage.setOnCloseRequest(windowEvent -> logger.writeLog(Logger.Operation.SHUTDOWN, "Application Shutdown"));
        logger.writeLog(Logger.Operation.STARTUP, "Application started up");
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void setPictureFolderPath(String pictureFolderPath) {
        CardService.pictureFolderPath = pictureFolderPath;
    }

    public static Scene getScene() {
        return scene;
    }

    public static CardService getCardService(){
        return cardService;
    }

    public static GuiHelper getHelperClass() {
        return guiHelper;
    }
}
