package battleship;

import battleship.ui.UserInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Controller {

    private static final Logger logger = Logger.getLogger(Controller.class.getName());

    Spieler spieler;
    Spieler computer;

    // Für intelligentes Zielen
    private final List<int[]> trefferListe = new ArrayList<>();

    public Controller() {
        spieler = new Spieler("Spieler");
        computer = new Spieler("Computer");
    }
    

    public void run() {
        spieler.benutzerPlatziertSchiffe();
        computer.zufaelligePlatzierungDerSchiffe();

        while (true) {
            if (!schiessMechanik()) {
                logger.info("Spiel wurde abgebrochen. Vielen Dank fürs Spielen!");
                resetGame();
                break;
            }

            // Überprüfen, ob ein Spieler gewonnen hat
            if (computer.getSpielfeld().istAllesVersenkt()) {
                logger.info(spieler.getName() + " hat gewonnen!");
                resetGame();
                break;
            }
            if (spieler.getSpielfeld().istAllesVersenkt()) {
                logger.info(computer.getName() + " hat gewonnen!");
                resetGame();
                break;
            }
        }
    }

    public boolean schiessMechanik() {
        Scanner scanner = new Scanner(System.in);

        // Spieler schießt
        while (true) {
            logger.info("\nDein Spielfeld:");
            UserInterface.print(spieler.getSpielfeld().zeige());

            logger.info("Spielfeld des Computers:");
            UserInterface.print(computer.getSpielfeld().zeigeVerdeckt());

            logger.info("Geben Sie die Koordinaten für Ihren Schuss ein (z. B. A5) oder 'exit' zum Beenden: ");
            String eingabe = scanner.nextLine().toUpperCase(); // Konvertiere Eingabe in Großbuchstaben

            if (eingabe.equals("EXIT")) {
                return false; // Spielabbruch
            }

            if (!isValidInput(eingabe)) {
                logger.warning("Ungültige Eingabe. Bitte verwenden Sie das Format 'A5'.");
                continue;
            }

            char zeile = eingabe.charAt(0);
            int spalte = Integer.parseInt(eingabe.substring(1)) - 1;

            Boolean treffer = computer.getSpielfeld().schiesse(zeile, spalte);
            if (treffer == null) {
                // Feld wurde bereits getroffen, Benutzer muss erneut eingeben
                continue;
            } else if (treffer) {
                logger.info("Treffer!");
                break;
            } else {
                logger.info("Fehlschuss!");
                break;
            }
        }

        // Computer schießt
        computerZug();
        return true; // Spiel läuft weiter
    }

    boolean isValidInput(String eingabe) {
        if (eingabe.length() < 2) {
            return false;
        }

        char zeile = eingabe.charAt(0);
        if (zeile < Spielfeld.FIRSTLINE || zeile >= Spielfeld.FIRSTLINE + Spielfeld.GROESSE) {
            return false; // Zeile außerhalb des Spielfelds
        }

        try {
            int spalte = Integer.parseInt(eingabe.substring(1)) - 1;
            if (spalte < 0 || spalte >= Spielfeld.GROESSE) {
                return false; // Spalte außerhalb des Spielfelds
            }
        } catch (NumberFormatException e) {
            return false; // Ungültige Zahl
        }

        return true;
    }


    void computerZug() {
        logger.info("\nDer Computer führt seinen Zug aus...");
        boolean schussErfolgreich = false;

        // Intelligentes Zielen, wenn Treffer vorhanden
        if (!trefferListe.isEmpty()) {
            int[] letzterTreffer = trefferListe.get(0);
            int zeile = letzterTreffer[0];
            int spalte = letzterTreffer[1];

            // Versuchen angrenzende Felder zu treffen
            schussErfolgreich = computerVisiereAngrenzendeFelderAn(zeile, spalte);
        }

        // Zufälliger Schuss, wenn keine Trefferliste vorhanden ist
        if (!schussErfolgreich) {
            while (true) {
                char zeile = (char) (Spielfeld.FIRSTLINE + (int) (Math.random() * Spielfeld.GROESSE));
                int spalte = (int) (Math.random() * Spielfeld.GROESSE);

                // Überprüfen, ob das Feld bereits getroffen wurde
                if (spieler.getSpielfeld().istFeldBereitsGetroffen(zeile, spalte)) {
                    continue; // Wiederhole, bis ein gültiges Feld gefunden wird
                }

                Boolean treffer = spieler.getSpielfeld().schiesse(zeile, spalte);
                if (treffer != null) {
                    if (treffer) {
                        logger.info("Computer hat getroffen!");
                        trefferListe.add(new int[]{zeile - Spielfeld.FIRSTLINE, spalte});
                    } else {
                        logger.info("Computer hat daneben geschossen.");
                    }
                    break;
                }
            }
        }

        // Spielfelder nach dem Zug anzeigen
        //logger.info("\nDein Spielfeld nach dem Zug des Computers:");
       // UserInterface.print(spieler.getSpielfeld().zeige());
    }

    private boolean computerVisiereAngrenzendeFelderAn(int zeile, int spalte) {
        int[][] richtungen = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] richtung : richtungen) {
            int neueZeile = zeile + richtung[0];
            int neueSpalte = spalte + richtung[1];

            if (neueZeile >= 0 && neueZeile < Spielfeld.GROESSE &&
                    neueSpalte >= 0 && neueSpalte < Spielfeld.GROESSE &&
                    !spieler.getSpielfeld().istFeldBereitsGetroffen((char) (Spielfeld.FIRSTLINE + neueZeile), neueSpalte)) {

                Boolean treffer = spieler.getSpielfeld().schiesse((char) (Spielfeld.FIRSTLINE + neueZeile), neueSpalte);
                if (treffer != null) {
                    if (treffer) {
                        logger.info("Computer hat ein angrenzendes Feld getroffen!");
                        trefferListe.add(new int[]{neueZeile, neueSpalte});
                    } else {
                        logger.info("Computer hat ein angrenzendes Feld verfehlt.");
                    }
                    return true; // Zug erfolgreich
                }
            }
        }

        // Schiff versenkt, Trefferliste leeren
        trefferListe.clear();
        return false;
    }

    private void resetGame() {
        spieler = null;
        computer = null;
        trefferListe.clear();
        logger.info("Das Spiel wurde zurückgesetzt.");
    }

}
