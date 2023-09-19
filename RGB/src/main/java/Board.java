public class Board {
    private char[][] grid;
    private int playerX;
    private int playerY;

    public Board(int width, int height) {
        grid = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = '.';
            }
        }

        // Starting position of the player in the center
        playerX = width / 2;
        playerY = height / 2;
        grid[playerY][playerX] = 'P';
    }

    public void display() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.print('\r');  // Move cursor to the start of the line
            System.out.println();   // Move cursor to the next line
        }
    }


    public void movePlayer(int deltaX, int deltaY) {
        int newX = playerX + deltaX;
        int newY = playerY + deltaY;

        if (isValidMove(newX, newY)) {
            grid[playerY][playerX] = '.';
            playerX = newX;
            playerY = newY;
            grid[playerY][playerX] = 'P';
        }
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < grid[0].length && y >= 0 && y < grid.length;
    }
}