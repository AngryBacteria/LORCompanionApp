package model.card.subclasses;

import com.google.gson.annotations.SerializedName;

/**
 * Small enum containing if a Card is a Champion or not.
 */
public enum SuperType {

    @SerializedName("Champion") CHAMPION,
    @SerializedName("") NONE
}
