package ui.gui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.card.Card;
import model.card.subclasses.*;
import model.validation.IntRangeValidator;
import model.validation.RegexValidator;
import model.validation.exceptions.InvalidInputException;

import java.io.IOException;

/**
 * Parent Class of CardEdit and CardCreator Controller. The reason this is needed is that those Controllers are
 * almost similar in the way they are Built. Only difference: one is needed to create Cards and the other is used
 * to edit existing Cards.
 */
public class CardCreatorParentController extends Controller{

    @FXML
    protected ComboBox<Region> regionChoice;
    @FXML
    protected ComboBox<Type> typeChoice;
    @FXML
    protected ComboBox<Rarity> rarityChoice;
    @FXML
    protected ComboBox<SpellSpeed> spellSpeedChoice;
    @FXML
    protected TextField nameField;
    @FXML
    protected TextField descriptionField;
    @FXML
    protected TextField attackField;
    @FXML
    protected TextField costField;
    @FXML
    protected TextField healthField;
    @FXML
    protected TextField pictureFullField;
    @FXML
    protected TextField pictureField;
    @FXML
    protected TextField lvlUpDescriptionField;
    @FXML
    protected CheckBox championBox;
    @FXML
    protected Label levelUpLabel;
    @FXML
    private Label errorLabel;

    //Events
    @FXML
    protected void clearButton() {
        nameField.clear();
        descriptionField.clear();
        lvlUpDescriptionField.clear();
        attackField.clear();
        costField.clear();
        healthField.clear();
        pictureField.clear();
        pictureFullField.clear();
        regionChoice.getSelectionModel().clearSelection();
        typeChoice.getSelectionModel().clearSelection();
        rarityChoice.getSelectionModel().clearSelection();
        spellSpeedChoice.getSelectionModel().clearSelection();

        healthField.setDisable(false);
        attackField.setDisable(false);
        spellSpeedChoice.setDisable(true);
    }

    protected boolean validate(){
        IntRangeValidator intRangeValidator = new IntRangeValidator(100, 0);

        //General
        if (typeChoice.getValue() == null){
            setRedBorder(typeChoice);
            this.errorLabel.setText("Please Choose a Type");
            return false;
        }

        if (nameField.getText().length() < 1){
            setRedBorder(nameField);
            this.errorLabel.setText("Please write a Name");
            return false;
        }

        if (descriptionField.getText().length() < 1){
            setRedBorder(descriptionField);
            this.errorLabel.setText("Please write a Description");
            return false;
        }

        if (pictureField.getText().length() < 1){
            setRedBorder(pictureField);
            this.errorLabel.setText("Please set a Picture Link");
            return false;
        }

        if (regionChoice.getValue() == null){
            setRedBorder(regionChoice);
            this.errorLabel.setText("Please choose a Region");
            return false;
        }

        if (rarityChoice.getValue() == null){
            setRedBorder(rarityChoice);
            this.errorLabel.setText("Please choose a Rarity");
            return false;
        }

        if (spellSpeedChoice.getValue() == null &&
                typeChoice.getValue().equals(Type.SPELL)){
            setRedBorder(spellSpeedChoice);
            this.errorLabel.setText("Please choose a Spell-Speed");
            return false;
        }

        try {
            intRangeValidator.validate(attackField.getText());
            intRangeValidator.validate(costField.getText());
            intRangeValidator.validate(healthField.getText());
        } catch (InvalidInputException e) {
            setRedBorder(attackField);
            setRedBorder(costField);
            setRedBorder(healthField);
            this.errorLabel.setText("Please enter a valid stats number (greater than 0 but not over 100");
            return false;
        }

        try {
            RegexValidator.URL_VALIDATOR.validate(pictureField.getText());
            RegexValidator.URL_VALIDATOR.validate(pictureFullField.getText());
        } catch (InvalidInputException e) {
            setRedBorder(pictureField);
            setRedBorder(pictureFullField);
            this.errorLabel.setText("Please enter a Valid Https Link pointing to an Image File");
            return false;
        }

        if (championBox.selectedProperty().get() && lvlUpDescriptionField.getText().length() < 1){
            setRedBorder(lvlUpDescriptionField);
            this.errorLabel.setText("Please enter a LvL-Up Description");
            return false;
        }
        return true;
    }

