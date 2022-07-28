package model.card;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.card.subclasses.Set;
import model.card.subclasses.*;
import model.validation.RegexValidator;
import model.validation.exceptions.InvalidInputException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Service Class for the Card Objects. It grabs the Card Objects either from the Original json files or the Json file
 * created by the Service itself. Hence the true/false in the Constructor. It supports many queries to get Cards or
 * change them.
 */
public class CardService {

    private final ArrayList<Card> cardArray;
    private final Map<String, Card> cardMap;
    public static final Card nicocard = new Card("Nico the destroyer", "Nico", "https://i0.wp.com/runeterraccg.com/wp-content/uploads/custom-2.png?resize=980%2C450&ssl=1", 1, 2, 3, Rarity.CHAMPION, Region.BANDLECITY, Type.UNIT, SuperType.CHAMPION, SpellSpeed.NONE);

    public static String mainFolderPath = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "lorCompanion" + File.separator;
    public static String pictureFolderPath = mainFolderPath + "cardpictures/";
    public static String logsPath = mainFolderPath + "logs/";

    public CardService(Boolean fromOriginals) {

        createDirectories();
        if (fromOriginals)
            this.cardArray = CardIndexing.getCardArrayFromArrays();
        else
            this.cardArray = readCardJson();

        this.cardMap = cardArray.stream().collect(Collectors.toMap(Card::getCardCode, item -> item));
    }

