package battleship;

public class Spielfeld {

    public static final char FIRSTLINE = 'A';
    public static final char HORIZONTAL = 'H';
    public static final char VERTIKAL = 'V';
    public static final int GROESSE = 10;
    public static final char WASSER = '~';
    public static final char SCHIFF = 'S';
    public static final char TREFFER = 'X';
    public static final char FEHLSCHUSS = 'O';

    private char[][] feld;

    public Spielfeld() {
        feld = new char[GROESSE][GROESSE];
        for (int i = 0; i < GROESSE; i++) {
            for (int j = 0; j < GROESSE; j++) {
                feld[i][j] = WASSER;
            }
        }
    }

    public boolean platziereSchiffBenutzer(char zeile, int spalte, int laenge, char richtung) {
        int x = zeile - FIRSTLINE;

        // Prüfen, ob die Zeile und Spalte im gültigen Bereich liegen
        if (zeile < FIRSTLINE || zeile >= FIRSTLINE + GROESSE) {
            throw new IllegalArgumentException("Zeilenkoordinate außerhalb des gültigen Bereichs.");
        }

        if (spalte < 0 || spalte >= GROESSE) {
            throw new IllegalArgumentException("Spaltennummer außerhalb des gültigen Bereichs.");
        }

        // Prüfen, ob die Ausrichtung gültig ist
        if (richtung != HORIZONTAL && richtung != VERTIKAL) {
            throw new IllegalArgumentException("Ungültige Ausrichtung. Nur H (horizontal) oder V (vertikal) erlaubt.");
        }

        if (!isWithinBounds(x, spalte, laenge, richtung)) {
            return false;
        }

        if (!isPlacementValid(x, spalte, laenge, richtung)) {
            return false;
        }

        placeShip(x, spalte, laenge, richtung);
        return true;
    }


    private boolean isWithinBounds(int x, int spalte, int laenge, char richtung) {
        if (x < 0 || x >= GROESSE || spalte < 0 || spalte >= GROESSE) {
            return false;
        }

        if (richtung == HORIZONTAL) {
            return spalte + laenge <= GROESSE;
        } else {
            return x + laenge <= GROESSE;
        }
    }

    private boolean isPlacementValid(int x, int spalte, int laenge, char richtung) {
        for (int i = 0; i < laenge; i++) {
            int currentX = richtung == HORIZONTAL ? x : x + i;
            int currentSpalte = richtung == HORIZONTAL ? spalte + i : spalte;

            if (feld[currentX][currentSpalte] != WASSER) {
                return false;
            }
        }
        return true;
    }

    private void placeShip(int x, int spalte, int laenge, char richtung) {
        for (int i = 0; i < laenge; i++) {
            int currentX = richtung == HORIZONTAL ? x : x + i;
            int currentSpalte = richtung == HORIZONTAL ? spalte + i : spalte;

            feld[currentX][currentSpalte] = SCHIFF;
        }
    }

    public Boolean schiesse(char zeile, int spalte) {
        int x = zeile - FIRSTLINE;

        // Überprüfen auf ungültige Koordinaten
        if (x < 0 || x >= GROESSE || spalte < 0 || spalte >= GROESSE) {
            throw new IllegalArgumentException("Ungültige Koordinaten: (" + zeile + ", " + spalte + ")");
        }

        // Überprüfen auf bereits getroffenes Feld
        if (feld[x][spalte] == TREFFER || feld[x][spalte] == FEHLSCHUSS) {
            throw new IllegalStateException("Das Feld wurde bereits getroffen: (" + zeile + ", " + spalte + ")");
        }

        // Treffer oder Fehlschuss markieren
        if (feld[x][spalte] == SCHIFF) {
            feld[x][spalte] = TREFFER; // Treffer markieren
            return true;
        } else if (feld[x][spalte] == WASSER) {
            feld[x][spalte] = FEHLSCHUSS; // Fehlschuss markieren
            return false;
        }

        // Sollte nie erreicht werden
        throw new IllegalStateException("Unerwarteter Zustand bei der Schussverarbeitung.");
    }



    public boolean istAllesVersenkt() {
        for (int i = 0; i < GROESSE; i++) {
            for (int j = 0; j < GROESSE; j++) {
                if (feld[i][j] == SCHIFF) {
                    return false;
                }
            }
        }
        return true;
    }

    public String zeige() {
        StringBuilder sb = new StringBuilder();

        // Zeilenbeschriftung (Platz für die Spaltennummern)
        sb.append("  ");
        for (int i = 1; i <= GROESSE; i++) {
            sb.append(i).append(" "); // Spaltennummer mit nur einem Leerzeichen
        }
        sb.append("\n");

        // Spielfeld mit Zeilenbeschriftung und Feldern
        for (int i = 0; i < GROESSE; i++) {
            sb.append((char) (FIRSTLINE + i)).append(" "); // Zeilenbeschriftung
            for (int j = 0; j < GROESSE; j++) {
                sb.append(feld[i][j]).append(" "); // Nur ein Leerzeichen zwischen den Feldern
            }
            sb.append("\n");
        }
        return sb.toString();
    }





    public String zeigeVerdeckt() {
        StringBuilder sb = new StringBuilder();
        sb.append("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < GROESSE; i++) {
            sb.append((char) (FIRSTLINE + i)).append(" ");
            for (int j = 0; j < GROESSE; j++) {
                if (feld[i][j] == SCHIFF) {
                    sb.append(WASSER).append(" "); // Verstecke Schiffe
                } else {
                    sb.append(feld[i][j]).append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void zufaelligePlatzierungDerSchiffe() {
        int[] schiffLaengen = {5, 4, 3, 3, 2}; // Beispielhafte Längen der Schiffe
        for (int laenge : schiffLaengen) {
            boolean platziert = false;
            while (!platziert) {
                char zeile = (char) (FIRSTLINE + (int) (Math.random() * GROESSE));
                int spalte = (int) (Math.random() * GROESSE);
                char richtung = Math.random() < 0.5 ? HORIZONTAL : VERTIKAL;
                platziert = platziereSchiffBenutzer(zeile, spalte, laenge, richtung);
            }
        }
    }

}
