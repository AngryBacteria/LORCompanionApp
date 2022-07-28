package ui.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.card.Card;
import model.card.CardService;
import model.card.subclasses.CardAndAmount;
import model.card.subclasses.Deck;
import model.card.subclasses.Region;
import model.logging.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller for the DeckBuilder Scene. Here all the Cards can be searched with a Gallery that has filter options.
 * A deck can be created and the Code exported. Also the cards Art can be further looked at by middle-clicking the images.
 */
public class DeckCreatorController extends Controller {


    private final Map<String, ImageView> imageViewMap = App.getHelperClass().getImageViewMap();
    @FXML
    private TableColumn<CardAndAmount, String> nameColumn;
    @FXML
    private Button importButton;
    @FXML
    private TableColumn<CardAndAmount, Number> amountColumn;
    @FXML
    private TableView<CardAndAmount> cardTable;
    @FXML
    private TableColumn<CardAndAmount, Number> costColumn;
    @FXML
    private Button clearButton;
    @FXML
    private Label cardsLabel;
    @FXML
    private TilePane imageTilePane;
    @FXML
    private TextField cardNameField;
    @FXML
    private TextField descriptionSearchField;
    @FXML
    private ComboBox<Region> regionComboBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Deck deck;

    //Popup stuff
    private boolean fullImage = true;
    private ListIterator<ImageView> popupImageIterator;
    private int popupListIndex = 0;
    private List<ImageView> fullImages = new ArrayList<>();
    private List<ImageView> gameImages = new ArrayList<>();

    @FXML
    public void initialize(){

        this.imageViewMap.values().forEach(imageView -> imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.executeImageViewEvent(imageView, event)));

        this.regionComboBox.getSelectionModel().selectLast();
        this.borderPane.setTop(this.getHeader());
        this.borderPane.setLeft(this.getLeft());
        this.deck = new Deck();
        this.cardTable.getItems().addAll(this.deck.getCardAndAmounts());
        this.cardsLabel.setText(String.valueOf(this.deck.getAmountOfCards()));

        this.setSortedImages();
        this.regionComboBox.getItems().addAll(Region.values());
        this.regionComboBox.getSelectionModel().selectLast();

        this.nameColumn.setCellValueFactory(celldata -> {
            String name = celldata.getValue().getCard().getName();
            return new SimpleStringProperty(name);
        } );

        this.amountColumn.setCellValueFactory(celldata -> {
            int amount = celldata.getValue().getAmount();
            return new SimpleIntegerProperty(amount);
        });

