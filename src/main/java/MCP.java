package battleship;

import battleship.Controller; // Import für Controller
import battleship.LoggerConfig;


public class MCP {
    public static void main(String[] args) {
        // Logger konfigurieren
        LoggerConfig.configureLogger();

        // Spiel starten
        Controller ctrl = new Controller();
        ctrl.run();
    }
}
