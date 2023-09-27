



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

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
        Board board = new Board(width[1], width[0]);
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
                case 'p' -> displayShopAndListener();
             }
            handleInteraction(board, c);
            if (operationalMove.indexOf(c) != -1) { // monster only moves on suitable input
                board.monsterMoving();
            }
            clearScreen();
        }

    }

    private static void displayShopAndListener() throws IOException {
        clearScreen();
        Shop shop = Shop.getInstance();
        shop.shopOpen();
        char input;
        while((input = (char) System.in.read()) != 'q') {
            if (Character.isDigit(input)) {
                int chosenItemIndex = Character.getNumericValue(input);
                Item chosenItem = shop.getBoughtItem(chosenItemIndex);
                // TODO more logic to check valid buy and add new item to player's inventory
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
            String[] dims = br.readLine().split(" ");
            dimensions[0] = Integer.parseInt(dims[0]); // Height
            dimensions[1] = Integer.parseInt(dims[1]); // Width
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dimensions;
    }

    /**
     * Handles various interactions within the game based on the provided input.
     * - For the input 'e', checks for a nearby character and initiates a conversation if the character is an NPC.
     * - For the input 'r', identifies a target and makes the player attack the target if one is present.
     *
     * @param board The game board context within which the interactions are checked and executed.
     * @param input The character input that represents the type of interaction.
     * @author Michael Galland
     */
    private static void handleInteraction(Board board, char input) {
        switch (input) {
            case 'e': // 'e' for interact
                GameCharacter nearbyCharacter = board.getNearbyCharacter();
                if (nearbyCharacter instanceof NPC) {
                    ((NPC) nearbyCharacter).converse(board.getPlayer());
                }
                break;
            case 'r': // 'r' for attack
                GameCharacter target = board.getAttackableTarget();
                if(target != null) {
                    board.getPlayer().attack(target);
                }
                break;
            // Add cases for other interactions
        }
    }




}