        this.costColumn.setCellValueFactory(celldata -> {
            int cost = celldata.getValue().getCard().getCost();
            return new SimpleIntegerProperty(cost);
        });
    }

    private void openPicturePopup(ImageView imageView){

        Stage window = new Stage();
        this.fullImages = new ArrayList<>();
        this.gameImages = new ArrayList<>();

        //Firstimage
        this.fullImages.add(this.createPopupImageFull(imageView.getId()));
        //Associated cards full
        List<Card> associatedCardsImagesFull = App.getCardService().getAssociatedCards(imageView.getId());
        if (associatedCardsImagesFull.size() >= 1){
            associatedCardsImagesFull.forEach(card -> this.fullImages.add(this.createPopupImageFull(card.getCardCode())));
        }

        this.gameImages.add(this.createPopupImageGame(imageView.getId()));
        //Associated cards game
        List<Card> associatedCardsImagesGame = App.getCardService().getAssociatedCards(imageView.getId());
        if (associatedCardsImagesGame.size() >= 1){
            associatedCardsImagesGame.forEach(card -> this.gameImages.add(this.createPopupImageGame(card.getCardCode())));
        }

        BorderPane layout = new BorderPane();
        layout.setCenter(this.fullImages.get(0));
        Scene scene = new Scene(layout, 1280, 720);
        window.setScene(scene);
        this.fullImages.get(0).setOnMouseClicked(action -> window.close());

        if (App.getCardService().getCardByCode(imageView.getId()).isPresent()){
            window.setTitle(App.getCardService().getCardByCode(imageView.getId()).get().getName());

            this.popupImageIterator = this.fullImages.listIterator();

            //label hbox
            Label label = new Label(App.getCardService().getCardByCode(imageView.getId()).get().getFlavorText());
            label.setWrapText(true);
            label.setFont(new Font("Arial", 24));
            label.alignmentProperty().set(Pos.CENTER);
            HBox hBox = new HBox(label);
            hBox.setAlignment(Pos.CENTER);
            layout.setBottom(hBox);

            //Main vbox
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            //vBox.getChildren().add(hBox);

            //forward button
            Button forwardButton = new Button("next");
            forwardButton.setOnMouseClicked(mouseEvent -> this.nextPicture(layout, label));
            vBox.getChildren().add(forwardButton);

            //forward button
            Button backwardButton = new Button("previous");
            backwardButton.setOnMouseClicked(mouseEvent -> this.previousPicture(layout, label));
            vBox.getChildren().add(backwardButton);

            //switch button
            Button switchButton = new Button("switch");
            switchButton.setOnMouseClicked(mouseEvent -> this.switchPicture(layout));
            vBox.getChildren().add(switchButton);

            layout.setTop(vBox);
        }
        window.showAndWait();
    }

    private ImageView createPopupImageFull(String cardId){

        ImageView popImageView = new ImageView(CardService.pictureFolderPath + cardId + "-full.png");
        popImageView.setFitHeight(600);
        popImageView.setPreserveRatio(true);
        popImageView.setId(cardId);
        return popImageView;
    }

    private ImageView createPopupImageGame(String cardId){

        ImageView popImageView = new ImageView(CardService.pictureFolderPath + cardId + ".png");
        popImageView.setFitHeight(500);
        popImageView.setPreserveRatio(true);
        popImageView.setId(cardId);
        return popImageView;
    }

    private void nextPicture(BorderPane layout, Label label){

        if (this.popupImageIterator.hasNext()){
            this.popupListIndex = this.popupImageIterator.nextIndex();
            ImageView imageView = this.popupImageIterator.next();
            layout.setCenter(imageView);

            if (App.getCardService().getCardByCode(imageView.getId()).isPresent())
                label.setText(App.getCardService().getCardByCode(imageView.getId()).get().getFlavorText());
            else
                label.setText("");
        }
    }

    private void previousPicture(BorderPane layout, Label label){

        if (this.popupImageIterator.hasPrevious()){
            this.popupListIndex = this.popupImageIterator.previousIndex();
            ImageView imageView = this.popupImageIterator.previous();
            layout.setCenter(imageView);
            if (App.getCardService().getCardByCode(imageView.getId()).isPresent())
                label.setText(App.getCardService().getCardByCode(imageView.getId()).get().getFlavorText());
            else
                label.setText("");
        }
    }

    private void switchPicture(BorderPane layout){

        if (this.fullImage){
            layout.setCenter(this.gameImages.get(this.popupListIndex));
            this.fullImage = false;
        }
        else{
            layout.setCenter(this.fullImages.get(this.popupListIndex));
            this.fullImage = true;
        }
    }

    /**
     * Every ImageView has this Event set on creation. Three Actions for all Mousebuttons.
     * @param imageView ImageView which the Event should be set
     * @param event Mouse Event executing the event
     */
    private void executeImageViewEvent(ImageView imageView, MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY){
            try {

                App.getCardService().getCardByCode(imageView.getId()).ifPresent(card -> this.deck.addCard(card));
                this.cardTable.getItems().clear();
                System.out.println(imageView.getId() +  ": Added ");
                this.cardTable.getItems().addAll(this.deck.getCardAndAmounts());
                this.cardsLabel.setText(String.valueOf(this.deck.getAmountOfCards()));
                this.resetRedBorder(this.importButton);
            } catch (IllegalStateException e) {
                e.printStackTrace();
                App.logger.writeLog(Logger.Operation.EXCEPTION, "Card [" + imageView.getId() + "] could not " +
                        "be added to the Deck because of the game rules");
            }
        }

        if (event.getButton() == MouseButton.MIDDLE)
            this.openPicturePopup(imageView);

        if (event.getButton() == MouseButton.SECONDARY){
            System.out.println(imageView.getId() +  ": Removed ");
            this.cardTable.getItems().clear();

            App.getCardService().getCardByCode(imageView.getId()).ifPresent(card -> this.deck.removeCard(card, 1));
            this.cardTable.getItems().addAll(this.deck.getCardAndAmounts());
            this.cardsLabel.setText(String.valueOf(this.deck.getAmountOfCards()));
            this.resetRedBorder(this.importButton);
        }
        event.consume();
    }

    /**
     * Displays all Card ImageViews that match the given Name. It uses the CardServices Name Search.
     * @param name Card Name that will be used for Search
     */
    private void displayCardsByName(String name){

        if (name.length() < 2)
            return;

        this.imageTilePane.getChildren().clear();

        List<ImageView> imageViewList = new ArrayList<>();
        this.getBaseCardArray().stream()
                .filter(Card::isCollectible)
                .filter(card -> card.getName().toLowerCase().contains(name.toLowerCase()))
                .forEach(card -> this.collectImage(imageViewList, card));

        this.imageTilePane.getChildren().addAll(imageViewList);
    }

    /**
     * Displays all Card ImageViews that match the given Description. It uses the "contains Description" search algorithm of the Card Class.
     * @param description Term that will be searched for in the Card Objects
     */
    private void displayCardsByDescription(String description){

        if (description.length() < 2)
            return;

        this.imageTilePane.getChildren().clear();

        description = description.toLowerCase();
        description = description.trim();
        List<ImageView> imageViewList = new ArrayList<>();
        String finalDescription = description;
        this.getBaseCardArray().stream().
                filter(Card::isCollectible).
                filter(card -> card.cardContains(finalDescription)).
                forEach(card -> this.collectImage(imageViewList, card));
        this.imageTilePane.getChildren().addAll(imageViewList);
    }

    /**
     * Helper Method for the displpay card Methods
     * @param imageViewList ImageViewlist to add imageview to
     * @param card Card to add as an Image View
     */
    private void collectImage(List<ImageView> imageViewList, Card card){
        try {
            imageViewList.add(this.imageViewMap.get(card.getCardCode()));
        } catch (Exception e) {
            System.out.println("Picture file not found [CardCode = " + card.getCardCode() + "]");
            App.logger.writeLog(Logger.Operation.EXCEPTION, "Picture file not found [CardCode = " + card.getCardCode() + "]");
        }
    }

    private void setSortedImages(){

        List<ImageView> imageViewList = new ArrayList<>();
        App.getCardService().getCardArray().stream()
                .filter(Card::isCollectible)
                .sorted(Comparator.comparingInt(Card::getCost))
                .sorted(Comparator.comparing(Card::isChampion).reversed())
                .forEach(card -> this.collectImage(imageViewList, card));
        this.imageTilePane.getChildren().addAll(imageViewList);
    }

    /**
     * Copies the Deck Code of the current built deck to the Pc's Clipboard.
     */
    @FXML
    private void copyDeckCode() {
        final ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(this.deck.convertDeckToLorDeck().getDeckCode());
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }

    @FXML
    private void importButtonAction() {

        String deckCode = Clipboard.getSystemClipboard().getString();

        try {
            this.deck = new Deck(deckCode);
            this.cardTable.getItems().clear();
            this.cardTable.getItems().addAll(this.deck.getCardAndAmounts());
            cardsLabel.setText("" + deck.getAmountOfCards());
        } catch (Exception e) {
            this.setRedBorder(this.importButton);
        }
    }

    @FXML
    private ArrayList<Card> getBaseCardArray(){

        if (this.regionComboBox.getValue().equals(Region.ALL)){
            return App.getCardService().getCardArray().stream()
                    .sorted(Comparator.comparingInt(Card::getCost))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        return App.getCardService().getCardArray().stream()
                .sorted(Comparator.comparingInt(Card::getCost))
                .filter(card -> card.getRegions().contains(this.regionComboBox.getValue()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @FXML
    private void cardNameSearchEvent(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            this.displayCardsByName(this.cardNameField.getText());
        }
    }

    @FXML
    private void cardDescriptionSearchEvent(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            this.displayCardsByDescription(this.descriptionSearchField.getText());
        }
    }

    @FXML
    private void deleteSearchButton() {
        this.imageTilePane.getChildren().clear();
        this.cardNameField.clear();
        this.descriptionSearchField.clear();
        this.regionComboBox.getSelectionModel().selectLast();
        this.setSortedImages();
    }

    @FXML
    private void clearButtonAction() {
        this.deck = new Deck();
        this.cardTable.getItems().clear();
        this.cardTable.getItems().addAll(this.deck.getCardAndAmounts());
        this.cardsLabel.setText(String.valueOf(this.deck.getAmountOfCards()));
        this.resetRedBorder(this.importButton);
    }

    @Override
    protected String getViewName() {
        return "CardGallery";
    }

}
