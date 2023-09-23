



import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.Wincon;

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
    public static void main(String[] args) throws IOException {
        int[] width = getTerminalSize();
        Board board = new Board(width[1], width[0]);
        setTerminalToCharMode();
        hideCursor();
        while (true) {
            board.display();
            char c = (char) System.in.read();
            switch (c) {
                case 'q' : {
                    resetTerminalToLineMode();
                    return;
                }
                case 'A' :
                    board.movePlayer(0, -1);
                    break;
                case 'B' :
                    board.movePlayer(0, 1);
                    break;
                case 'C' :
                    board.movePlayer(1, 0);
                    break;
                case 'D' :
                    board.movePlayer(-1, 0);
                    break;
                case 'p' :
                    displayShopAndListener();
                    break;
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

    /**
     * Acquires the size of the terminal from OS to create suitable
     * size rooms and board that is playable
     * @return an array of length two contains the width and height of the terminal
     */
    public static int[] getTerminalSize() {
        int[] dimensions = new int[2];
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                WinNT.HANDLE h = Kernel32.INSTANCE.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
                Wincon.CONSOLE_SCREEN_BUFFER_INFO bufferInfo = new Wincon.CONSOLE_SCREEN_BUFFER_INFO();
                MyKernel32.INSTANCE.GetConsoleScreenBufferInfo(h, bufferInfo);
                dimensions[0] = bufferInfo.srWindow.Bottom - bufferInfo.srWindow.Top + 1;
                dimensions[1] = bufferInfo.srWindow.Right - bufferInfo.srWindow.Left + 1;
            } else {
                Process sizeProcess = new ProcessBuilder("sh", "-c", "stty size < /dev/tty").start();
                BufferedReader br = new BufferedReader(new InputStreamReader(sizeProcess.getInputStream()));
                String[] dims = br.readLine().split(" ");
                dimensions[0] = Integer.parseInt(dims[0]);  // Height
                dimensions[1] = Integer.parseInt(dims[1]);  // Width
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dimensions;
    }
    interface MyKernel32 extends Kernel32 {
        MyKernel32 INSTANCE = Native.load("kernel32", MyKernel32.class);
        boolean GetConsoleScreenBufferInfo(WinNT.HANDLE hConsoleOutput, Wincon.CONSOLE_SCREEN_BUFFER_INFO lpConsoleScreenBufferInfo);
    }

    public static class CONSOLE_SCREEN_BUFFER_INFO extends WinDef.RECT {
        public COORD dwSize = new COORD();
        public COORD dwCursorPosition = new COORD();
        public short wAttributes;
        public SMALL_RECT srWindow = new SMALL_RECT();
        public COORD dwMaximumWindowSize = new COORD();
    }

    public static class COORD extends WinDef.RECT {
        public short X;
        public short Y;
    }

    public static class SMALL_RECT extends WinDef.RECT {
        public short Left;
        public short Top;
        public short Right;
        public short Bottom;
    }
}

