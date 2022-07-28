package model.card;

import model.card.subclasses.Region;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Class for the CardIndexing Class.
 */
class CardIndexingTest {


    @Test
    void cardArraysHaveCardsInThem(){

        assertTrue(CardIndexing.getCardArrayFromArrays().size()>1500);
        assertTrue(CardIndexing.getCardsArrayFromResourcesFile("set1-en_us.json").length>100);
        assertTrue(CardIndexing.getCardsArrayFromResourcesFile("set2-en_us.json").length>100);
        assertTrue(CardIndexing.getCardsArrayFromResourcesFile("set3-en_us.json").length>100);
        assertTrue(CardIndexing.getCardsArrayFromResourcesFile("set4-en_us.json").length>100);
        assertTrue(CardIndexing.getCardsArrayFromResourcesFile("set5-en_us.json").length>100);
        assertTrue(CardIndexing.getCardsArrayFromResourcesFile("set6-en_us.json").length>100);
    }

    @Test
    void getAllArrayFieldValues() {

        List<String> regions = CardIndexing.getAllArrayFieldValues("regions");
        //There are only 11 regions in the game files. I added 2 additional regions myself to the Card object
        assertEquals(11, regions.size());
    }

    @Test
    void getAllFieldValues() {

        List<String> typeList = CardIndexing.getAllFieldValues("type");
        assertEquals(5, typeList.size());

        List<String> spellSpeedList = CardIndexing.getAllFieldValues("spellSpeed");
        //4 Because there are 3 Spellspeeds + the empty string if there is no spellSpeed
        assertEquals(4, spellSpeedList.size());

        List<String> setList = CardIndexing.getAllFieldValues("set");
        assertEquals(6, setList.size());

    }
}