    //Queries
    public List<Card> getCardsByName(String name){
        return this.cardArray.stream()
                .filter(card -> card.getName().equalsIgnoreCase(name))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Card> getCardsByContains(String searchWord){
        return this.cardArray.stream()
                .filter(card -> card.cardContains(searchWord))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Optional<Card> getCardByCode(String code){

        if (cardMap.containsKey(code))
            return Optional.of(cardMap.get(code));
        return Optional.empty();
    }

    public List<Card> getCardsByAttack(int attack){
        return this.cardArray.stream()
                .filter(card -> card.getAttack() == attack)
                .sorted(Comparator.comparingInt(Card::getAttack))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Card> getCardsByHealth(int health){
        return this.cardArray.stream()
                .filter(card -> card.getHealth() == health)
                .sorted(Comparator.comparingInt(Card::getAttack))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Card> getCardsByCost(int cost){
        return this.cardArray.stream()
                .filter(card -> card.getCost() == cost)
                .sorted(Comparator.comparingInt(Card::getAttack))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Card> getCardsByType(Type type){
        return this.cardArray.stream()
                .filter(card -> card.getType().equals(type))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Card> getCardsBySuperType(SuperType superType){
        return this.cardArray.stream()
                .filter(card -> card.getSupertype() == superType)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Card> getCardsBySet(Set set){
        return this.cardArray.stream()
                .filter(card -> card.getSet().equals(set))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Card> getCardsByRegion(Region region){
        return this.cardArray.stream()
                .filter(card -> card.getRegions().contains(region))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Card> getAssociatedCards(Card card){

        List<Card> output = new ArrayList<>();
        for (String cardCode : card.getAssociatedCardRefs()){
            output.add(this.cardMap.get(cardCode));
        }
        return output;
    }

    public List<Card> getAssociatedCards(String code){

        Optional<Card> card = getCardByCode(code);
        List<Card> output = new ArrayList<>();
        if (card.isPresent()){
            for (String cardCode : card.get().getAssociatedCardRefs()){
                output.add(this.cardMap.get(cardCode));
            }
        }
        return output;
    }

    //Helper Methods
    public Optional<Region> getRegionByName(String name){
        name = name.trim();
        for (Region currentRegion : Region.values()){
            if(currentRegion.getRegionFull().equalsIgnoreCase(name) ||
                    currentRegion.getAbbreviation().equalsIgnoreCase(name) ||
                    currentRegion.name().equalsIgnoreCase(name))
                return Optional.of(currentRegion);
        }
        return Optional.empty();
    }

    public Optional<Keyword> getKeyWordByName(String name){
        name = name.trim();
        for (Keyword currentKeyword : Keyword.values()){
            if (currentKeyword.getNameRef().equalsIgnoreCase(name) || currentKeyword.name().equalsIgnoreCase(name))
                return Optional.of(currentKeyword);
        }
        return Optional.empty();
    }

    public List<Card> getCardArray() {
        return Collections.unmodifiableList(cardArray);
    }

    public Map<String, Card> getCardMap() {
        return Collections.unmodifiableMap(cardMap);
    }

    public ObservableList<Card> getObservableList(){
        return FXCollections.observableList(this.cardArray);
    }

    public ObservableList<Card> getObservableListByCardNames(String name){

        List<Card> outputList = this.cardArray.stream()
                .filter(card -> card.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());

        return FXCollections.observableList(outputList);
    }

    //Card Methods
    public void addCard(Card card){

        if (!this.cardMap.containsKey(card.getCardCode())){
            this.cardArray.add(card);
            this.cardMap.put(card.getCardCode(), card);
        }
    }

    public void removeCard(Card card){
        this.cardArray.remove(card);
        this.cardMap.remove(card.getCardCode(), card);
    }

    public void addKeyword(Card card, Keyword keyword) throws IllegalArgumentException {
            if (this.cardMap.containsKey(card.getCardCode()))
                card.addKeyword(keyword);
            else
                throw new IllegalArgumentException("Card [" + card.getCardCode() + "] is not in CardService");
    }

    public void removeKeyword(Card card, Keyword keyword) throws IllegalArgumentException {
        if (this.cardMap.containsKey(card.getCardCode()))
            card.removeKeyword(keyword);
        else
            throw new IllegalArgumentException("Card [" + card.getCardCode() + "] is not in CardService");
    }

    public void addRegion(Card card, Region region) throws IllegalArgumentException {
        if (this.cardMap.containsKey(card.getCardCode()))
            card.addRegion(region);
        else
            throw new IllegalArgumentException("Card [" + card.getCardCode() + "] is not in CardService");
    }

    public void removeRegion(Card card, Region region) throws IllegalArgumentException {
        if (this.cardMap.containsKey(card.getCardCode()))
            card.removeRegion(region);
        else
            throw new IllegalArgumentException("Card [" + card.getCardCode() + "] is not in CardService");
    }

    public void addAssociatedCard(Card receiver, Card associate) throws IllegalArgumentException {
        if (this.cardMap.containsKey(receiver.getCardCode()) && this.cardMap.containsKey(associate.getCardCode()))
            receiver.addAssociatedCard(associate.getCardCode());
        else
            throw new IllegalArgumentException("Cards are not in CardService");
    }

    public void removeAssociatedCard(Card receiver, Card associate) throws IllegalArgumentException {
        if (this.cardMap.containsKey(receiver.getCardCode()) && this.cardMap.containsKey(associate.getCardCode()))
            receiver.removeAssociatedCard(associate.getCardCode());
        else
            throw new IllegalArgumentException("Cards are not in CardService");
    }

    public void changePicture(Card card, String url, boolean game){

            try {
                RegexValidator.URL_VALIDATOR.validate(url);
                card.changePicture(url, game);
            }
            catch (InvalidInputException e) {
                System.out.println("Url isn't valid");
            }
    }

    public void changeLvlUpDescription(Card card, String description){

        if (this.cardMap.containsKey(card.getCardCode())){
            card.changeLvLUpDescription(description);
        }
    }

    public void downloadImages(){
        getCardArray().forEach(card -> {
            String cardName = card.getCardCode() + ".png";
            String imageUrl = String.format("https://dd.b.pvp.net/latest/%s/en_us/img/cards/%s",
                    card.getSet().toString().toLowerCase(), cardName);

            System.out.println(imageUrl);

            try {

                File file = new File(CardService.pictureFolderPath + cardName);
                if (!file.exists()){
                    URL url = new URL(imageUrl);
                    BufferedImage img = ImageIO.read(url);
                    ImageIO.write(img, "png", file);
                }

                else
                    System.out.println("Image exists already");

            } catch (IOException e) {
                System.out.println("Error when downloading image into cardimages folder");
            }
        });
    }

    public void downloadImagesFull(){

        getCardArray().forEach(card -> {
            String cardName = card.getCardCode() + "-full" + ".png";
            String imageUrl = String.format("https://dd.b.pvp.net/latest/%s/en_us/img/cards/%s",
                    card.getSet().toString().toLowerCase(), cardName);

            System.out.println(imageUrl);

            try {

                File file = new File(CardService.pictureFolderPath + cardName);
                if (!file.exists()){
                    URL url = new URL(imageUrl);
                    BufferedImage img = ImageIO.read(url);
                    ImageIO.write(img, "png", file);
                }

                else
                    System.out.println("Image exists already");

            } catch (IOException e) {
                System.out.println("Error when downloading image into cardimages folder");
            }
        });
    }

    public void downloadImage(Card card) throws IOException{

        String imageFileName = card.getCardCode() + ".png";
        String imageFileNameFull = card.getCardCode() + "-full.png";

        try {

            File file = new File(CardService.pictureFolderPath + imageFileName);
            URL url = new URL(card.getAssets().getGameAbsolutePath());
            BufferedImage img = ImageIO.read(url);
            ImageIO.write(img, "png", file);
            System.out.println("Downloaded Game Image for Card " + card.getCardCode());

            file = new File(CardService.pictureFolderPath + imageFileNameFull);
            url = new URL(card.getAssets().getFullAbsolutePath());
            img = ImageIO.read(url);
            ImageIO.write(img, "png", file);
            System.out.println("Downloaded Full Image for Card " + card.getCardCode());


        } catch (IOException e) {
            System.out.println("Error when downloading image into cardimages folder");
            throw new IOException("Card doesn't have an Image because download failed");
        }
    }

    public void writeCardsToJson(){

        try(FileWriter fileWriter = new FileWriter(CardService.mainFolderPath + "cards.json"); BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
            Gson gson = gsonBuilder.create();
            gson.toJson(getCardArray(), bufferedWriter);
        }
        catch (IOException e) {
            System.out.println("Error when writing json file");
        }
    }

    private ArrayList<Card> readCardJson (){

        try(FileReader fileReader = new FileReader(CardService.mainFolderPath + "cards.json") ; BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            Gson gson = new Gson();
            return gson.fromJson(bufferedReader, new TypeToken<List<Card>>(){}.getType());
        }

        catch (IOException e) {
            System.out.println("Error when reading jsons");
            throw new IllegalStateException("Aborting because cards couldn't be loaded");
        }
    }

    public void createDirectories(){

        try {
            Path path = Paths.get(mainFolderPath);
            if (!Files.exists(path))
                Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error when creating Main Folder");
        }

        try {
            Path path = Paths.get(pictureFolderPath);
            if (!Files.exists(path))
                Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error when creating Pictures Folder");
        }

        try {
            Path path = Paths.get(logsPath);
            if (!Files.exists(path))
                Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error when creating Main Folder");
        }
    }

    public static void main(String[] args) {

        CardService cardService = new CardService(true);
        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.execute(cardService::downloadImagesFull);
        executor.execute(cardService::downloadImages);
        executor.shutdown();
    }
}
