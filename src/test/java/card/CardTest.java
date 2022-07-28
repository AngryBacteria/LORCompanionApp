package card;

import model.card.Card;
import model.card.CardService;
import model.card.subclasses.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Small (not finished) test Class for the Card Class
 */
class CardTest {

    CardService cardService = new CardService(true);

    Card Jhin = cardService.getCardsByName("Jhin").get(0);
    Card Yasuo = cardService.getCardsByName("Yasuo").get(0);
    Card Poppy = cardService.getCardsByName("Poppy").get(0);
    Card SharkTrainer = cardService.getCardByCode("05BC010").get();
    Card ShortTooth = cardService.getCardByCode("05BC010T1").get();
    Card nicocard = CardService.nicocard;
    Card TwinDisciplines = cardService.getCardByCode("01IO012").get();
    Card BandleTree = cardService.getCardByCode("05BC194").get();


    @Test
    void getDescriptionRaw() {
        cardService.getCardArray().forEach(card -> assertNotNull(card.getDescriptionRaw()));
        assertEquals("Origin: The Virtuoso. \r\nAttack: Deal 2 to all Stunned enemies.", Jhin.getDescriptionRaw());
    }

    @Test
    void getLevelupDescriptionRaw() {
        cardService.getCardArray().forEach(card -> assertNotNull(card.getLevelupDescriptionRaw()));
        assertEquals("You've played 12+ Fast spells, Slow spells, or Skills.", Jhin.getLevelupDescriptionRaw());
    }

    @Test
    void getFlavorText() {
        cardService.getCardArray().forEach(card -> assertNotNull(card.getFlavorText()));
        assertEquals("\"Overture: We open on the city of Qayanvi, where an old master summons a young pupil. He is tasked with disposing a group of nefarious villains and recovering stolen sacred artifacts. The actor is keen, but he does not as yet take his place. For now he simply prepares, waiting in the wings.\"", Jhin.getFlavorText());
    }

    @Test
    void getArtistName() {
        cardService.getCardArray().forEach(card -> assertNotNull(card.getArtistName()));
        assertEquals("Alessandro Poli", Jhin.getArtistName());
    }

    @Test
    void getName() {
        cardService.getCardArray().forEach(card -> assertNotNull(card.getName()));
        assertEquals("Jhin", Jhin.getName());
    }

    @Test
    void getCardCode() {
        cardService.getCardArray().forEach(card -> assertNotNull(card.getCardCode()));
        assertEquals("06RU002", Jhin.getCardCode());
    }

    @Test
    void getSet() {
        cardService.getCardArray().forEach(card -> assertNotNull(card.getSet()));
        assertEquals(Set.SET6, Jhin.getSet());
    }

    @Test
    void getKeywords() {
        assertEquals(Keyword.BURST, TwinDisciplines.getKeywords().get(0));
        assertEquals(Keyword.LANDMARK, BandleTree.getKeywords().get(0));
        assertEquals(0, Poppy.getKeywords().size());
        cardService.getCardArray().forEach(card -> assertNotNull(card.getKeywords()));
    }

    @Test
    void getAttack() {
        assertEquals(1, nicocard.getAttack());
        assertEquals(4, Jhin.getAttack());
    }

    @Test
    void getCost() {
        assertEquals(2, nicocard.getCost());
        assertEquals(4, Jhin.getCost());
    }

    @Test
    void getHealth() {
        assertEquals(3, nicocard.getHealth());
        assertEquals(4, Jhin.getHealth());
    }

    @Test
    void getRegions() {
        cardService.getCardArray().forEach(card -> assertNotNull(card.getRegions()));
        assertEquals(Region.RUNETERRA, Jhin.getRegions().get(0));

        List<Region> regionList = new ArrayList<>();
        regionList.add(Region.BANDLECITY);
        regionList.add(Region.DEMACIA);
        assertEquals(regionList, Poppy.getRegions());
    }

    @Test
    void getAssets() {
        cardService.getCardArray().forEach(card -> assertNotNull(card.getAssets()));
        assertEquals("https://dd.b.pvp.net/3_8_0/set6/en_us/img/cards/06RU002.png", Jhin.getAssets().getGameAbsolutePath());
        assertEquals("https://dd.b.pvp.net/3_8_0/set6/en_us/img/cards/06RU002-full.png", Jhin.getAssets().getFullAbsolutePath());
    }

