package model.card;

import model.card.subclasses.Set;
import model.card.subclasses.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Card Class, contains all important fields from the CardGame. Includes several methods for getting Infos about the Cards
 * and their Fields. Most fields are Enums which are in their own classes.
 */
public class Card {

    //Card fields. Most I will leave not final, as in the future if I still work on this project, changing them with
    //Setters might be something I want to do.
    //Strings
    private String descriptionRaw;
    private String levelupDescriptionRaw;
    private String flavorText;
    private String artistName;
    private String name;
    private String cardCode;

    //Lists
    private List<Asset> assets;
    private List<Keyword> keywords;
    private List<String> associatedCardRefs;
    private List<Region> regions;

    private List<SubType> subtypes;

    //Numbers
    private int attack;
    private int cost;
    private int health;

    //Booleans and Enums
    private boolean collectible;
    private Rarity rarity;
    private Set set;
    private Type type;
    private SuperType supertype;

    //Spell specific
    private SpellSpeed spellSpeed;


    public Card(String descriptionRaw, String levelupDescriptionRaw, String flavorText, String artistName, String name, String cardCode, List<String> associatedCardRefs, List<Asset> assets, List<Keyword> keywords, int attack, int cost, int health, boolean collectible, Rarity rarity, Set set, List<Region> regions, Type type, SuperType supertype, SpellSpeed spellSpeed, List<SubType> subTypes) {
        this.descriptionRaw = descriptionRaw;
        this.levelupDescriptionRaw = levelupDescriptionRaw;
        this.flavorText = flavorText;
        this.artistName = artistName;
        this.name = name;
        this.cardCode = cardCode;
        this.associatedCardRefs = associatedCardRefs;
        this.assets = assets;
        this.keywords = keywords;
        this.attack = attack;
        this.cost = cost;
        this.health = health;
        this.collectible = collectible;
        this.rarity = rarity;
        this.set = set;
        this.regions = regions;
        this.type = type;
        this.supertype = supertype;
        this.spellSpeed = spellSpeed;
        this.subtypes = subTypes;
    }

    //Constructor for custom Cards
    public Card(String decriptionRaw, String name, String pictureURL, int attack, int cost, int health, Rarity rarity, Region region, Type type, SuperType supertype, SpellSpeed spellSpeed) {
        this.descriptionRaw = decriptionRaw;
        this.levelupDescriptionRaw = "";
        this.flavorText = "";
        this.artistName = "";
        this.name = name;
        this.associatedCardRefs = new ArrayList<>();
        this.assets = new ArrayList<>();
        this.keywords = new ArrayList<>();
        this.attack = attack;
        this.cost = cost;
        this.health = health;
        this.collectible = true;
        this.rarity = rarity;
        this.set = Set.CUSTOM;
        this.regions = new ArrayList<>();
        this.type = type;
        this.supertype = supertype;
        this.spellSpeed = spellSpeed;

        this.regions.add(region);
        this.assets.add(new Asset(pictureURL, pictureURL));
        int randomNum = ThreadLocalRandom.current().nextInt(1, 99999);
        this.cardCode = String.format("0%d%s0%d", this.set.getNumber(), this.regions.get(0).getAbbreviation(), randomNum);
    }

    public Card(String decriptionRaw, String name, String pictureGameURL, String pictureFullURL, int attack, int cost, int health, Rarity rarity, Region region, Type type, SuperType supertype, SpellSpeed spellSpeed) {
        this.descriptionRaw = decriptionRaw;
        this.levelupDescriptionRaw = "";
        this.flavorText = "";
        this.artistName = "";
        this.name = name;
        this.associatedCardRefs = new ArrayList<>();
        this.assets = new ArrayList<>();
        this.keywords = new ArrayList<>();
        this.attack = attack;
        this.cost = cost;
        this.health = health;
        this.collectible = true;
        this.rarity = rarity;
        this.set = Set.CUSTOM;
        this.regions = new ArrayList<>();
        this.type = type;
        this.supertype = supertype;
        this.spellSpeed = spellSpeed;

        this.regions.add(region);
        this.assets.add(new Asset(pictureGameURL, pictureFullURL));
        int randomNum = ThreadLocalRandom.current().nextInt(1, 99999);
        this.cardCode = String.format("0%d%s0%d", this.set.getNumber(), this.regions.get(0).getAbbreviation(), randomNum);
    }

    //Setters
    public void addKeyword(Keyword keyword){
        if (!this.keywords.contains(keyword))
            this.keywords.add(keyword);
    }

    public void removeKeyword(Keyword keyword){
        this.keywords.remove(keyword);
    }

    public void addRegion(Region region){
        if (!this.regions.contains(region) && this.regions.size() < 3)
            this.regions.add(region);
    }

    public void removeRegion(Region region){
        this.regions.remove(region);
    }

    public void addAssociatedCard(String cardCode){
        if (!this.getAssociatedCardRefs().contains(cardCode))
            this.getAssociatedCardRefs().add(cardCode);
    }

