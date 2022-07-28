package model.card.subclasses;

import com.google.gson.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

/**
 * In the Game there are Regions. Every Card has at least one Region. This Class represents the Regions as an Enum.
 * Every Region has an abbreviation, a full Name and a discord emoji ID, an Url to the official website and
 * a short description.
 */
public enum Region {


    @SerializedName("Targon")TARGON
            ("MT", "Targon", 940238494352146452L, 966958091792949258L,
            "https://images.contentstack.io/v3/assets/blt187521ff0727be24/blt68d52af2e5b56e7b/60ee0f7ffdd68748b2b7cd06/mttargon-once-in-a-lifetime.jpg",
                    "https://universe.leagueoflegends.com/en_US/region/mount-targon/",
                    "A brutal, expansionist empire where might makes right, Noxus sprawls across the continent in a struggle for complete dominance. However, within the region’s borders beats a heart fueled by the blood, sweat, and tears of its dedicated citizens, allowing any who would try for power to step forth and seize victory...if they’re able. Do you have what it takes?"),

    @SerializedName("Noxus")NOXUS
            ("NX", "Noxus", 940238455022178324L, 966958091826516028L,
            "https://images.contentstack.io/v3/assets/blt187521ff0727be24/bltfe6820bc73fa8d1b/60ee0febeebf8245e1b490fb/noxus-bastion.jpg",
                    "https://universe.leagueoflegends.com/en_US/region/noxus/",
                    "Mount Targon stands as a unyielding challenge, beckoning only the most dedicated souls to challenge its towering summit. Far removed from the rest of Runeterra, myth shrouds the mountain like a veil, drawing the daring and delusional in equal measure. Yet the heavens shine brightest above its peaks, and the cosmos holds many secrets worth exploring. Are you prepared to make the climb?"),

    @SerializedName("Demacia")DEMACIA
            ("DE", "Demacia", 940238415058841663L, 966958091549691906L,
            "https://cdnportal.mobalytics.gg/production/1969/10/Demacia-splash.jpg",
            "https://universe.leagueoflegends.com/en_US/region/demacia/",
                    "Justice. Honor. Duty. Proud Demacia sits on the northwestern edge of Runeterra. Once a safe haven from a brutal war, Demacia is now caught between the shifting tides of time. Can this mighty kingdom evolve to further serve its people, or do they face obsoletion along the march of progress? Stand on the side of justice!"),

    @SerializedName("Freljord")FRELJORD
            ("FR", "Freljord", 940238427494969354L, 966958091914588200L,
            "https://images.contentstack.io/v3/assets/blt187521ff0727be24/blt045ff48f67d6bd88/60ee0d9093cbaa393e346517/freljord-rakelstake.jpg",
            "https://universe.leagueoflegends.com/en_US/region/freljord/",
                    "In the bitter cold of the frozen north, the wilderness is torn asunder from a savage, three-way civil war. Those who call this place home must find the fire within to persevere through harsh conditions and stand as paragons of fortitude and resilience. Can you weather the path to victory?"),

    @SerializedName("Shadow Isles")SHADOWISLES
            ("SI", "Shadow Isles", 940238483669262336L, 966958091763597333L,
            "https://images.contentstack.io/v3/assets/blt187521ff0727be24/blt640eb217bfb3bbfb/60ee11cbc019ad2aaa39a552/shadow_isles_environment_06.jpg",
                    "https://universe.leagueoflegends.com/en_US/region/shadow-isles/",
                    "The Blessed Isles were once a thriving, fertile land...until the king of the Isles, beset by grief, sought to defy the natural order of the world, bringing ruination to the land. Now the Shadow Isles, veiled by the sinister shroud known as the Black Mist, leech life from any creature who dares step foot on those cursed shores. Do you dare turn your gaze to the Shadow Isles? "),

