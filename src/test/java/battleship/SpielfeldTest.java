package battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class SpielfeldTest {

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


/*neue Tests*/
@Test
void testSchiffPlatzierenUndAnzeigen_Laenge4_Vertikal() {

    assertTrue(spielfeld.platziereSchiffBenutzer('B', 1, 4, 'V'));
    String actual = spielfeld.zeige();

    String expected =
            "  1 2 3 4 5 6 7 8 9 10\n" +
                    "A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                    "B ~ S ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                    "C ~ S ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                    "D ~ S ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                    "E ~ S ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                    "F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                    "G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                    "H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                    "I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n" +
                    "J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ \n";

    assertEquals(expected, actual);
}


    @Test
    void testSchiffPlatzieren_AusserhalbDesSpielfelds() {
        assertFalse(spielfeld.platziereSchiffBenutzer('K', 5, 3, 'H')); // Zeile außerhalb des Spielfelds
        assertFalse(spielfeld.platziereSchiffBenutzer('A', 9, 3, 'H')); // Spalte überschreitet Spielfeldgröße
        assertFalse(spielfeld.platziereSchiffBenutzer('A', -1, 3, 'H')); // Ungültige Spalte
    }

    @Test
    void testSchuss_Treffer() {
        spielfeld.platziereSchiffBenutzer('C', 2, 3, 'H'); // Platziere ein Schiff
        Boolean result = spielfeld.schiesse('C', 3); // Schuss auf ein Schiff

        assertTrue(result);
        String actual = spielfeld.zeige();
        assertTrue(actual.contains("X")); // Überprüfen, ob ein Treffer markiert wurde
    }

    @Test
    void testSchuss_Fehlschuss() {
        spielfeld.platziereSchiffBenutzer('C', 2, 3, 'H'); // Platziere ein Schiff
        Boolean result = spielfeld.schiesse('D', 5); // Schuss ins Wasser

        assertFalse(result);
        String actual = spielfeld.zeige();
        assertTrue(actual.contains("O")); // Überprüfen, ob ein Fehlschuss markiert wurde
    }

    @Test
    void testSchuss_BereitsGetroffen() {
        spielfeld.platziereSchiffBenutzer('C', 2, 3, 'H'); // Platziere ein Schiff
        spielfeld.schiesse('C', 3); // Schuss auf ein Schiff
        Boolean result = spielfeld.schiesse('C', 3); // Erneuter Schuss auf dieselbe Stelle

        assertNull(result); // Bereits getroffen
    }


    @Test
    void testAllesVersenkt_NachSpielende() {
        spielfeld.platziereSchiffBenutzer('A', 0, 2, 'H');
        spielfeld.schiesse('A', 0);
        spielfeld.schiesse('A', 1);

        assertTrue(spielfeld.istAllesVersenkt()); // Alle Schiffe sollten versenkt sein
    }

    @Test
    void testZeigeVerdeckt() {
        spielfeld.platziereSchiffBenutzer('A', 0, 3, 'H');
        String actual = spielfeld.zeigeVerdeckt();

        assertFalse(actual.contains("S")); // Sicherstellen, dass Schiffe verdeckt sind
        assertTrue(actual.contains("~")); // Wasser sollte weiterhin angezeigt werden
    }

    /*neue tests*/
    @Test
    void testComputerSchussAbgabe() {
        // Vorbereitung: Platziere ein Schiff auf dem Spielfeld des Spielers
        spielfeld.platziereSchiffBenutzer('C', 2, 3, 'H'); // Schiff der Länge 3 wird platziert

        // Simulation: Computer gibt einen Schuss ab
        Boolean ergebnis = spielfeld.schiesse('C', 3); // Computer schießt auf das Schiff

        // Überprüfung: Der Schuss ist ein Treffer
        assertTrue(ergebnis, "Der Computer sollte einen Treffer landen.");
        String actual = spielfeld.zeige();
        assertTrue(actual.contains("X"), "Das Spielfeld sollte einen Treffer ('X') zeigen.");
    }
    @Test
    void testComputerSchiessStrategie() {
        // Vorbereitung: Platziere ein Schiff und simuliere einen Treffer
        spielfeld.platziereSchiffBenutzer('E', 4, 2, 'H'); // Schiff der Länge 2 wird platziert
        spielfeld.schiesse('E', 4); // Computer trifft das erste Segment des Schiffs

        // Simulation: Computer visiert angrenzendes Feld an
        Boolean ergebnis = spielfeld.schiesse('E', 5); // Computer schießt auf das nächste Segment

        // Überprüfung: Der zweite Schuss ist ein Treffer
        assertTrue(ergebnis, "Der Computer sollte einen Treffer landen.");
        String actual = spielfeld.zeige();
        assertTrue(actual.contains("X"), "Das Spielfeld sollte den Treffer ('X') anzeigen.");
    }




}
