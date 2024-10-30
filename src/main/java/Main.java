import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int GRID_SIZE = 10;
    private static final char WATER = '~';
    private static final char HIT = 'X';
    private static final char MISS = 'O';
    private static final char SHIP = 'S';
    private static final String[] SHIPS = {"Admiral Arnold", "Don Mario", "King Stefan", "Prince Lukas", "The RaZer"};
    private static final int[] SHIP_SIZES = {5, 4, 3, 3, 2};


    private char[][] playerGrid = new char[GRID_SIZE][GRID_SIZE];
    private char[][] computerGrid = new char[GRID_SIZE][GRID_SIZE];
    private char[][] computerHiddenGrid = new char[GRID_SIZE][GRID_SIZE];
    private Scanner scanner = new Scanner(System.in);

    //kommentar
    public static void main(String[] args) {
        Main game = new Main();
        game.initializeGrids();
        System.out.println("Spielfeld zur Orientierung:");
        game.printGrid(game.playerGrid, false); // Anzeige des leeren Spielfelds
        game.manualPlaceShips(game.playerGrid);
        game.placeShips(game.computerGrid);
        game.runGame();
    }

    // Initialisiere das Spielfeld
    private void initializeGrids() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                playerGrid[i][j] = WATER;
                computerGrid[i][j] = WATER;
                computerHiddenGrid[i][j] = WATER;
            }
        }
    }

    // Schiffe manuell platzieren
    private void manualPlaceShips(char[][] grid) {
        System.out.println("Platzieren Sie Ihre Schiffe.");
        for (int i = 0; i < SHIPS.length; i++) {
            boolean placed = false;
            while (!placed) {
                System.out.printf("Platzierung für %s (%d Felder). Geben Sie Position und Richtung (z. B. A5 H oder B4 V): ", SHIPS[i], SHIP_SIZES[i]);
                String input = scanner.nextLine().toUpperCase();
                int row = input.charAt(0) - 'A';
                int col = Integer.parseInt(input.substring(1, input.length() - 2)) - 1;
                char direction = input.charAt(input.length() - 1);

                if (canPlaceShip(grid, row, col, SHIP_SIZES[i], direction)) {
                    for (int j = 0; j < SHIP_SIZES[i]; j++) {
                        if (direction == 'H') {
                            grid[row][col + j] = SHIP;
                        } else if (direction == 'V') {
                            grid[row + j][col] = SHIP;
                        }
                    }
                    placed = true;
                    System.out.println(SHIPS[i] + " platziert.");
                    printGrid(grid, false); // Spielfeld nach jeder Platzierung anzeigen
                } else {
                    System.out.println("Ungültige Position. Versuchen Sie es erneut.");
                }
            }
        }
    }

    // Überprüfe, ob das Schiff platziert werden kann
    private boolean canPlaceShip(char[][] grid, int row, int col, int size, char direction) {
        if (direction == 'H') {
            if (col + size > GRID_SIZE) return false;
            for (int j = 0; j < size; j++) {
                if (grid[row][col + j] == SHIP) return false;
            }
        } else if (direction == 'V') {
            if (row + size > GRID_SIZE) return false;
            for (int j = 0; j < size; j++) {
                if (grid[row + j][col] == SHIP) return false;
            }
        }
        return true;
    }

    // Schiffe automatisch auf dem Computer-Spielfeld platzieren
    private void placeShips(char[][] grid) {
        Random random = new Random();
        for (int i = 0; i < SHIPS.length; i++) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(GRID_SIZE);
                int col = random.nextInt(GRID_SIZE);
                char direction = random.nextBoolean() ? 'H' : 'V';

                // Überprüfen, ob das Schiff an der zufälligen Position platziert werden kann
                if (canPlaceShip(grid, row, col, SHIP_SIZES[i], direction)) {
                    for (int j = 0; j < SHIP_SIZES[i]; j++) {
                        if (direction == 'H') {
                            grid[row][col + j] = SHIP;
                        } else if (direction == 'V') {
                            grid[row + j][col] = SHIP;
                        }
                    }
                    placed = true;
                    System.out.println(SHIPS[i] + " platziert auf Computer-Spielfeld.");
                }
            }
        }
    }

    // Spielablauf
    private void runGame() {
        boolean playerTurn = true;
        while (true) {
            System.out.println("Spieler Spielfeld:");
            printGrid(playerGrid, false);
            System.out.println("Computer Spielfeld:");
            printGrid(computerHiddenGrid, true);

            if (playerTurn) {
                System.out.println("Dein Zug! Gib die Koordinate ein (z.B., A5): ");
                String input = scanner.nextLine().toUpperCase();
                int row = input.charAt(0) - 'A';
                int col = Integer.parseInt(input.substring(1)) - 1;
                if (takeShot(computerGrid, computerHiddenGrid, row, col)) {
                    System.out.println("Treffer!");
                    if (isGameOver(computerGrid)) {
                        System.out.println("Herzlichen Glückwunsch! Du hast gewonnen!");
                        break;
                    }
                } else {
                    System.out.println("Fehlschuss.");
                    playerTurn = false;
                }
            } else {
                System.out.println("Computer ist am Zug.");
                Random random = new Random();
                int row, col;
                do {
                    row = random.nextInt(GRID_SIZE);
                    col = random.nextInt(GRID_SIZE);
                } while (playerGrid[row][col] == HIT || playerGrid[row][col] == MISS);
                if (takeShot(playerGrid, playerGrid, row, col)) {
                    System.out.println("Computer hat getroffen!");
                    if (isGameOver(playerGrid)) {
                        System.out.println("Der Computer hat gewonnen.");
                        break;
                    }
                } else {
                    System.out.println("Computer hat verfehlt.");
                    playerTurn = true;
                }
            }
        }
    }

    // Schuss abgeben und Rückmeldung geben
    private boolean takeShot(char[][] targetGrid, char[][] displayGrid, int row, int col) {
        if (targetGrid[row][col] == SHIP) {
            targetGrid[row][col] = HIT;
            displayGrid[row][col] = HIT;
            return true;
        } else if (targetGrid[row][col] == WATER) {
            targetGrid[row][col] = MISS;
            displayGrid[row][col] = MISS;
            return false;
        }
        return false;
    }

    // Spielfeld ausgeben
    private void printGrid(char[][] grid, boolean hideShips) {
        System.out.print("  ");
        for (int i = 1; i <= GRID_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < GRID_SIZE; j++) {
                if (hideShips && grid[i][j] == SHIP) {
                    System.out.print(WATER + " ");
                } else {
                    System.out.print(grid[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    // Überprüfen, ob alle Schiffe versenkt sind
    private boolean isGameOver(char[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == SHIP) {
                    return false;
                }
            }
        }
        return true;
    }
}