    @SerializedName("Ionia")IONIA
            ("IO", "Ionia", 940238445685641246L, 966958091772002314L,
            "https://images.contentstack.io/v3/assets/blt187521ff0727be24/blte6ece8c577fd842f/60ee0e1cc300f42b197f1c9f/ionia-the-first-lands.jpg",
            "https://universe.leagueoflegends.com/en_US/region/ionia/",
            "Balance: the cornerstone of the First Lands we know as Ionia. Once, it existed aplenty, with the spiritual and physical worlds coexisting together in harmony...but such serenity is fleeting. Noxus, bent on imperial expansion, brought their insatiable hunger to Ionia’s shores. Though the empire was driven out, there is an air of uncertainty hovering over the land. Are you willing to pay the price for peace?"),

    @SerializedName("Shurima")SHURIMA
            ("SH", "Shurima", 940238474622169128L, 966958091759411210L,
            "https://images.contentstack.io/v3/assets/blt187521ff0727be24/bltbb26c701fd6aeeb5/60ee11f560216d1db87a125b/shurima-risen.jpg",
            "https://universe.leagueoflegends.com/en_US/region/shurima/",
            "Once an expansive empire, Shurima's glory has long since been lost to the sands of time. Now, scavengers and mercenaries eke out a living in this harsh desert, hoping to one day find untold riches hidden beneath its barren land. For where there lies ruin, also lies opportunity... At least, for those who are worthy. Do you dare brave these badlands in pursuit of ascension?"),

    @SerializedName("Bilgewater")BILGEWATER
            ("BW", "Bilgewater", 940238405265137675L, 966958091788771379L,
            "https://images.contentstack.io/v3/assets/blt187521ff0727be24/blt81671b41e69cf391/60ee0bb4fde37d4c039e4393/bilgewater-bilgewater-bay.jpg",
            "https://universe.leagueoflegends.com/en_US/region/bilgewater/",
            "Across the Guardian’s Sea, nestled amongst the Blue Flame Isles, Bilgewater Bay is as lawless as the denizens that call it home. Smugglers and cutthroats lay claim to these shores, making a name for themselves with sharp minds and sharper blades. The land is not the only perilous place, for danger lurks beneath the waves. Are you prepared to risk it all for the ultimate prize?"),

    @SerializedName("Piltover & Zaun")PILTOVERZAUN
            ("PZ", "Piltover & Zaun", 940238464048320512L, 966958091788750848L,
            "https://dd.b.pvp.net/3_1_0/core/en_us/img/regions/icon-piltoverzaun.png",
            "https://leagueoflegends.fandom.com/wiki/Piltover_%26_Zaun_(Legends_of_Runeterra)",
            "Piltover and Zaun may seem worlds apart, but an unusual harmony connects them beyond mere proximity. On the surface, Piltover is a prosperous city of industry. Standing as Valoran’s cultural center, innovation and ingenuity are prized. However, amidst the cliffs beneath Piltover lies Zaun. Swathed in a smog-induced twilight, Zaun thrives in the shadow of its sister city. When the brilliant scientists above find society too constraining, they journey below, into the toxic air to conduct their experiments. Will you choose to stay on the surface or chance what lurks beneath?"),

    @SerializedName("Bandle City")BANDLECITY
            ("BC", "Bandle City", 940238393588195448L, 966958091801358346L,
            "https://images.contentstack.io/v3/assets/blt187521ff0727be24/bltbe73a6a61da9d0aa/60ee0b92a718b62441b30993/yordle_portal_02.jpg",
                    "https://universe.leagueoflegends.com/en_US/region/bandle-city/",
                    "Bandle City is said to be a pristine paradise, a wonderous dreamland unlike any other. Few mortals have glimpsed the yordles' mysterious home, and fewer still have returned to tell the tale. Those who have can never agree on what wonders they witnessed.  What secrets will you uncover beyond the Bandlewood?"),

    @SerializedName("Runeterra")RUNETERRA
            ("RU", "Runeterra", 977527388994945034L, 977528760867905586L,
                    "https://dd.b.pvp.net/latest/core/en_us/img/regions/icon-runeterra.png",
                    "",
                    "Runeterra region"),


