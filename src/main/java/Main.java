import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Spieler spieler = new Spieler("Spieler");
        Spieler computer = new Spieler("Computer");


        spieler.benutzerPlatziertSchiffe();


        computer.zufaelligePlatzierungDerSchiffe();


        System.out.println("Dein Spielfeld:");
        spieler.zeigeSpielfeld();

        // Zeige das Spielfeld des Computers (zur Kontrolle)
        System.out.println("\nGegners Spielfeld (verdeckt):");
        computer.zeigeSpielfeld();
    }
}
/*Spieler*/

class Spieler {
    private String name;
    private Spielfeld spielfeld;

    public Spieler(String name) {
        this.name = name;
        this.spielfeld = new Spielfeld();
    }

    public void benutzerPlatziertSchiffe() {
        Scanner scanner = new Scanner(System.in);
        String[] schiffe = {"Admiral Arnold (5 Felder)", "Don Mario (4 Felder)", "King Stefan (3 Felder)",
                "Prince Lukas (3 Felder)", "The RaZer (2 Felder)"};
        int[] laengen = {5, 4, 3, 3, 2};

        System.out.println("Platzieren Sie Ihre Schiffe:");

        for (int i = 0; i < schiffe.length; i++) {
            boolean platziert = false;
            while (!platziert) {
                System.out.println("\nAktuelles Spielfeld:");
                spielfeld.zeige();

                System.out.print("Position für " + schiffe[i] + " (z.B. a5v für vertikal oder b8h für horizontal): ");
                String eingabe = scanner.nextLine().toUpperCase();
                if (eingabe.length() < 3 || (eingabe.length() == 4 && !Character.isDigit(eingabe.charAt(2)))) {
                    System.out.println("Ungültiges Format. Bitte erneut versuchen.");
                    continue;
                }

                char zeile = eingabe.charAt(0);
                int spalte = Integer.parseInt(eingabe.substring(1, eingabe.length() - 1)) - 1;
                char richtung = eingabe.charAt(eingabe.length() - 1);

                if (richtung != 'H' && richtung != 'V') {
                    System.out.println("Ungültige Richtung. Bitte H (horizontal) oder V (vertikal) verwenden.");
                    continue;
                }

                platziert = spielfeld.platziereSchiffBenutzer(zeile, spalte, laengen[i], richtung);
                if (!platziert) {
                    System.out.println("Ungültige Platzierung. Bitte erneut versuchen.");
                }
            }
        }
    }

    public void zufaelligePlatzierungDerSchiffe() {
        int[] laengen = {5, 4, 3, 3, 2};

        for (int i = 0; i < laengen.length; i++) {
            boolean platziert = false;
            while (!platziert) {
                char zeile = (char) ('A' + (int) (Math.random() * 10));
                int spalte = (int) (Math.random() * 10);
                char richtung = Math.random() < 0.5 ? 'H' : 'V';

                platziert = spielfeld.platziereSchiffBenutzer(zeile, spalte, laengen[i], richtung);
            }
        }
    }

    public void zeigeSpielfeld() {
        spielfeld.zeige();
    }
}

class Spielfeld {
    private char[][] feld;
    private static final int GROESSE = 10;

    public Spielfeld() {
        feld = new char[GROESSE][GROESSE];
        for (int i = 0; i < GROESSE; i++) {
            for (int j = 0; j < GROESSE; j++) {
                feld[i][j] = '~';
            }
        }
    }

    public boolean platziereSchiffBenutzer(char zeile, int spalte, int laenge, char richtung) {
        int x = zeile - 'A';
        if (x < 0 || x >= GROESSE || spalte < 0 || spalte >= GROESSE) {
            return false;
        }

        if (richtung == 'H') {
            if (spalte + laenge > GROESSE) return false;
            for (int i = 0; i < laenge; i++) {
                if (feld[x][spalte + i] != '~') return false;
            }
            for (int i = 0; i < laenge; i++) {
                feld[x][spalte + i] = 'S';
            }
        } else {
            if (x + laenge > GROESSE) return false;
            for (int i = 0; i < laenge; i++) {
                if (feld[x + i][spalte] != '~') return false;
            }
            for (int i = 0; i < laenge; i++) {
                feld[x + i][spalte] = 'S';
            }
        }
        return true;
    }

    public void zeige() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < GROESSE; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < GROESSE; j++) {
                System.out.print(feld[i][j] + " ");
            }
            System.out.println();
        }
    }
}
