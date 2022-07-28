package ui.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import model.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Abstract JavaFX Controller. It holds all sorts of Methods that are used by the extending Controllers. Also static
 * UI Elements such as the MenuBar are designed here and then set for every SubScene.
 */
public abstract class Controller {

    @FXML
    protected BorderPane borderPane;

    protected MenuBar getHeader(){
        MenuBar menuBar = new MenuBar();

        //AppMenu
        Menu appMenu = new Menu("Application");
        appMenu.setGraphic(getIcon("menuIcon.png"));

        MenuItem menuItemClose = new MenuItem("Close");
        menuItemClose.setGraphic(getIcon("closeIcon.png"));
        menuItemClose.setOnAction(actionEvent -> quitApplication());

        MenuItem cardDirectoryItem = new MenuItem("Set Card directory");
        cardDirectoryItem.setGraphic(getIcon("folderOpenIcon.png"));
        cardDirectoryItem.setOnAction(actionEvent -> setCardFileDirectory());

        MenuItem saveCardsItem = new MenuItem("Save cards to Json");
        saveCardsItem.setGraphic(getIcon("backup.png"));
        saveCardsItem.setOnAction(actionEvent -> {
            App.getCardService().writeCardsToJson();
            AlertBox.display("Cards Saved", "Cards saved to Json file");
        });

        MenuItem downloadAllImages = new MenuItem("Download all Card Images");
        downloadAllImages.setGraphic(getIcon("download.png"));
        downloadAllImages.setOnAction(actionEvent -> {
            App.getCardService().downloadImages();
            App.getHelperClass().initiateMissingImageViews();
            AlertBox.display("Image Download", "Images downloaded");
        });

        MenuItem downloadAllImagesFull = new MenuItem("Download all Full size Card Images");
        downloadAllImagesFull.setGraphic(getIcon("download.png"));
        downloadAllImagesFull.setOnAction(actionEvent -> {
            App.getCardService().downloadImagesFull();
            AlertBox.display("Image Download", "Images downloaded");
        });

        appMenu.getItems().addAll(menuItemClose, cardDirectoryItem, saveCardsItem, downloadAllImages, downloadAllImagesFull);

        //Action Menu
        Menu actionMenu = new Menu("Actions");
        actionMenu.setGraphic(getIcon("action.png"));

        MenuItem menuItemMainMenu = new MenuItem("Open Main Menu");
        menuItemMainMenu.setOnAction(actionEvent -> switchToMainMenu());

        MenuItem menuItemCardCreator = new MenuItem("Open Card Creator");
        menuItemCardCreator.setOnAction(actionEvent -> switchToCardCreator());

        MenuItem menuItemDeckCreator = new MenuItem("Open Deck Creator");
        menuItemDeckCreator.setOnAction(actionEvent -> switchToDeckCreator());
        actionMenu.getItems().addAll(menuItemMainMenu, menuItemCardCreator, menuItemDeckCreator);

        menuBar.getMenus().addAll(appMenu, actionMenu);
        return menuBar;
    }

    protected VBox getLeft(){
        VBox vBox = new VBox();
        Button buttonMainMenu = new Button("Main Menu");
        Button buttonCardCreator = new Button("Card Creator");
        Button buttonDeckCreator = new Button("Deck Creator");

        vBox.setSpacing(10);
        VBox.setMargin(buttonMainMenu, new Insets(2, 10, 0, 0));
        vBox.setAlignment(Pos.TOP_LEFT);

        double width = 100d;
        buttonCardCreator.setPrefWidth(width);
        buttonMainMenu.setPrefWidth(width);
        buttonDeckCreator.setPrefWidth(width);

        buttonMainMenu.setOnAction(actionEvent -> switchToMainMenu());
        buttonCardCreator.setOnAction(actionEvent -> switchToCardCreator());
        buttonDeckCreator.setOnAction(actionEvent -> switchToDeckCreator());

        vBox.getChildren().addAll(buttonMainMenu, buttonCardCreator, buttonDeckCreator);
        return vBox;
    }

    //Switch scenes methods
    protected void switchToCardCreator() {
        try {
            App.setRoot("/CardCreator");
        } catch (IOException e) {
            App.logger.writeLog(Logger.Operation.EXCEPTION, "CardCreator FXML File couldn't be loaded correctly. ");
            e.printStackTrace();
        }
    }

    protected void switchToDeckCreator() {
        try {
            App.setRoot("/deckCreator");
        } catch (IOException e) {
            App.logger.writeLog(Logger.Operation.EXCEPTION, "deckCreator FXML File couldn't be loaded correctly. ");
            e.printStackTrace();
        }
    }

    protected void switchToMainMenu() {
        try {
            App.setRoot("/MainMenu");
        } catch (IOException e) {
            App.logger.writeLog(Logger.Operation.EXCEPTION, "MainMenu FXML File couldn't be loaded correctly. ");
            e.printStackTrace();
        }
    }

    protected void switchToCardEdit() {
        try {
            App.setRoot("/CardEdit");
        } catch (IOException e) {
            App.logger.writeLog(Logger.Operation.EXCEPTION, "CardEdit FXML File couldn't be loaded correctly. ");
            e.printStackTrace();
        }
    }

    //Helper methods
    protected void quitApplication() {
        App.getCardService().writeCardsToJson();
        App.logger.writeLog(Logger.Operation.IO_OPERATION, "Cards saved to json file");
        Platform.exit();
    }

    protected void setCardFileDirectory(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(App.getScene().getWindow());
        App.setPictureFolderPath(selectedDirectory + File.separator);
        App.getHelperClass().resetImageViews();
    }

    protected ImageView getIcon(String name){
        ImageView openView = null;
        try {
            Image openIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/css/icons/" + name)),
                    25, 25, true, false);
            openView = new ImageView(openIcon);
        } catch (Exception e) {
            App.logger.writeLog(Logger.Operation.EXCEPTION, "Icon [" + name + "] couldn't be loaded");
            e.printStackTrace();
        }
        return openView;
    }

    protected void setRedBorder (javafx.scene.Parent textField){
        textField.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
    }

    protected void resetRedBorder (javafx.scene.Parent textField){
        textField.setStyle("-fx-border-style: none;");
    }

    @FXML
    public abstract void initialize();
    protected abstract String getViewName();
}