    @Test
    void isCollectible() {
        assertTrue(nicocard.isCollectible());
        assertTrue(SharkTrainer.isCollectible());
        assertFalse(ShortTooth.isCollectible());
        assertTrue(Yasuo.isCollectible());
    }

    @Test
    void getRarity() {
        cardService.getCardArray().forEach(card -> assertNotNull(card.getRarity()));
        assertEquals(Rarity.CHAMPION, Poppy.getRarity());
        assertEquals(Rarity.CHAMPION, Yasuo.getRarity());
        assertEquals(Rarity.CHAMPION, nicocard.getRarity());

        assertEquals(Rarity.COMMON, TwinDisciplines.getRarity());
    }

    @Test
    void getType() {
        cardService.getCardArray().forEach(card -> assertNotNull(card.getType()));
        assertEquals(Type.UNIT, Jhin.getType());
        assertEquals(Type.UNIT, SharkTrainer.getType());
        assertEquals(Type.SPELL, TwinDisciplines.getType());
        assertEquals(Type.LANDMARK, BandleTree.getType());
    }

    @Test
    void getSupertype() {
        assertEquals(SuperType.CHAMPION, Yasuo.getSupertype());
        assertEquals(SuperType.CHAMPION, Poppy.getSupertype());
        assertEquals(SuperType.NONE, TwinDisciplines.getSupertype());
        assertEquals(SuperType.NONE, BandleTree.getSupertype());
        assertEquals(SuperType.NONE, ShortTooth.getSupertype());
        cardService.getCardArray().forEach(card -> assertNotNull(card.getSupertype()));
    }

    @Test
    void getAssociatedCardRefs() {
        cardService.getCardArray().forEach(card -> assertNotNull(card.getAssociatedCardRefs()));

        List<String> associatedCardsJhin = new ArrayList<>();
        associatedCardsJhin.add("06RU002T6");
        associatedCardsJhin.add("06RU002T5");
        associatedCardsJhin.add("06RU002T4");
        associatedCardsJhin.add("06RU002T2");
        associatedCardsJhin.add("06RU002T1");
        associatedCardsJhin.add("06RU002T13");
        associatedCardsJhin.add("06RU002T12");
        assertEquals(associatedCardsJhin, Jhin.getAssociatedCardRefs());
    }

    @Test
    void getSpellSpeed() {
        assertEquals(SpellSpeed.BURST, TwinDisciplines.getSpellSpeed());
        cardService.getCardArray().forEach(card -> assertNotNull(card.getSpellSpeed()));
        assertEquals(SpellSpeed.NONE, Jhin.getSpellSpeed());
        assertEquals(SpellSpeed.NONE, Poppy.getSpellSpeed());
    }

    @Test
    void isChampion() {
        assertFalse(SharkTrainer.isChampion());
        assertFalse(ShortTooth.isChampion());
        assertFalse(TwinDisciplines.isChampion());
        assertFalse(BandleTree.isChampion());
        assertTrue(Yasuo.isChampion());
        assertTrue(Poppy.isChampion());
    }

    @Test
    void hasKeywordSearch() {
        assertTrue(Yasuo.hasKeywordSearch("Quick attack"));
        assertFalse(Yasuo.hasKeywordSearch("Regeneration"));
        assertFalse(Poppy.hasKeywordSearch("Impact"));
        assertTrue(ShortTooth.hasKeywordSearch("Overwhelm"));
    }

    @Test
    void hasRegionSearch() {
        assertTrue(Yasuo.hasRegionSearch("Ionia"));
        assertFalse(Yasuo.hasRegionSearch("Demacia"));
        assertTrue(Poppy.hasRegionSearch("Demacia"));
        assertTrue(Poppy.hasRegionSearch("Bandle City"));
        assertTrue(Poppy.hasRegionSearch("BandleCity"));
    }

    @Test
    void containsDescription() {
        assertTrue(Yasuo.cardContains("Quick Attack"));
        assertTrue(Jhin.cardContains("Origin"));
        assertTrue(Poppy.cardContains("Attack"));
        assertFalse(Poppy.cardContains("Origin"));
    }
}