    @SerializedName("Ixtal")IXTAL
            ("IX", "Ixtal", 1, 1,
                    "https://images.contentstack.io/v3/assets/blt187521ff0727be24/blt9a01601bbdff5c4a/60ee0e385fbfcb5e848229a5/ixtal_splash.jpg",
                    "https://universe.leagueoflegends.com/en_US/region/ixtal/",
                    "Renowned for its mastery of elemental magic, Ixtal was one of the first independent nations to join the Shuriman empire. In truth, Ixtali culture is much older—part of the great westward diaspora that gave rise to civilizations including the Buhru, magnificent Helia, and the ascetics of Targon—and it is likely they played a significant role in the creation of the first Ascended.\n" + "\n" + "But the mages of Ixtal survived the Void, and later the Darkin, by distancing themselves from their neighbors, drawing the wilderness around them like a shield. While much had already been lost, they were committed to the preservation of what little remained…\n" + "\n" + "Now, secluded deep in the jungle for thousands of years, the sophisticated arcology-city of Ixaocan remains mostly free of outside influence. Having witnessed from afar the ruination of the Blessed Isles and the Rune Wars that followed, the Ixtali view all the other factions of Runeterra as upstarts and pretenders, and use their powerful magic to keep any intruders at bay.\n"),

    @SerializedName("Void")VOID
            ("VO", "The Void", 1, 1,
                    "https://images.contentstack.io/v3/assets/blt187521ff0727be24/blt4754fc6a91233824/60ee139d5397524ead38918f/void-an-unknowable-power.jpg",
                    "https://universe.leagueoflegends.com/en_US/region/void/",
                    "Screaming into existence with the birth of the universe, the Void is a manifestation of the unknowable nothingness that lies beyond. It is a force of insatiable hunger, waiting through the eons until its masters, the mysterious Watchers, mark the final time of undoing.\n" + "\n" + "To be a mortal touched by this power is to suffer an agonizing glimpse of eternal unreality, enough to shatter even the strongest mind. Denizens of the Void realm itself are construct-creatures, often of only limited sentience, but tasked with a singular purpose—to usher in total oblivion across Runeterra."),

    ALL("all", "all", 1, 1, "", "", "Region for cardsearch");

    private final String abbreviation;
    private final String regionFull;
    private final String pictureURL;
    private final String loreTextURL;
    private final String description;

    //Not needed here
    private final long emojiID;
    private final long emojiBarID;

    Region(String abbreviation, String regionFull, long emojiID, long emojiBarID, String pictureURL, String loreTextURL, String description) {
        this.abbreviation = abbreviation;
        this.regionFull = regionFull;
        this.emojiID = emojiID;
        this.emojiBarID = emojiBarID;
        this.pictureURL = pictureURL;
        this.loreTextURL = loreTextURL;
        this.description = description;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public String getRegionFull() {
        return this.regionFull;
    }

    public String getPictureURL() {
        return this.pictureURL;
    }

    public String getRandomPictureURL() {

        Random random = new Random();
        ArrayList<String> array = new ArrayList<>();
        try (FileReader fileReader = new FileReader("src/main/resources/regions/" + this.abbreviation + ".txt")) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Stream<String> lines = bufferedReader.lines();
            lines.forEach(array::add);
        } catch (IOException e) {
            System.out.println("No Image links found in text file");
        }
        return array.get(random.nextInt(array.size()));
    }

    public String getDescription() {
        return this.description;
    }

    public String getLoreTextURL() {
        return this.loreTextURL;
    }

    @Override
    public String toString() {
        return this.getRegionFull();
    }

    //Not needed here
    public long getEmojiID() {
        return this.emojiID;
    }

    public String getEmojiBarDiscord(){
        return "<:bar:" + this.emojiBarID + ">";
    }



}
