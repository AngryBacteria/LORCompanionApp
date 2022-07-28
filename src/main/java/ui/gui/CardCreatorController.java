package ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller for the CardCreation Scene. With this Scene Cards can be created from Scratch by the User.
 */
public class CardCreatorController extends CardCreatorParentController{

    @FXML
    public void initialize(){
        this.setStaticContent();
    }

    @Override
    protected String getViewName() {
        return "CardCreator";
    }
}
