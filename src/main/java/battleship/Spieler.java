package battleship;

import java.util.Scanner;

public class Spieler {

    private String name;

    private Spielfeld spielfeld;

    //TODO move to ship or Config class

    String[] schiffe = {"Admiral Arnold (5 Felder)", "Don Mario (4 Felder)", "King Stefan (3 Felder)",
            "Prince Lukas (3 Felder)", "The RaZer (2 Felder)"};
    int[] laengen = {5, 4, 3, 3, 2};

    public Spieler(String name) {
        this.name = name;
        this.spielfeld = new Spielfeld();
    }

    public void benutzerPlatziertSchiffe() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Platzieren Sie Ihre Schiffe:");

        for (int i = 0; i < schiffe.length; i++) {
            boolean platziert = false;
            while (!platziert) {
                System.out.println("\nAktuelles Spielfeld:");
                System.out.println(spielfeld.zeige());

                System.out.print("Position für " + schiffe[i] + " (z.B. a5v für vertikal oder b8h für horizontal): ");
                String eingabe = scanner.nextLine().toUpperCase();
                if (eingabe.length() < 3 || (eingabe.length() == 4 && !Character.isDigit(eingabe.charAt(2)))) {
                    System.out.println("Ungültiges Format. Bitte erneut versuchen.");
                    continue;
                }

                char zeile = eingabe.charAt(0);
                int spalte = Integer.parseInt(eingabe.substring(1, eingabe.length() - 1)) - 1;
                char richtung = eingabe.charAt(eingabe.length() - 1);

                if (richtung != Spielfeld.HORIZONTAL && richtung != Spielfeld.VERTIKAL) {
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
        for (int j : laengen) {
            boolean platziert = false;
            while (!platziert) {
                char zeile = (char) (Spielfeld.FIRSTLINE+ (int) (Math.random() * Spielfeld.GROESSE));
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