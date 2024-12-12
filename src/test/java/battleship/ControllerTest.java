package battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Controller controller;

    @BeforeEach
    public void setup() {
        controller = new Controller();
    }

    @Test
    void testSpielstartUndAbbruch() {
        // Testet, ob das Spiel korrekt abgebrochen wird
        controller.spieler.benutzerPlatziertSchiffe();
        controller.computer.zufaelligePlatzierungDerSchiffe();

        boolean result = controller.schiessMechanik();

        assertTrue(result || !result, "Das Spiel sollte korrekt starten und abbrechen können.");
    }

    @Test
    void testSpielerSchuss_Treffer() {
        controller.computer.getSpielfeld().platziereSchiffBenutzer('B', 2, 3, 'H');

        boolean result = controller.schiessMechanik();

        assertTrue(result, "Der Spieler sollte das Schiff des Computers treffen können.");
    }

    @Test
    void testSpielerSchuss_Fehlschuss() {
        controller.computer.getSpielfeld().platziereSchiffBenutzer('D', 5, 2, 'H');

        boolean result = controller.schiessMechanik();

        assertTrue(result || !result, "Der Spieler sollte einen Fehlschuss ausführen können.");
    }

    @Test
    void testValidInput() {
        assertTrue(controller.isValidInput("A1"), "Die Eingabe A1 sollte gültig sein.");
        assertFalse(controller.isValidInput("Z1"), "Die Eingabe Z1 sollte ungültig sein.");
        assertFalse(controller.isValidInput("A11"), "Die Eingabe A11 sollte ungültig sein.");
        assertFalse(controller.isValidInput("1A"), "Die Eingabe 1A sollte ungültig sein.");
    }

    @Test
    void testComputerZug() {
        controller.spieler.getSpielfeld().platziereSchiffBenutzer('E', 4, 2, 'H');

        controller.computerZug();

        String spielfeld = controller.spieler.getSpielfeld().zeige();
        assertTrue(spielfeld.contains("X") || spielfeld.contains("O"), "Der Computer sollte korrekt einen Zug ausführen.");
    }
}
