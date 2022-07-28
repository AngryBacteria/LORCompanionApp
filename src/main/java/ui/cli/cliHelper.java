package ui.cli;

import model.card.Card;
import model.card.subclasses.*;
import model.logging.Logger;
import model.validation.BooleanValidator;
import model.validation.EnumValidator;
import model.validation.IntRangeValidator;
import model.validation.RegexValidator;
import ui.cli.readers.ConsoleInput;

import java.io.IOException;
import java.util.List;

/**
 * The cliHelper Class holds all Methods needed for the CLI-Application that are called via the Menu that was
 * constructed in the mainCLI class.
 */
public class cliHelper {

    public static String spacer = "-----------------------------------";
    private final ConsoleInput<Integer> statInput = new ConsoleInput<>(new IntRangeValidator(100, 0), "Stat: ", mainCLI.pubScanner);
    private final ConsoleInput<String> cardCodeInput = new ConsoleInput<>(RegexValidator.CARDCODE_VALIDATOR, "CardCode: ", mainCLI.pubScanner);

    public cliHelper() {
    }

    public void searchCardsByName(){

        System.out.print("CardName: ");
        String cardName = mainCLI.pubScanner.nextLine();
        List<Card> cards = mainCLI.cardService.getCardsByName(cardName);
        cards.forEach(System.out::println);
    }

    public void searchCardsByCode(){

        String cardCode = this.cardCodeInput.enterValue();

        if (mainCLI.cardService.getCardByCode(cardCode).isPresent())
            System.out.println(mainCLI.cardService.getCardByCode(cardCode).get());
        else
            System.out.println("No card with Code [" + cardCode + "]");
    }

    public void searchCardsByRegion(){

        printEnum(Region.values());
        ConsoleInput<Region> regionConsoleInput =
                new ConsoleInput<>(new EnumValidator<>(Region.values()),"Region: ", mainCLI.pubScanner);
        mainCLI.cardService.getCardsByRegion(regionConsoleInput.enterValue()).forEach(System.out::println);
    }

    public void searchCardsBySet(){

        printEnum(Set.values());
        ConsoleInput<Set> regionConsoleInput =
                new ConsoleInput<>(new EnumValidator<>(Set.values()),"Set: ", mainCLI.pubScanner);
        mainCLI.cardService.getCardsBySet(regionConsoleInput.enterValue()).forEach(System.out::println);
    }

    public void cardsContain(){
        ConsoleInput<String> search = new ConsoleInput<>(RegexValidator.NOT_EMPTY_VALIDATOR, "Search: ", mainCLI.pubScanner);
        mainCLI.cardService.getCardsByContains(search.enterValue()).forEach(System.out::println);
    }

    public void searchCardsByHealth(){
        mainCLI.cardService.getCardsByHealth(statInput.enterValue()).forEach(System.out::println);
    }

    public void searchCardsByCost(){
        mainCLI.cardService.getCardsByCost(statInput.enterValue()).forEach(System.out::println);
    }

    public void searchCardsByAttack(){
        mainCLI.cardService.getCardsByCost(statInput.enterValue()).forEach(System.out::println);
    }

    public void addKeyword(){

        String cardCode = this.cardCodeInput.enterValue();
        if (mainCLI.cardService.getCardByCode(cardCode).isPresent()){
            printEnum(Keyword.values());
            Keyword keyword = new ConsoleInput<>(new EnumValidator<>(Keyword.values()),"Keyword: ", mainCLI.pubScanner).enterValue();
            mainCLI.cardService.getCardByCode(cardCode).get().addKeyword(keyword);
        }
        else
            System.out.printf("Card with Code %s doesn't exist%n", cardCode);
    }

