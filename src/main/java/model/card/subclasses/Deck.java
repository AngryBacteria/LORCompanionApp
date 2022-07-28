package model.card.subclasses;

import model.card.Card;
import model.card.CardService;
import no.stelar7.api.r4j.impl.lor.LoRDeckCode;
import no.stelar7.api.r4j.pojo.lor.offline.card.LoRCard;
import no.stelar7.api.r4j.pojo.lor.offline.card.LoRDeck;

import java.util.*;

/**
 * In the Game there are Decks. Those consist of 40 Cards with some additional Rules. This Deck Class represents such
 * a Deck. Cards can be added and removed. Also the official Deck Code can be extracted, for this a third party library
 * is used. With this unique Deck Code, the Deck can be important into the Game to be played.
 */
public class Deck {

    private Map<Card, Integer> cardIntegerMap;

    public Deck() {
        this.cardIntegerMap = new HashMap<>();
    }

    //Problem if Cardservice is not updates yet, the custom cards wont work. Maybe just not use custom cards for now.
    public Deck(String deckCode) {

        CardService cardService = new CardService(false);
        this.cardIntegerMap = new HashMap<>();

        LoRDeck loRDeck = LoRDeckCode.decode(deckCode);
        for (LoRCard loRCard : loRDeck.getDeck().keySet()) {

            if (cardService.getCardByCode(loRCard.getCardCode()).isEmpty())
                throw new IllegalStateException("One or more Cards in the Deck couldn't be " +
                        "found in the Deck Service " + loRCard.getCardCode());

            Integer amount = loRDeck.getDeck().get(loRCard);
            Card card = cardService.getCardByCode(loRCard.getCardCode()).get();
            this.cardIntegerMap.put(card, amount);
        }
    }

    public void addCard(Card card) {
        if (this.cardIntegerMap.containsKey(card)) {
            if (this.cardIntegerMap.get(card) > 2)
                throw new IllegalStateException("Deck already has 3 Cards of that type");
            else if (this.getAmountOfChampions() > 5)
                throw new IllegalStateException("Deck already has 6 Champions");
            else
                this.cardIntegerMap.put(card, this.cardIntegerMap.get(card) + 1);
        } else
            this.cardIntegerMap.put(card, 1);
    }

    public void addCard(Card card, int amount) {
        if (this.cardIntegerMap.containsKey(card))
            this.cardIntegerMap.put(card, this.cardIntegerMap.get(card) + amount);

        else
            this.cardIntegerMap.put(card, amount);
    }

    public void removeCard(Card card, int amount) {
        if (this.cardIntegerMap.containsKey(card)) {
            if (amount > this.cardIntegerMap.get(card) || this.cardIntegerMap.get(card) == 1)
                this.cardIntegerMap.remove(card);
            else
                this.cardIntegerMap.put(card, this.cardIntegerMap.get(card) - amount);
        }
    }

    public void removeCard(Card card) {
        this.cardIntegerMap.remove(card);
    }

    public java.util.Set<Region> getDeckRegions() {
        java.util.Set<Region> output = new HashSet<>();
        for (Card card : this.cardIntegerMap.keySet()) {
            output.addAll(card.getRegions());
        }
        return output;
    }

    public LoRDeck convertDeckToLorDeck() {

        for (Card card : this.cardIntegerMap.keySet()) {
            if (card.getSet().equals(Set.CUSTOM))
                throw new IllegalArgumentException("No custom cards allowed");
        }

        LoRDeck loRDeck = new LoRDeck();
        for (Card card : this.cardIntegerMap.keySet()) {
            loRDeck.addCard(LoRCard.create(card.getCardCode()), this.cardIntegerMap.get(card));
        }
        return loRDeck;
    }

    public Map<Card, Integer> getCardIntegerMap() {
        return Collections.unmodifiableMap(this.cardIntegerMap);
    }

    public ArrayList<CardAndAmount> getCardAndAmounts() {
        ArrayList<CardAndAmount> cardAndAmounts = new ArrayList<>();
        for (Card card : this.cardIntegerMap.keySet()) {
            cardAndAmounts.add(new CardAndAmount(card, this.cardIntegerMap.get(card)));
        }
        return cardAndAmounts;
    }

    public int getAmountOfCards() {
        int output = 0;
        for (CardAndAmount cardAndAmount : this.getCardAndAmounts()) {
            output = output + cardAndAmount.getAmount();
        }
        return output;
    }

    public int getAmountOfChampions(){
        int output = 0;
        for (CardAndAmount cardAndAmount : this.getCardAndAmounts()) {
            if(cardAndAmount.getCard().isChampion())
                output = output + 1;
        }
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return Objects.equals(this.cardIntegerMap, deck.cardIntegerMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.cardIntegerMap);
    }

    @Override
    public String toString() {

        String output = "Deck\n";
        for (Card card : this.cardIntegerMap.keySet()) {
            output = output + String.format("%s  x  %s\n", this.cardIntegerMap.get(card), card.getName());
        }
        return output;
    }
}


