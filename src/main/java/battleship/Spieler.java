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
        logger.info("Platzieren Sie Ihre Schiffe:");

        for (int i = 0; i < schiffe.length; i++) {
            boolean platziert = false;
            while (!platziert) {
                zeigeSpielfeld();

                String eingabe = erfasseEingabe(scanner, schiffe[i]);

                if (!validiereEingabe(eingabe)) {
                    logger.warning("Ungültiges Format. Bitte das Format A5H oder A5V verwenden.");
                    continue;
                }

                try {
                    if (platziereSchiff(eingabe, laengen[i])) {
                        platziert = true;
                    } else {
                        logger.warning("Ungültige Platzierung. Bitte erneut versuchen.");
                    }
                } catch (NumberFormatException e) {
                    logger.warning("Ungültige Spalte. Bitte erneut versuchen.");
                }
            }
        }
    }

    private void zeigeSpielfeld() {
        if (logger.isLoggable(Level.INFO)) {
            logger.info("\nAktuelles Spielfeld:");
            logger.info(spielfeld.zeige());
        }
    }

    private String erfasseEingabe(Scanner scanner, String schiffName) {
        logger.info(() -> String.format("Position für %s (z.B. A5H für horizontal oder A5V für vertikal):", schiffName));
        return scanner.nextLine().toUpperCase();
    }

    private boolean validiereEingabe(String eingabe) {
        return eingabe.length() >= 3 &&
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
