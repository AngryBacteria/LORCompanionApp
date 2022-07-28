package ui.cli;

import model.card.CardService;
import model.logging.Logger;
import ui.cli.menu.Menu;
import ui.cli.menu.MenuItem;
import ui.gui.App;

import java.util.Scanner;

/**
 * Main CLI-Class holding all the important Fields (Logger, Scanner and CardInfo) for the CLI Application.
 * The Menu is constructed here too.
 */
public class mainCLI {

    public static Logger cliLogger = new Logger("CardCLILog.txt");
    public static Scanner pubScanner = new Scanner(System.in);
    public static cliHelper cliHelper = new cliHelper();
    public static CardService cardService = new CardService(false);

    public static void main(String[] args) {

        cliLogger.writeLog(Logger.Operation.STARTUP, "CLI-Application started up");

        //Query menu
        Menu queryMenu = new Menu(pubScanner, 1);
        queryMenu.addMenuItem(new MenuItem(1, "Search cards by Name", ()->cliHelper.searchCardsByName()));
        queryMenu.addMenuItem(new MenuItem(2, "Search cards by Region", ()->cliHelper.searchCardsByRegion()));
        queryMenu.addMenuItem(new MenuItem(3, "Search cards by Set", ()->cliHelper.searchCardsBySet()));
        queryMenu.addMenuItem(new MenuItem(4, "Search cards by CardCode", ()->cliHelper.searchCardsByCode()));
        queryMenu.addMenuItem(new MenuItem(5, "Search cards with a Search term", ()->cliHelper.cardsContain()));
        queryMenu.addMenuItem(new MenuItem(6, "Search cards by Health", ()->cliHelper.searchCardsByHealth()));
        queryMenu.addMenuItem(new MenuItem(7, "Search cards by Cost", ()->cliHelper.searchCardsByCost()));
        queryMenu.addMenuItem(new MenuItem(8, "Search cards by Attack", ()->cliHelper.searchCardsByAttack()));

        //Changing menu
        Menu changeMenu = new Menu(pubScanner, 1);
        changeMenu.addMenuItem(new MenuItem(1, "Add Keyword", ()->cliHelper.addKeyword()));
        changeMenu.addMenuItem(new MenuItem(2, "Remove Keyword", ()->cliHelper.removeKeyword()));
        changeMenu.addMenuItem(new MenuItem(3, "Add Associated Card", ()->cliHelper.addAssociated()));
        changeMenu.addMenuItem(new MenuItem(4, "Remove Associated Card", ()->cliHelper.removeAssociated()));
        changeMenu.addMenuItem(new MenuItem(5, "Create Card", ()->cliHelper.createCard()));
        changeMenu.addMenuItem(new MenuItem(6, "Edit Card-Picture", ()->cliHelper.changeCardPicture()));

        //Debug menu
        Menu debugMenu = new Menu(pubScanner, 1);
        debugMenu.addMenuItem(new MenuItem(1, "Read CLI-Log", ()-> cliLogger.getFullLog()));
        debugMenu.addMenuItem(new MenuItem(2, "Read GUI-Log", ()-> App.logger.getFullLog()));

        //Info Menu
        Menu infoMenu = new Menu(pubScanner, 1);
        infoMenu.addMenuItem(new MenuItem(1, "Amount of cards", ()-> cliHelper.amountOfCards()));
        infoMenu.addMenuItem(new MenuItem(2, "Amount of cards summary", ()-> cliHelper.amountOfCardsSummary()));

        //Main menu
        Menu mainMenu = new Menu(pubScanner, 0);
        mainMenu.addMenuItem(new MenuItem(1, "Queries", queryMenu));
        mainMenu.addMenuItem(new MenuItem(2, "Changing", changeMenu));
        mainMenu.addMenuItem(new MenuItem(3, "Info", infoMenu));
        mainMenu.addMenuItem(new MenuItem(4, "Debug", debugMenu));

        mainMenu.run();
        cliLogger.writeLog(Logger.Operation.SHUTDOWN, "CLI-Application shutdown");
        cardService.writeCardsToJson();
    }

}
