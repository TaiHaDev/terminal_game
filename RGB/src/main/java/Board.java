import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private char[][] grid;
    private int playerX;
    private int playerY;
    private static final char PLAYER_SYMBOL = '*';
    private static final char EMPTY_SYMBOL = ' ';
    private static final char DOOR_SYMBOL = '+';
    private static final char VERTICAL_BARRIER = '-';
    private static final char HORIZONTAL_BARRIER = '|';



    public Board(int width, int height) {
        grid = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == height - 1 || i == 0) {
                    grid[i][j] = '-';
                } else if (j == width - 1 || j == 0) {
                    grid[i][j] = '|';
                } else {
                    grid[i][j] = EMPTY_SYMBOL;
                }
            }
        }
        // Starting position of the player in the center
        playerX = width / 2;
        playerY = height / 2;
        grid[playerY][playerX] = PLAYER_SYMBOL;

        List<Room> rooms = generateRandomRooms(16, 70, 15, 45, 5);
        applyRoom(rooms);
    }

    private void applyRoom(List<Room> rooms) {
        for (Room room : rooms) {
            int r = room.getY();
            int c = room.getX();
            int roomWidth = room.getWidth();
            int roomHeight = room.getHeight();
            for (int i = 0; i <= roomWidth; i++) {
                grid[r][c + i] = '-';
                grid[r + roomHeight][c + i] = '-';
            }
            for (int i = 0; i < roomHeight; i++) {
                grid[r + i][c] = '|';
                grid[r + i][c + roomWidth] = '|';
            }
            for (DoorDirection doorDirection : room.getDoorDirection()) {
                switch (doorDirection) {
                    case TOP -> {
                        grid[r][c + roomWidth / 2] = DOOR_SYMBOL;
                    }
                    case BOTTOM -> {
                        grid[r + roomHeight][c + roomWidth / 2] = DOOR_SYMBOL;
                    }
                    case LEFT -> {
                        grid[r + roomHeight / 2][c] = DOOR_SYMBOL;
                    }
                    default -> {
                        grid[r + roomHeight / 2][c  + roomWidth] = DOOR_SYMBOL;
                    }
                }
            }
        }
    }

    public void display() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j]);
            }
            if (i != grid.length - 1) {
                System.out.print('\r');  // Move cursor to the start of the line
                System.out.println();   // Move cursor to the next line
            }
        }
    }


    public void movePlayer(int deltaX, int deltaY) {
        int newX = playerX + deltaX;
        int newY = playerY + deltaY;
        char cur = grid[newY][newX];
        if (isValidMove(newX, newY) && cur != VERTICAL_BARRIER && cur != HORIZONTAL_BARRIER) {
            grid[playerY][playerX] = EMPTY_SYMBOL;
            playerX = newX;
            playerY = newY;
            grid[playerY][playerX] = PLAYER_SYMBOL;
        }
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < grid[0].length && y >= 0 && y < grid.length;
    }
    List<Room> generateRandomRooms(int maxRooms, int maxWidth, int maxHeight, int minWidth, int minHeight) {
        List<Room> rooms = new ArrayList<>();
        // create room in the middle where player spawns
        rooms.add(new Room(playerX - 20, playerY - 5, 40 , 10,
                List.of(DoorDirection.TOP, DoorDirection.LEFT, DoorDirection.BOTTOM, DoorDirection.RIGHT)));

        int gridWidth = grid[0].length;
        int gridHeight = grid.length;
        Random rand = new Random();
        for (int i = 0; i < maxRooms; i++) {
            int width = rand.nextInt(maxWidth - minWidth) + minWidth + 1;
            int height = rand.nextInt(maxHeight - minHeight) + minHeight + 1;
            int x = rand.nextInt(gridWidth - width);
            int y = rand.nextInt(gridHeight - height);

            Room newRoom = new Room(x, y, width, height, calculateDoorPlacement(x, y));

            boolean intersect = false;

            for (Room room : rooms) {
                if (newRoom.intersects(room)) {
                    intersect = true;
                    break;
                }
            }

            if (!intersect) {
                rooms.add(newRoom);
            }
        }
        return rooms;
    }

    private List<DoorDirection> calculateDoorPlacement(int x, int y) {
        List<DoorDirection> res = new ArrayList<>();
        int halfHeight = grid.length / 2;
        int halfWidth = grid[0].length / 2;
        if (x < halfWidth) {
            res.add(DoorDirection.RIGHT);
        } else {
            res.add(DoorDirection.LEFT);
        }
        if (y < halfHeight) {
            res.add(DoorDirection.BOTTOM);
        } else {
            res.add(DoorDirection.TOP);
        }
        return res;
    }
}