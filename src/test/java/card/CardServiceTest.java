package card;

import model.card.Card;
import model.card.CardService;
import model.card.subclasses.Keyword;
import model.card.subclasses.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Small (not finished) test Class for the CardService Class
 */
class CardServiceTest {

    CardService cardService;
    Card Jhin;
    Card Yasuo;
    Card Poppy;
    Card SharkTrainer;
    Card ShortTooth;
    Card nicocard;
    Card TwinDisciplines;
    Card BandleTree;

    @BeforeEach
    void initiate(){
        cardService = new CardService(true);
        Jhin = cardService.getCardsByName("Jhin").get(0);
        Yasuo = cardService.getCardsByName("Yasuo").get(0);
        Poppy = cardService.getCardsByName("Poppy").get(0);
        SharkTrainer = cardService.getCardByCode("05BC010").get();
        ShortTooth = cardService.getCardByCode("05BC010T1").get();
        nicocard = CardService.nicocard;
        TwinDisciplines = cardService.getCardByCode("01IO012").get();
        BandleTree = cardService.getCardByCode("05BC194").get();
    }



    @Test
    void getCardsByName() {

    }

    @Test
    void getCardsByContains() {

    }

    @Test
    void getCardByCode() {
    }

    @Test
    void getCardsByAttack() {
    }

    @Test
    void getCardsByHealth() {
    }

    @Test
    void getCardsByCost() {
    }

    @Test
    void getCardsByType() {
    }

    @Test
    void getCardsBySuperType() {
    }

    @Test
    void getCardsBySet() {
    }

    @Test
    void getCardsByRegion() {
    }

    @Test
    void getAssociatedCards() {
    }

    @Test
    void testGetAssociatedCards() {
    }

    @Test
    void getRegionByName() {
        assertEquals(Region.BANDLECITY, cardService.getRegionByName("Bandle City").get());
        assertEquals(Region.BANDLECITY, cardService.getRegionByName("BandleCity").get());
        assertTrue(cardService.getRegionByName("Demacia").isPresent());
        assertFalse(cardService.getRegionByName("blabla").isPresent());
    }

    @Test
    void getKeyWordByName() {
        assertEquals(Keyword.QUICK_ATTACK, cardService.getKeyWordByName("Quickstrike").get());
        assertEquals(Keyword.IMPACT, cardService.getKeyWordByName("Impact").get());
        assertFalse(cardService.getKeyWordByName("blabla").isPresent());
    }

    @Test
    void getCardArray() {
    }

    @Test
    void getCardMap() {
    }

    @Test
    void getObservableList() {
    }

    @Test
    void addCard() {

        String cardCode = nicocard.getCardCode();

        assertFalse(cardService.getCardMap().containsKey(cardCode));
        assertFalse(cardService.getCardByCode(cardCode).isPresent());
        assertEquals(0, cardService.getCardsByName("Nico").size());

        cardService.addCard(nicocard);

        assertTrue(cardService.getCardMap().containsKey(cardCode));
        assertTrue(cardService.getCardByCode(cardCode).isPresent());
        assertEquals(1, cardService.getCardsByName("Nico").size());

        cardService.addCard(nicocard);
        assertEquals(1, cardService.getCardsByName("Nico").size());
    }

    @Test
    void removeCard() {
        Card card = cardService.getCardArray().get(40);
        assertTrue(cardService.getCardByCode(card.getCardCode()).isPresent());

        cardService.removeCard(card);
        assertFalse(cardService.getCardByCode(card.getCardCode()).isPresent());

        cardService.removeCard(card);
        assertFalse(cardService.getCardByCode(card.getCardCode()).isPresent());
    }

    @Test
    void addKeyword() {
        assertTrue(Yasuo.getKeywords().contains(Keyword.QUICK_ATTACK));
        assertFalse(Yasuo.getKeywords().contains(Keyword.IMPACT));

        cardService.addKeyword(Yasuo, Keyword.IMPACT);

        assertTrue(Yasuo.getKeywords().contains(Keyword.QUICK_ATTACK));
        assertTrue(Yasuo.getKeywords().contains(Keyword.IMPACT));
    }

    @Test
    void removeKeyword() {
        assertTrue(Yasuo.getKeywords().contains(Keyword.QUICK_ATTACK));
        cardService.removeKeyword(Yasuo, Keyword.QUICK_ATTACK);
        assertFalse(Yasuo.getKeywords().contains(Keyword.QUICK_ATTACK));
    }

    @Test
    void addRegion() {
        assertEquals(Region.RUNETERRA, Jhin.getRegions().get(0));
        assertFalse(Jhin.getRegions().contains(Region.BANDLECITY));
        assertEquals(1, Jhin.getRegions().size());

        Jhin.addRegion(Region.BANDLECITY);

        assertEquals(Region.RUNETERRA, Jhin.getRegions().get(0));
        assertTrue(Jhin.getRegions().contains(Region.BANDLECITY));
        assertEquals(2, Jhin.getRegions().size());
    }

    @Test
    void removeRegion() {
        assertEquals(Region.RUNETERRA, Jhin.getRegions().get(0));
        assertFalse(Jhin.getRegions().contains(Region.BANDLECITY));
        assertEquals(1, Jhin.getRegions().size());

        assertEquals(2, Poppy.getRegions().size());
        assertTrue(Poppy.getRegions().contains(Region.DEMACIA));
        assertTrue(Poppy.getRegions().contains(Region.BANDLECITY));

        Jhin.removeRegion(Region.RUNETERRA);
        Poppy.removeRegion(Region.BANDLECITY);

        assertFalse(Jhin.getRegions().contains(Region.RUNETERRA));
        assertEquals(0, Jhin.getRegions().size());

        assertEquals(1, Poppy.getRegions().size());
        assertTrue(Poppy.getRegions().contains(Region.DEMACIA));
        assertFalse(Poppy.getRegions().contains(Region.BANDLECITY));
    }

    @Test
    void addAssociatedCard() {
    }

    @Test
    void removeAssociatedCard() {
    }
}