    @FXML
    protected void createButton() {

        if (validate()){
            SuperType superType;
            if (championBox.selectedProperty().get())
                superType = SuperType.CHAMPION;
            else superType = SuperType.NONE;

            Card localCard = new Card(descriptionField.getText(), nameField.getText(), pictureField.getText(), pictureFullField.getText(),
                    Integer.parseInt(attackField.getText()), Integer.parseInt(costField.getText()),
                    Integer.parseInt(healthField.getText()), rarityChoice.getValue(),
                    regionChoice.getValue(), typeChoice.getValue(), superType, spellSpeedChoice.getValue());
            App.getCardService().changeLvlUpDescription(localCard, lvlUpDescriptionField.getText());

            try {
                App.getCardService().downloadImage(localCard);
            } catch (IOException e) {
                setRedBorder(pictureField);
            }
            App.getCardService().addCard(localCard);
            App.getHelperClass().addSingeImage(localCard);
            switchToMainMenu();
            System.out.println(localCard);
        }
    }


    protected void handleTypeDecision(Type type){

        this.errorLabel.setText("");

        switch(type){
            case UNIT -> {
                healthField.setDisable(false);
                attackField.setDisable(false);
                spellSpeedChoice.setDisable(true);
                spellSpeedChoice.getSelectionModel().clearSelection();
                championBox.setVisible(true);
            }
            case ABILITY -> System.out.println("ABILITY");
            case LANDMARK -> {
                healthField.setText("0");
                attackField.setText("0");
                healthField.setDisable(true);
                attackField.setDisable(true);
                spellSpeedChoice.setDisable(true);

                championBox.setVisible(false);
                lvlUpDescriptionField.clear();
                lvlUpDescriptionField.setVisible(false);
                levelUpLabel.setVisible(false);
            }
            case SPELL ->  {
                healthField.setText("0");
                attackField.setText("0");
                healthField.setDisable(true);
                attackField.setDisable(true);
                spellSpeedChoice.setDisable(false);

                championBox.setVisible(false);
                lvlUpDescriptionField.clear();
                lvlUpDescriptionField.setVisible(false);
                levelUpLabel.setVisible(false);
            }
        }
    }

    protected void setStaticContent(){

        borderPane.setTop(getHeader());
        borderPane.setLeft(getLeft());

        typeChoice.getItems().setAll(Type.values());
        typeChoice.getItems().remove(3, 5);

        regionChoice.getItems().setAll(Region.values());
        rarityChoice.getItems().setAll(Rarity.values());
        spellSpeedChoice.getItems().setAll(SpellSpeed.values());
        spellSpeedChoice.setDisable(true);

        championBox.setVisible(false);
        lvlUpDescriptionField.setVisible(false);
        levelUpLabel.setVisible(false);

        typeChoice.setOnAction(actionEvent -> {
            Type selectedType = typeChoice.getValue();
            if (selectedType != null)
                handleTypeDecision(selectedType);
        });
    }

    @FXML
    protected void championBoxEvent() {

        if (championBox.selectedProperty().getValue().equals(true)){
            lvlUpDescriptionField.setVisible(true);
            levelUpLabel.setVisible(true);
        }
        else if (championBox.selectedProperty().getValue().equals(false)){
            lvlUpDescriptionField.clear();
            lvlUpDescriptionField.setVisible(false);
            levelUpLabel.setVisible(false);
        }
    }

    @FXML
    protected void keyTypedEvent(KeyEvent keyEvent) {

        this.errorLabel.setText("");
        System.out.println("Key typed event");
        resetRedBorder((Parent) keyEvent.getSource());
    }

    @Override
    public void initialize() {
    }

    @Override
    protected String getViewName() {
        return "CardCreatorParentController";
    }
}
