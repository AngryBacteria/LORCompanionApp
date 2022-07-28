package ui.gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.card.Card;

/**
 * Controller for the MainMenu Scene. Here all the Cards can be looked at with a TableView. There is also the
 * possibility to edit them via a Popup.
 */
public class MainMenuController extends Controller{

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Card> cardTable;
    @FXML
    private TableColumn<Card, String> cardNameColumn;
    @FXML
    private TableColumn<Card, Integer> cardAttackColumn;
    @FXML
    private TableColumn<Card, Integer> cardHealthColumn;
    @FXML
    private TableColumn<Card, Integer> cardCostColumn;
    @FXML
    private TableColumn<Card, String> cardRegionColumn;
    @FXML
    private TableColumn<Card, String> cardKeywordsColumn;
    @FXML
    private TableColumn<Card, String> cardCodeColumn;
    @FXML
    private TableColumn<Card, String> cardSubTypeColumn;

    ObservableList<Card> observableList;

    @FXML
    public void initialize(){

        borderPane.setTop(getHeader());
        borderPane.setLeft(getLeft());

        cardNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        cardAttackColumn.setCellValueFactory(
                new PropertyValueFactory<>("attack"));

        cardHealthColumn.setCellValueFactory(
                new PropertyValueFactory<>("health"));

        cardCostColumn.setCellValueFactory(
                new PropertyValueFactory<>("cost"));

        cardRegionColumn.setCellValueFactory(
                new PropertyValueFactory<>("regions"));

        cardKeywordsColumn.setCellValueFactory(
                new PropertyValueFactory<>("keywords"));

        cardCodeColumn.setCellValueFactory(
                new PropertyValueFactory<>("cardCode"));

        cardSubTypeColumn.setCellValueFactory(
                new PropertyValueFactory<>("subtypes"));

        this.observableList = App.getCardService().getObservableList();
        cardTable.setItems(this.observableList);
        cardTable.sort();
    }

    @FXML
    private void editCard() {
        if (!(cardTable.getSelectionModel().getSelectedItem() == null)){
            App.getHelperClass().setCard(cardTable.getSelectionModel().getSelectedItem());
            switchToCardEdit();
        }
    }

    @FXML
    private void deleteCardAction() {
        if (!(cardTable.getSelectionModel().getSelectedItem() == null)){
            App.getCardService().removeCard(cardTable.getSelectionModel().getSelectedItem());
            App.getHelperClass().removeSingeImage(cardTable.getSelectionModel().getSelectedItem());
            cardTable.refresh();
        }
    }

    @FXML
    public void cardNameSearchEvent(KeyEvent keyEvent) {

        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            this.observableList = App.getCardService().getObservableListByCardNames(searchField.getText());
            cardTable.setItems(this.observableList);
            cardTable.sort();
        }
    }

    @FXML
    public void resetAction() {
        resetTable();
    }

    private void resetTable(){
        this.observableList = App.getCardService().getObservableList();
        cardTable.setItems(this.observableList);
        cardTable.sort();
    }

    @Override
    protected String getViewName() {
        return "mainMenu";
    }
}
