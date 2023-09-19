



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Testing {
    public static void main(String[] args) throws IOException {
        int[] width = getTerminalSize();
        Board board = new Board(width[1], width[0]);
        setTerminalToCharMode();
        hideCursor();
        while (true) {
            board.display();
            char c = (char) System.in.read();
            switch (c) {
                case 'q' -> {
                    return;
                }
                case 'A' -> board.movePlayer(0, -1);
                case 'B' -> board.movePlayer(0, 1);
                case 'C' -> board.movePlayer(1, 0);
                case 'D' -> board.movePlayer(-1, 0);
            }
            clearScreen();
        }

    }



    public static void clearScreen() {
        System.out.print("\033[2J\033[H\033[3J");
        System.out.flush();
    }
    public static void hideCursor() {
        System.out.println("\033[?25l");
        System.out.flush();
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
}