    public void removeKeyword(){

        String cardCode = this.cardCodeInput.enterValue();
        if (mainCLI.cardService.getCardByCode(cardCode).isPresent()){
            printEnum(Keyword.values());
            Keyword keyword = new ConsoleInput<>(new EnumValidator<>(Keyword.values()),"Keyword: ", mainCLI.pubScanner).enterValue();
            mainCLI.cardService.getCardByCode(cardCode).get().removeKeyword(keyword);
        }
        else
            System.out.printf("Card with Code %s doesn't exist%n", cardCode);
    }

    public void addAssociated(){

        String cardCode = this.cardCodeInput.enterValue();
        if (mainCLI.cardService.getCardByCode(cardCode).isPresent()){
            String cardCode2 = this.cardCodeInput.enterValue();

            if (mainCLI.cardService.getCardByCode(cardCode2).isPresent())
                mainCLI.cardService.getCardByCode(cardCode).get().addAssociatedCard(cardCode2);
            else
                System.out.printf("Card with Code %s doesn't exist%n", cardCode2);
        }
        else
            System.out.printf("Card with Code %s doesn't exist%n", cardCode);
    }

    public void removeAssociated(){

        String cardCode = this.cardCodeInput.enterValue();
        if (mainCLI.cardService.getCardByCode(cardCode).isPresent()){

            mainCLI.cardService.getCardByCode(cardCode).get().getAssociatedCardRefs().forEach(s -> System.out.println("-" + s));
            String cardCode2 = new ConsoleInput<>(RegexValidator.CARDCODE_VALIDATOR, "Card code of Card you want to add: ", mainCLI.pubScanner).enterValue();
            if (mainCLI.cardService.getCardByCode(cardCode2).isPresent())
                mainCLI.cardService.getCardByCode(cardCode).get().addAssociatedCard(cardCode2);
            else
                System.out.printf("Card with Code %s doesn't exist%n", cardCode2);
        }
        else
            System.out.printf("Card with Code %s doesn't exist%n", cardCode);
    }

    public void createCard(){

        System.out.println("""
                0: Unit
                1: Champion
                2: Spell
                3: Landmark""");

        ConsoleInput<Integer> decision = new ConsoleInput<>(new IntRangeValidator(3, 0), "Decision: ", mainCLI.pubScanner);
        int intDecision = decision.enterValue();

        String name = new ConsoleInput<>(RegexValidator.NOT_EMPTY_VALIDATOR, "Name: ", mainCLI.pubScanner).enterValue();
        String description = new ConsoleInput<>(RegexValidator.NOT_EMPTY_VALIDATOR, "Description: ", mainCLI.pubScanner).enterValue();

        int attack = new ConsoleInput<>(new IntRangeValidator(100, 0), "Attack: ", mainCLI.pubScanner).enterValue();
        int cost = new ConsoleInput<>(new IntRangeValidator(100, 0), "Cost: ", mainCLI.pubScanner).enterValue();
        int health = new ConsoleInput<>(new IntRangeValidator(100, 0), "Health: ", mainCLI.pubScanner).enterValue();

        printEnum(Region.values());
        Region region = new ConsoleInput<>(new EnumValidator<>(Region.values()),"Region: ", mainCLI.pubScanner).enterValue();

        printEnum(Rarity.values());
        Rarity rarity = new ConsoleInput<>(new EnumValidator<>(Rarity.values()),"Rarity: ", mainCLI.pubScanner).enterValue();

        Type type = null;
        SpellSpeed spellSpeed = null;
        SuperType superType = null;
        String lvlUpDescription = "";

        if (intDecision == 0){
            type = Type.UNIT;
            spellSpeed = SpellSpeed.NONE;
            superType = SuperType.NONE;
        }

        if (intDecision == 1){
            type = Type.UNIT;
            spellSpeed = SpellSpeed.NONE;
            superType = SuperType.CHAMPION;
            lvlUpDescription = new ConsoleInput<>(RegexValidator.NOT_EMPTY_VALIDATOR, "LvL Up Description: ", mainCLI.pubScanner).enterValue();
        }

        if (intDecision == 2){
            type = Type.SPELL;
            printEnum(SpellSpeed.values());
            spellSpeed = new ConsoleInput<>(new EnumValidator<>(SpellSpeed.values()),"Spellspeed: ", mainCLI.pubScanner).enterValue();
            superType = SuperType.NONE;
        }

        if (intDecision == 3){
            type = Type.LANDMARK;
            spellSpeed = SpellSpeed.NONE;
            superType = SuperType.NONE;
        }

        String pictureURL = new ConsoleInput<>(RegexValidator.URL_VALIDATOR, "Game-Picture URL: ", mainCLI.pubScanner).enterValue();
        String fullUrl = new ConsoleInput<>(RegexValidator.URL_VALIDATOR, "Full-Art Picture URL: ", mainCLI.pubScanner).enterValue();
        Card localCard = new Card(description, name, pictureURL, fullUrl, attack, cost, health, rarity, region, type, superType, spellSpeed);
        mainCLI.cardService.changeLvlUpDescription(localCard, lvlUpDescription);

        try {
            mainCLI.cardService.downloadImage(localCard);
        } catch (IOException e) {
            mainCLI.cliLogger.writeLog(Logger.Operation.EXCEPTION, "Image File couldn't be downloaded");
            System.out.println("Card Picture couldn't be downloaded, try again");
            return;
        }
        mainCLI.cardService.addCard(localCard);
    }

