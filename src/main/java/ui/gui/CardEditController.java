package ui.gui;

import javafx.fxml.FXML;
import model.card.Card;
import model.card.subclasses.*;
import model.logging.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller that is used by the CardEdit scene.
 */
public class CardEditController extends CardCreatorParentController{

    private Card card;
    @FXML
    public void initialize(){

        this.card = App.getHelperClass().getCard();
        setStaticContent();
        fillCardInfo();
    }

    public void fillCardInfo(){
        regionChoice.setValue(card.getRegions().get(0));
        typeChoice.setValue(card.getType());
        rarityChoice.setValue(card.getRarity());
        spellSpeedChoice.setValue(card.getSpellSpeed());
        nameField.setText(card.getName());

        descriptionField.setText(card.getDescriptionRaw());
        attackField.setText(String.valueOf(card.getAttack()));
        costField.setText(String.valueOf(card.getCost()));
        healthField.setText(String.valueOf(card.getHealth()));
        pictureField.setText(card.getAssets().getGameAbsolutePath());
        pictureFullField.setText(card.getAssets().getFullAbsolutePath());
        lvlUpDescriptionField.setText(card.getLevelupDescriptionRaw());

        if (card.isChampion()){
            championBox.selectedProperty().set(true);
            championBoxEvent();
        }
        handleTypeDecision(card.getType());
    }

    @Override
    protected String getViewName() {
        return "CardCreator";
    }

    public void editButtonEvent() {

        if (validate()){
            SuperType superType;
            if (championBox.selectedProperty().get())
                superType = SuperType.CHAMPION;
            else superType = SuperType.NONE;

            List<Asset> assetList = new ArrayList<>();
            assetList.add(new Asset(pictureField.getText(), pictureFullField.getText()));

            List<Region> regionList = card.getRegions();
            if (!regionList.contains(regionChoice.getValue())){
                regionList.add(regionChoice.getValue());
            }

            Card localCard = new Card(descriptionField.getText(), lvlUpDescriptionField.getText(), card.getFlavorText(),
                    card.getArtistName(), nameField.getText(), card.getCardCode(), card.getAssociatedCardRefs(), assetList, card.getKeywords(),
                    Integer.parseInt(attackField.getText()), Integer.parseInt(costField.getText()), Integer.parseInt(healthField.getText()),
                    card.isCollectible(), rarityChoice.getValue(), card.getSet(), regionList, typeChoice.getValue(), superType, spellSpeedChoice.getValue(), card.getSubtypes());

            App.getCardService().removeCard(card);
            App.getHelperClass().removeSingeImage(card);
            try {
                App.getCardService().downloadImage(localCard);
            } catch (IOException e) {
                App.logger.writeLog(Logger.Operation.EXCEPTION, "Picture files couldn't be downloaded");
            }
            App.getCardService().addCard(localCard);
            App.getHelperClass().addSingeImage(localCard);
            switchToMainMenu();
        }
    }
}
