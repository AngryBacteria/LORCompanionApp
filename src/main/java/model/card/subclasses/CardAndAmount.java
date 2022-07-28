package model.card.subclasses;

import model.card.Card;

import java.util.Objects;

/**
 * Helper Class for the Deck class and for the DeckCreator JavaFX application. It holds a Card and the amount of
 * the Card in the deck.
 */
public class CardAndAmount{
    private final Card card;
    private final Integer amount;

    public CardAndAmount(Card card, Integer amount) {
        this.card = card;
        this.amount = amount;
    }

    public Card getCard() {
        return card;
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardAndAmount that = (CardAndAmount) o;
        return Objects.equals(card, that.card) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(card, amount);
    }
}