    public void changePicture(String url, boolean game){

        if (game)
            this.getAssets().setGameAbsolutePath(url);
        else
            this.getAssets().setFullAbsolutePath(url);
    }

    public void changeLvLUpDescription(String levelupDescription){
        this.levelupDescriptionRaw = levelupDescription;
    }

    public void removeAssociatedCard(String cardCode){
        this.getAssociatedCardRefs().remove(cardCode);
    }

    //Getters
    public String getDescriptionRaw() {
        return this.descriptionRaw;
    }

    public String getLevelupDescriptionRaw() {
        return this.levelupDescriptionRaw;
    }

    public String getFlavorText() {
        return this.flavorText;
    }

    public String getArtistName() {
        return this.artistName;
    }

    public String getName() {
        return this.name;
    }

    public String getCardCode() {
        return this.cardCode;
    }

    public Set getSet() {
        return this.set;
    }

    public List<Keyword> getKeywords() {
        return Collections.unmodifiableList(this.keywords);
    }

    public int getAttack() {
        return this.attack;
    }

    public int getCost() {
        return this.cost;
    }

    public int getHealth() {
        return this.health;
    }

    public List<Region> getRegions() {
        return Collections.unmodifiableList(this.regions);
    }

    public Asset getAssets() {
        return this.assets.get(0);
    }
    public List<Asset> getAllAssets(){
        return this.assets;
    }

    public boolean isCollectible() {
        return this.collectible;
    }

    public Rarity getRarity() {
        return this.rarity;
    }

    public Type getType() {
        return this.type;
    }

    public SuperType getSupertype() {
        return this.supertype;
    }

    public List<String> getAssociatedCardRefs() {
        return Collections.unmodifiableList(this.associatedCardRefs);
    }

    public SpellSpeed getSpellSpeed() {
        return this.spellSpeed;
    }

    public boolean isChampion(){
        return this.getType().equals(Type.UNIT) && this.supertype.equals(SuperType.CHAMPION);
    }

    public List<SubType> getSubtypes() {
        return subtypes;
    }

    public boolean hasKeywordSearch(String keywordSearch){

        for (Keyword keyword : this.getKeywords()){
            if (keyword.name().replace("_", " ").equalsIgnoreCase(keywordSearch) ||
                    keyword.getNameRef().equalsIgnoreCase(keywordSearch)){
                return true;
            }
        }
        return false;
    }

    public boolean hasRegionSearch(String regionSearch){

        for (Region region : this.getRegions()){
            if (region.name().equalsIgnoreCase(regionSearch) ||
                    region.getRegionFull().equalsIgnoreCase(regionSearch) ||
                    region.getAbbreviation().equalsIgnoreCase(regionSearch)){
                return true;
            }
        }
        return false;
    }

    private boolean hasSubTypeSearch(String search){

        if (subtypes.size()>1){
            for(SubType subType : subtypes){
                if (subType.name().equalsIgnoreCase(search))
                    return true;
            }
        }
        return false;
    }

    public Boolean cardContains(String descriptionSearch){
        descriptionSearch = descriptionSearch.toLowerCase(Locale.ROOT);
        return (this.getName().equalsIgnoreCase(descriptionSearch) ||
                this.getCardCode().equalsIgnoreCase(descriptionSearch) ||
                this.getDescriptionRaw().toLowerCase(Locale.ROOT).contains(descriptionSearch) ||
                this.getLevelupDescriptionRaw().toLowerCase(Locale.ROOT).contains(descriptionSearch) ||
                this.hasKeywordSearch(descriptionSearch) ||
                this.hasRegionSearch(descriptionSearch) ||
                this.hasSubTypeSearch(descriptionSearch) ||
                this.artistName.equalsIgnoreCase(descriptionSearch));
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + this.name + '\'' +
                ", cardCode='" + this.cardCode + '\'' +
                ", set='" + this.set + '\'' +
                ", rarity='" + this.rarity + '\'' +
                ", attack=" + this.attack +
                ", cost=" + this.cost +
                ", health=" + this.health +
                ", Type=" + this.type +
                ", Supertype=" + this.supertype +
                ", Keywords=" + this.keywords +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return attack == card.attack && cost == card.cost && health == card.health && collectible == card.collectible && Objects.equals(descriptionRaw, card.descriptionRaw) && Objects.equals(levelupDescriptionRaw, card.levelupDescriptionRaw) && Objects.equals(flavorText, card.flavorText) && Objects.equals(artistName, card.artistName) && Objects.equals(name, card.name) && Objects.equals(cardCode, card.cardCode) && Objects.equals(assets, card.assets) && Objects.equals(keywords, card.keywords) && Objects.equals(associatedCardRefs, card.associatedCardRefs) && Objects.equals(regions, card.regions) && Objects.equals(subtypes, card.subtypes) && rarity == card.rarity && set == card.set && type == card.type && supertype == card.supertype && spellSpeed == card.spellSpeed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(descriptionRaw, levelupDescriptionRaw, flavorText, artistName, name, cardCode, assets, keywords, associatedCardRefs, regions, subtypes, attack, cost, health, collectible, rarity, set, type, supertype, spellSpeed);
    }
}
