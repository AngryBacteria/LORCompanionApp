package model.card.subclasses;

import com.google.gson.annotations.SerializedName;

/**
 * Cards sometimes have Keywords. Those give the Card special effects. This Class holds the Enum which has 2 Fields.
 * NameRef as a secondary, language independent name and the description of the Effect.
 */
public enum Keyword {

    @SerializedName("Attach")ATTACH("Attach", "Play me on an ally to give it my stats and keywords while I'm attached. When that ally leaves play, Recall me."),
    @SerializedName("Attune")ATTUNE("Attune", "When I'm summoned, refill 1 spell mana."),
    @SerializedName("Augment")AUGMENT("Augment", "When you play a created card, grant me +1|+0."),
    @SerializedName("Barrier")BARRIER("Barrier", "Negates the next damage the unit would take. Lasts one round."),
    @SerializedName("Burst")BURST("Burst", "Can be played whenever you may act. Happens instantly and allows you to continue to play other cards."),
    @SerializedName("Can't Block")CANT_BLOCK("CantBlock", "None"),
    @SerializedName("Challenger")CHALLENGER("Challenger", "Can choose which enemy unit blocks."),
    @SerializedName("Countdown")COUNTDOWN("Countdown", "Round Start: I count down 1. At 0, activate the Countdown effect, then destroy me."),
    @SerializedName("Deep")DEEP("Deep", "None"),
    @SerializedName("Double Attack")DOUBLE_ATTACK("DoubleStrike", "While attacking, it strikes both before AND at the same time as its blocker."),
    @SerializedName("Elusive")ELUSIVE("Elusive", "Can only be blocked by an Elusive unit."),
    @SerializedName("Ephemeral")EPHEMERAL("Ephemeral", "This unit dies when it strikes or when the round ends."),
    @SerializedName("Fast")FAST("Fast", "Can be played whenever you may act. Happens after your opponent has a chance to react."),
    @SerializedName("Fearsome")FEARSOME("Fearsome", "Can only be blocked by enemies with 3 or more Power."),
    @SerializedName("Fleeting")FLEETING("Fleeting", "Fleeting cards discard from hand when the round ends."),
    @SerializedName("Focus")FOCUS("Focus", "Can be played outside combat or when no other spells or skills are pending. Happens instantly and allows you to continue to play other cards."),
    @SerializedName("Formidable")FORMIDABLE("Formidable", "I strike with my Health instead of my Power."),
    @SerializedName("Fury")FURY("Fury", "When I kill a unit, grant me +1|+1."),
    @SerializedName("Imbue")IMBUE("Imbue", "These abilities trigger when you resolve a spell."),
    @SerializedName("Immobile")IMMOBILE("Immobile", "Can't attack or block."),
    @SerializedName("Impact")IMPACT("Impact", "When this strikes while attacking, it deals 1 to the enemy Nexus. This keyword can stack."),
    @SerializedName("Landmark")LANDMARK("LandmarkVisualOnly", "Landmarks take up a space on the board. They can't attack, block, or take damage."),
    @SerializedName("Last Breath")LAST_BREATH("LastBreath", "These abilities take effect when the unit dies."),
    @SerializedName("Lifesteal")LIFESTEAL("Lifesteal", "Damage this unit deals heals its Nexus that amount."),
    @SerializedName("Lurk")LURK("Lurker", "When you attack while I'm on top of your deck, I Lurk, granting Lurker allies everywhere +1|+0. Max once per round."),
    @SerializedName("Missing Translation")MISSING_TRANSLATION("", ""),
    @SerializedName("Overwhelm")OVERWHELM("Overwhelm", "Excess damage I deal to my blocker is dealt to the enemy Nexus."),
    @SerializedName("Quick Attack")QUICK_ATTACK("QuickStrike", "While attacking, strikes before its blocker."),
    @SerializedName("Regeneration")REGENERATION("Regeneration", "Heals fully at the end of each round."),
    @SerializedName("Scout")SCOUT("Scout", "The first time only Scout units attack each round, ready your attack."),
    @SerializedName("Skill")SKILL("Skill", "A unit's spell-like effect that allows enemy reactions."),
    @SerializedName("Slow")SLOW("Slow", "Can be played outside of combat when no spells or skills are pending. Happens after your opponent has a chance to react."),
    @SerializedName("SpellShield")SPELLSHIELD("Spellshield", "Negates the next enemy spell or skill that would affect me."),
    @SerializedName("Trap")TRAP("Autoplay", "Attaches to another card, trapping it. When the trapped card is drawn, perform the effect."),
    @SerializedName("Tough")TOUGH("Tough", "Takes 1 less damage from all sources."),
    @SerializedName("Vulnerable")VULNERABLE("Vulnerable", "The enemy can challenge this unit, forcing it to block."),

    //Not in files but should be existing
    @SerializedName("Fated")FATED("Fated", "Each round, the first time an allied card targets me, grant me +1|+1."),
    @SerializedName("Can block Elusives")CAN_BLOCK_ELUSIVES("BlockElusive", "Missing Translation"),

    //New
    @SerializedName("Spawn")SPAWN("Spawn", "Spawn"),
    @SerializedName("Boon")BOON("Boon", "Boon"),
    ALL("", "keyword for javafx");

    private final String nameRef;
    private final String description;

    Keyword(String nameRef, String description) {
        this.nameRef = nameRef;
        this.description = description;
    }

    public String getNameRef() {
        return nameRef;
    }

    public String getDescription() {
        return description;
    }



}