    public void amountOfCards(){

        long amountOfCardsCollectible = mainCLI.cardService.getCardArray().stream()
                .filter(Card::isCollectible)
                .count();
        long amountOfCardsNotCollectible = mainCLI.cardService.getCardArray().stream()
                .filter(card -> !card.isCollectible())
                .count();
        System.out.printf("Collectible Cards: %s\nNot Collectible Cards: %s\n", amountOfCardsCollectible, amountOfCardsNotCollectible);
    }

    public void amountOfCardsSummary(){

        long amountOfChampions = mainCLI.cardService.getCardArray().stream()
                .filter(Card::isChampion)
                .count();
        long amountOfFollowers = mainCLI.cardService.getCardArray().stream()
                .filter(card -> card.getType().equals(Type.UNIT) && !card.isChampion())
                .count();
        long amountOfLandmarks = mainCLI.cardService.getCardArray().stream()
                .filter(card -> card.getType().equals(Type.LANDMARK))
                .count();
        long amountOfSpells = mainCLI.cardService.getCardArray().stream()
                .filter(card -> card.getType().equals(Type.SPELL))
                .count();

        System.out.printf("Champions: %s\nFollowers: %s\nLandmarks: %s\nSpells: %s\n", amountOfChampions, amountOfFollowers, amountOfLandmarks, amountOfSpells);
    }

    public void changeCardPicture(){

        String cardCode = this.cardCodeInput.enterValue();
        if (mainCLI.cardService.getCardByCode(cardCode).isPresent()){
            Card localCard = mainCLI.cardService.getCardByCode(cardCode).get();
            Boolean addFullGameArt = new ConsoleInput<>(new BooleanValidator(), "Do you want to set a full Art Image?", mainCLI.pubScanner).enterValue();

            if (addFullGameArt){
                String fullUrl = new ConsoleInput<>(RegexValidator.URL_VALIDATOR, "Full-Art Picture URL: ", mainCLI.pubScanner).enterValue();
                mainCLI.cardService.changePicture(localCard, fullUrl, false);
            }
            else {
                String fullUrl = new ConsoleInput<>(RegexValidator.URL_VALIDATOR, "Game-Art Picture URL: ", mainCLI.pubScanner).enterValue();
                mainCLI.cardService.changePicture(localCard, fullUrl, true);
            }
        }
        else
            System.out.println("Card not found");
    }

    public <E extends Enum<E>> void printEnum(E[] enumValues){

        System.out.printf("Please choose one of those %s options", enumValues.length);
        for (Enum<E> eEnum : enumValues){
            System.out.println("-" + eEnum);
        }
    }
}
