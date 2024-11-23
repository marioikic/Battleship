package battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SpielfeldTest {

    private Spielfeld spielfeld;

    @BeforeEach
    public void setup() {
        spielfeld = new Spielfeld();
    }


    @Test
    void testZeige_leeresSpielfeld() {

        String actual = spielfeld.zeige();
        String expected =
                "  1 2 3 4 5 6 7 8 9 10\n" +
                        "A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                        "B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                        "C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                        "D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                        "E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                        "F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                        "G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                        "H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                        "I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                        "J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n";

        assertEquals(expected, actual);
    }

    @Test
    void testSchiffPlatzierenUndAnzeigen_Laenge3_Horizontal() {

        assertTrue(spielfeld.platziereSchiffBenutzer('B', 1, 3, 'H'));
        String actual = spielfeld.zeige();

        String expected =
                "  1 2 3 4 5 6 7 8 9 10\n" +
                        "A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                        "B ~ S S S ~ ~ ~ ~ ~ ~ \n" +
                        "C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                        "D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                        "E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                        "F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                        "G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                        "H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                        "I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                        "J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n";

        assertEquals(expected, actual);
    }

    @Test
    void testSchiffPlatzierenUndAnzeigen_wrong() {
        assertFalse(spielfeld.platziereSchiffBenutzer('B', 0, 0, 'Z'));
    }
}