package battleship;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Spieler {

    private String name;
    private static final Logger logger = Logger.getLogger(Spieler.class.getName());
    private Spielfeld spielfeld;

    private String[] schiffe = {"Admiral Arnold (5 Felder)", "Don Mario (4 Felder)", "King Stefan (3 Felder)",
            "Prince Lukas (3 Felder)", "The RaZer (2 Felder)"};
    private int[] laengen = {5, 4, 3, 3, 2};

    public Spieler(String name) {
        this.name = name;
        this.spielfeld = new Spielfeld();
    }

    public void benutzerPlatziertSchiffe() {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < schiffe.length; i++) {
            boolean platziert = false;

            while (!platziert) {
                // Zeige das Spielfeld zuerst
                zeigeSpielfeld();

                // Zeige die Eingabeaufforderung unter dem Spielfeld
                String eingabe = erfasseEingabe(scanner, schiffe[i]);

                if (!validiereEingabe(eingabe)) {
                    logger.warning("Ungültiges Format. Bitte das Format A5H oder A5V verwenden.");
                    continue;
                }

                try {
                    if (platziereSchiff(eingabe, laengen[i])) {
                        platziert = true;
                        logger.info("Schiff erfolgreich platziert.");
                    } else {
                        logger.warning("Ungültige Platzierung. Bitte erneut versuchen.");
                    }
                } catch (NumberFormatException e) {
                    logger.warning("Ungültige Spalte. Bitte erneut versuchen.");
                }
            }
        }
    }

    public void zeigeSpielfeld() {
        System.out.println("\nAktuelles Spielfeld:");
        System.out.println(spielfeld.zeige());
    }

    private String erfasseEingabe(Scanner scanner, String schiffName) {
        System.out.println(String.format("Position für %s (z.B. A5H für horizontal oder A5V für vertikal):", schiffName));
        return scanner.nextLine().toUpperCase();
    }

    private boolean validiereEingabe(String eingabe) {
        return eingabe.length() >= 3 &&
                Character.isLetter(eingabe.charAt(0)) &&
                Character.isDigit(eingabe.charAt(1)) &&
                (eingabe.charAt(eingabe.length() - 1) == Spielfeld.HORIZONTAL ||
                        eingabe.charAt(eingabe.length() - 1) == Spielfeld.VERTIKAL);
    }

    private boolean platziereSchiff(String eingabe, int laenge) throws NumberFormatException {
        char zeile = eingabe.charAt(0);
        int spalte = Integer.parseInt(eingabe.substring(1, eingabe.length() - 1)) - 1;
        char richtung = eingabe.charAt(eingabe.length() - 1);

        return spielfeld.platziereSchiffBenutzer(zeile, spalte, laenge, richtung);
    }

    public void zufaelligePlatzierungDerSchiffe() {
        for (int j : laengen) {
            boolean platziert = false;
            while (!platziert) {
                char zeile = (char) (Spielfeld.FIRSTLINE + (int) (Math.random() * Spielfeld.GROESSE));
                int spalte = (int) (Math.random() * Spielfeld.GROESSE);
                char richtung = Math.random() < 0.5 ? Spielfeld.HORIZONTAL : Spielfeld.VERTIKAL;
                platziert = spielfeld.platziereSchiffBenutzer(zeile, spalte, j, richtung);
            }
        }
    }

    public Spielfeld getSpielfeld() {
        return spielfeld;
    }

    public String getName() {
        return name;
    }
}
