package model.card.subclasses;

import com.google.gson.annotations.SerializedName;

/**
 * Every Card in the Game has a Rarity. This Class represents the 4 Rarities that are in the Game. They have 2 types
 * of Costs connected to them. The cards can be bought with either Essence or RiotPoints.
 */
public enum Rarity {

    @SerializedName("None")NONE(0, 0),
    @SerializedName("Champion")CHAMPION(3000, 300),
    EPIC(1200, 120),
    RARE(300, 30),
    COMMON(100, 10);

    private final int priceEssence;
    private final int priceRP;

    Rarity(int priceEssence, int priceRP) {
        this.priceEssence = priceEssence;
        this.priceRP = priceRP;
    }

    public int getPriceEssence() {
        return this.priceEssence;
    }

    public int getPriceRP() {
        return this.priceRP;
    }

}
