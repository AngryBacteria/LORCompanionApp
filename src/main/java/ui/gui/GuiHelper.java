package ui.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.card.Card;
import model.card.CardService;
import model.logging.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper Service Class for various Controllers. Mainly it handles the ImageViewMap for the CardGallery Controller
 * and the Card object needed to edit cards with the CardEditController.
 */
public class GuiHelper {

    private final Map<String, ImageView> imageViewMap = new HashMap<>();
    private Card card;

    public void initiateImageViews(){

        App.getCardService().getCardArray().stream()
                .filter(Card::isCollectible)
                .forEach(card -> {
                    try {
                        String url = String.format(CardService.pictureFolderPath +"%s.png", card.getCardCode());
                        this.imageViewMap.put(card.getCardCode(), this.createImageView(url, card.getCardCode()));
                    } catch (Exception e) {
                        System.out.println("Picture file not found [CardCode = " + card.getCardCode() + "]");
                        App.logger.writeLog(Logger.Operation.EXCEPTION, "Picture file not found [CardCode = " + card.getCardCode() + "]");
                    }
                });
    }

    public void resetImageViews(){
        this.imageViewMap.clear();
        this.initiateImageViews();
    }

    public void initiateMissingImageViews(){
        App.getCardService().getCardArray().stream()
                .filter(Card::isCollectible)
                .forEach(card -> {
                    if (!this.imageViewMap.containsKey(card.getCardCode())){
                        try {
                            String url = String.format(CardService.pictureFolderPath +"%s.png", card.getCardCode());
                            this.imageViewMap.put(card.getCardCode(), this.createImageView(url, card.getCardCode()));
                        } catch (Exception e) {
                            System.out.println("Picture file not found [CardCode = " + card.getCardCode() + "]");
                            App.logger.writeLog(Logger.Operation.EXCEPTION, "Picture file not found [CardCode = " + card.getCardCode() + "]");
                        }
                    }
                });
    }

    private ImageView createImageView(String url, String id){

        //Image image = new Image(url, 220, 306, true, true, false);
        Image image = new Image(url, 260, 346, true, true, false);
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setId(id);
        return imageView;
    }

    public void addSingeImage(Card card){
        String url = String.format(CardService.pictureFolderPath +"%s.png", card.getCardCode());
        this.imageViewMap.put(card.getCardCode(), this.createImageView(url, card.getCardCode()));
    }

    public void removeSingeImage(Card card){
        String url = String.format(CardService.pictureFolderPath +"%s.png", card.getCardCode());
        this.imageViewMap.remove(card.getCardCode(), this.createImageView(url, card.getCardCode()));
    }

    public Map<String, ImageView> getImageViewMap() {
        return this.imageViewMap;
    }

    public Card getCard() {
        return this.card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
