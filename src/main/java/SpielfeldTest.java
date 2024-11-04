

public class SpielfeldTest {
    public static void main(String[] args) {
        // Test 1: Überprüfen der leeren Spielfeldanzeige
        testLeeresSpielfeldAnzeigen();

        // Test 2: Überprüfen der Platzierung eines Schiffes und der korrekten Anzeige
        testSchiffPlatzierenUndAnzeigen();
    }

    private static void testLeeresSpielfeldAnzeigen() {
        System.out.println("Test 1: Leeres Spielfeld anzeigen");
        Spielfeld spielfeld = new Spielfeld();
        spielfeld.zeige();

        // Erwartete Ausgabe:
        //   1 2 3 4 5 6 7 8 9 10
        // A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    }

    private static void testSchiffPlatzierenUndAnzeigen() {
        System.out.println("\nTest 2: Schiff platzieren und Spielfeld anzeigen");
        Spielfeld spielfeld = new Spielfeld();

        // Ein Schiff der Länge 3 wird horizontal bei B2 platziert
        spielfeld.platziereSchiffBenutzer('B', 1, 3, 'H');
        spielfeld.zeige();

        // Erwartete Ausgabe:
        //   1 2 3 4 5 6 7 8 9 10
        // A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // B ~ S S S ~ ~ ~ ~ ~ ~
        // C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    }
}
