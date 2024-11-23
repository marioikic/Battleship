package battleship.ui;

import java.util.Scanner;
import java.util.logging.Logger;

public class UserInterface {

    private static final Logger logger = Logger.getLogger(UserInterface.class.getName());
    private static final Scanner scanner = new Scanner(System.in);

    // Privater Konstruktor, um Instanziierung zu verhindern
    private UserInterface() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void print(String message) {
        logger.info(message); // Standardausgabe für Informationen
    }

    public static String readInput(String prompt) {
        logger.info(prompt); // Eingabeaufforderung loggen
        return scanner.nextLine();
    }

    public static void printError(String errorMessage) {
        logger.warning(errorMessage); // Fehler als Warnung loggen
    }
}
