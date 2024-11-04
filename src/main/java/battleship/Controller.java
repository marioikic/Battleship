package battleship;

import battleship.ui.UserInterface;

public class Controller {

    Spieler spieler;
    Spieler computer;

    public Controller() {
        spieler = new Spieler("Spieler");
        computer = new Spieler("Computer");
    }

    public Controller(String player1, String player2) {
        spieler = new Spieler(player1);
        computer = new Spieler(player2);
    }

    public void run() {
        spieler.benutzerPlatziertSchiffe();
        computer.zufaelligePlatzierungDerSchiffe();

        System.out.println(spieler.getName() + " Spielfeld:");
        Spielfeld spielfeld = spieler.getSpielfeld();
        String spielfeldAsString = spielfeld.zeige();
        UserInterface.print(spielfeldAsString);

        System.out.println(computer.getName() + " Spielfeld:");
        spielfeld = computer.getSpielfeld();
        spielfeldAsString = spielfeld.zeige();
        UserInterface.print(spielfeldAsString);
    }

}