package model.card.subclasses;
import com.google.gson.annotations.SerializedName;

/**
 * In the Game there are 5 basic Types of Cards. This Class represents those Types with an Enum.
 */
public enum Type {

    @SerializedName("Unit") UNIT,
    @SerializedName("Landmark") LANDMARK,
    @SerializedName("Spell") SPELL,
    @SerializedName("Ability") ABILITY,
    @SerializedName("Trap") TRAP

}
