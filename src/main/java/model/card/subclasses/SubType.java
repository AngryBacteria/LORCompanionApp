package model.card.subclasses;

import com.google.gson.annotations.SerializedName;

// FIXME (due1) Import wird nicht benötigt.
import java.util.List;

public enum SubType {

    ASCENDED,
    CELESTIAL,
    DRAGON,
    ELITE,
    ELNUK,
    FAE,
    LURKER,
    @SerializedName("MECHA-YORDLE")MECHA_YORDLE,
    @SerializedName("MOON WEAPON")MOON_WEAPON,
    PORO,
    @SerializedName("SEA MONSTER")SEA_MONSTER,
    SPIDER,
    TECH,
    TREASURE,
    YETI,
    YORDLE,

}

