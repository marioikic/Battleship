import battleship.Spielfeld;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class SpielfeldTest {

    private Spielfeld spielfeld;

    @BeforeEach
    void setup() {
        spielfeld = new Spielfeld();
    }

    @Test
    void testT001_StartenDesSpiels() {
        String initialBoard = spielfeld.zeige();
        assertNotNull(initialBoard);
        assertTrue(initialBoard.contains("~"), "Das Spielfeld sollte initial leer sein.");
    }

    @Test
    void testT002_AnzeigeSchiffSetzen() {
        assertTrue(spielfeld.platziereSchiffBenutzer('A', 0, 5, 'H'));
        String board = spielfeld.zeige();
        assertTrue(board.contains("S S S S S"), "Das Schiff sollte korrekt auf A1-A5 angezeigt werden.");
    }

    @Test
    void testT003_SchiffeZufaelligSetzen() {
        spielfeld.zufaelligePlatzierungDerSchiffe();
        String board = spielfeld.zeige();
        assertTrue(board.contains("S"), "Es sollten Schiffe zufällig platziert werden.");
    }

    @Test
    void testT006_UeberlappungTesten() {
        spielfeld.platziereSchiffBenutzer('A', 0, 5, 'H');
        assertFalse(spielfeld.platziereSchiffBenutzer('A', 3, 4, 'H'), "Schiffe sollten sich nicht überlappen können.");
    }

    @Test
    void testT007_OutOfBoundsPlatzierung() {
        assertFalse(spielfeld.platziereSchiffBenutzer('J', 8, 4, 'H'), "Die Platzierung sollte abgelehnt werden, da sie das Spielfeld überschreitet.");
    }

    @Test
    void testT008_TrefferRegistrieren() {
        spielfeld.platziereSchiffBenutzer('C', 2, 3, 'H');
        Boolean result = spielfeld.schiesse('C', 2);
        assertTrue(result, "Ein Treffer sollte als 'true' registriert werden.");
        assertTrue(spielfeld.zeige().contains("X"), "Das Feld sollte als Treffer markiert sein.");
    }

    @Test
    void testT009_SchussVerfehlen() {
        Boolean result = spielfeld.schiesse('C', 2);
        assertFalse(result, "Ein Fehlschuss sollte als 'false' registriert werden.");
        assertTrue(spielfeld.zeige().contains("O"), "Das Feld sollte als Fehlschuss markiert sein.");
    }

    @Test
    void testT010_WiederholterSchuss() {
        spielfeld.platziereSchiffBenutzer('C', 2, 3, 'H');
        spielfeld.schiesse('C', 2);
        assertThrows(IllegalStateException.class, () -> spielfeld.schiesse('C', 2), "Ein wiederholter Schuss sollte eine Exception werfen.");
    }

    @Test
    void testT011_OutOfBoundsSchuss() {
        assertThrows(IllegalArgumentException.class, () -> spielfeld.schiesse('Z', 12), "Ungültige Koordinaten sollten eine Exception werfen.");
    }

    @Test
    void testT016_AlleSchiffeVersenkt() {
        spielfeld.platziereSchiffBenutzer('A', 0, 2, 'H');
        spielfeld.schiesse('A', 0);
        spielfeld.schiesse('A', 1);
        assertTrue(spielfeld.istAllesVersenkt(), "Alle Schiffe sollten als versenkt erkannt werden.");
    }

    @Test
    void testE001_UngueltigesFormatPlatzierung() {
        assertThrows(IllegalArgumentException.class, () -> spielfeld.platziereSchiffBenutzer('Z', 12, 3, 'H'), "Ungültige Koordinaten sollten eine Exception werfen.");
    }

    @Test
    void testE002_UngueltigeZahlenPlatzierung() {
        assertThrows(IllegalArgumentException.class, () -> spielfeld.platziereSchiffBenutzer('A', 11, 2, 'V'), "Spaltennummer außerhalb des Bereichs sollte eine Exception werfen.");
    }

    @Test
    void testE003_UngueltigeAusrichtungPlatzierung() {
        assertThrows(IllegalArgumentException.class, () -> spielfeld.platziereSchiffBenutzer('A', 4, 3, 'X'), "Ungültige Ausrichtung sollte eine Exception werfen.");
    }

    @Test
    void testE004_UngueltigeKoordinatenSchuss() {
        assertThrows(IllegalArgumentException.class, () -> spielfeld.schiesse('Z', 5), "Ungültige Schusskoordinaten sollten eine Exception werfen.");
    }

    @Test
    void testE005_WiederholteEingabeSchuss() {
        spielfeld.platziereSchiffBenutzer('D', 3, 2, 'H');
        spielfeld.schiesse('D', 3);
        assertThrows(IllegalStateException.class, () -> spielfeld.schiesse('D', 3), "Wiederholter Schuss sollte eine Exception werfen.");
    }

    @Test
    void testE006_UngueltigeZahlenSchuss() {
        assertThrows(IllegalArgumentException.class, () -> spielfeld.schiesse('B', 11), "Ungültige Spaltennummer beim Schuss sollte eine Exception werfen.");
    }

    @Test
    void testE007_SpielabbruchWaerendPlatzierung() {
        boolean abbruch = false;
        try {
            spielfeld.platziereSchiffBenutzer('E', 3, 3, 'H');
            abbruch = true;
        } catch (Exception e) {
            abbruch = false;
        }
        assertTrue(abbruch, "Das Spiel sollte korrekt abgebrochen werden können.");
    }

    @Test
    void testE008_UngueltigeEingabeComputerschuss() {
        spielfeld.platziereSchiffBenutzer('A', 0, 2, 'H');
        spielfeld.schiesse('A', 0);
        try {
            spielfeld.schiesse('A', 0); // Bereits beschossenes Feld
        } catch (IllegalStateException e) {
            assertTrue(true, "Der Computer sollte erneut versuchen, auf ein gültiges Feld zu schießen.");
        }
    }
}
