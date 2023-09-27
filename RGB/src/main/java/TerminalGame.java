



import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;

/**
 * Works with the terminal to draw the {@link Board} and interact with the terminal
 * using keyboard input. This class makes intensive use of the ANSI escape code
 * to allow player interact with the terminal smoothly
 * @author Phuoc Ha u7454578
 */
public class TerminalGame {
    public static String operationalMove = "ABCDe"; // moving or fighting
    public static void main(String[] args) throws IOException {
        int[] width = getTerminalSize();
        System.out.println(Arrays.toString(width));
        Board board;
        int pressCount = 0;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            board = new Board(100, 100);
        } else {
            board = new Board(width[1], width[0]);
        }
        setTerminalToCharMode();
        hideCursor();
        while (true) {
            board.display();
            char c = (char) System.in.read();
            switch (c) {
                case 'q' -> {
                    resetTerminalToLineMode();
                    return;
                }
                case 'A' -> board.movePlayer(0, -1);
                case 'B' -> board.movePlayer(0, 1);
                case 'C' -> board.movePlayer(1, 0);
                case 'D' -> board.movePlayer(-1, 0);
                case 'p' -> displayShopAndListener(board);
                case 'c' -> board.collectGold();
             }
            checkState(board,pressCount);
            handleInteraction(board, c);
            if (operationalMove.indexOf(c) != -1) { // monster only moves on suitable input
                board.monsterMoving();
            }
            clearScreen();
        }

    }

    /**
     *
     * @param board read teh current board and figure out possible tips that should provided to the user
     */
    public static void checkState(Board board,int pressCount){
        if(board.getPlayer().getHealth() < 50){
            System.out.println("find a merchant to but potion");
        }
        else if(board.getNearbyMonster() != null){
            System.out.println("press E to fight the monster");
        }
        else if (pressCount != 0){
            int num = pressCount % 2;
            if (num == 0){
                System.out.println("kill all monsters to win the game");
            }
            else {
                System.out.println("buy weapon and potion using 'p' key");
            }
        }
        else {
            System.out.println("move using 'arrow key'");
        }

    }
    private static void displayShopAndListener(Board board) throws IOException {
        clearScreen();
        Shop shop = Shop.getInstance();
        shop.shopOpen();
        printOut(board.getPlayer().getStatInfo());
        char input;
        while(true) {
            clearScreen();
            shop.shopOpen();
            printOut(board.getPlayer().getStatInfo());
            input = (char) System.in.read();
            if (input == 'q') break;
            if (Character.isDigit(input)) {
                int chosenItemIndex = Character.getNumericValue(input);
                Item chosenItem = shop.getBoughtItem(chosenItemIndex - 1);
                if (chosenItem != null) {
                    board.buyItem(chosenItem);
                }
            }
        }

    }


    /**
     * Using ANSI escape code to clear the screen of the current board
     * so that a new board can be printed again on the terminal
     */
    public static void clearScreen() {
        System.out.print("\033[2J\033[H\033[3J");
        System.out.flush();
    }

    /**
     * Hide the cursor to have a better user experience when playing the game
     */
    public static void hideCursor() {
        System.out.println("\033[?25l");
        System.out.flush();
    }

    /**
     * Setting the terminal to raw mode so input can be read straight away
     * without the need to press enter
     */
    private static void setTerminalToCharMode() {
        try {
            Runtime.getRuntime().exec(new String[]{"sh", "-c", "stty raw -echo < /dev/tty"});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exit the raw mode created by {@link TerminalGame#setTerminalToCharMode()}
     */
    private static void resetTerminalToLineMode() {
        try {
            Runtime.getRuntime().exec(new String[]{"sh", "-c", "stty sane < /dev/tty"});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void printOut(String out) {
        System.out.print(out);
        System.out.println();
        System.out.print("\r");
    }
    private static void removeLastPrintLine() {
        System.out.print("\033[1A");  // Move up
        System.out.print("\033[K");  // Clear line
    }

    /**
     * Acquires the size of the terminal from OS to create suitable
     * size rooms and board that is playable
     * @return an array of length two contains the width and height of the terminal
     */
    public static int[] getTerminalSize() {
        int[] dimensions = new int[2];
        try {
            Process sizeProcess = new ProcessBuilder("sh", "-c", "stty size < /dev/tty").start();
            BufferedReader br = new BufferedReader(new InputStreamReader(sizeProcess.getInputStream()));
            if (br.readLine() != null) {
                String[] dims = br.readLine().split(" ");
                dimensions[0] = Integer.parseInt(dims[0]); // Height
                dimensions[1] = Integer.parseInt(dims[1]); // Width
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dimensions;
    }

    private static void handleInteraction(Board board, char input) {
        switch (input) {
            case 'e' -> { // 'E' for interact
                GameCharacter nearbyCharacter = board.getNearbyCharacter(); // method to get character near the player
                if (nearbyCharacter instanceof NPC) {
                    ((NPC) nearbyCharacter).converse(board.getPlayer()); // Note: converse method may not need player now
                }
            }
            case 'r' -> { // 'R' for attack
                Map.Entry<Monster, Point> entry = board.getAttackableTarget();
                if (entry == null) break;
                Monster target = entry.getKey(); // method to get characters that can be attacked
                if (target != null) {
                    Player player = board.getPlayer();
                    player.fight(target);
                    if (target.getHealth() <= 0) {
                        board.removeMonster(entry.getValue());
                    }
                    if (player.getHealth() <= 0 || board.countMonster() == 0) {
                        endGame();
                    }
                }
            }
            // Add cases for other interactions
        }
    }

    private static void endGame() {
        clearScreen();
        System.out.print("Game over! Do you want to play again ? (y/n)");
        try {
            char input = (char) System.in.read();
            if (input == 'y') {
                main(null);
            } else if (input == 'n') {
                System.exit(0);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }


}

