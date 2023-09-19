



import java.io.IOException;
import java.util.Scanner;

public class Testing {
    public static void main(String[] args) throws IOException {
        Board board = new Board(10, 10);
        setTerminalToCharMode();
        while (true) {
            board.display();
            char c = (char) System.in.read();
            switch (c) {
                case 'q' -> {
                    return;
                }
                case 'A' -> board.movePlayer(0, -1);
                case 'B' -> board.movePlayer(0, 1);
                case 'C' -> board.movePlayer(-1, 0);
                case 'D' -> board.movePlayer(1, 0);
            }
            clearScreen();
        }

    }



    public static void clearScreen() {
        System.out.print("\033[2J\033[H");
        System.out.flush();
    }
    public static void hideCursor() {
        System.out.println("\033[?25l");
    }

    private static void setTerminalToCharMode() {
        try {
            Runtime.getRuntime().exec(new String[]{"sh", "-c", "stty raw -echo < /dev/tty"});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void resetTerminalToLineMode() {
        try {
            Runtime.getRuntime().exec(new String[]{"sh", "-c", "stty sane < /dev/tty"});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

