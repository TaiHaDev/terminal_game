import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Defines a board that represents the game state of the roguelike game
 * and also contain some logic to randomly create rooms in the board
 * and display the current state of the board to the terminal
 * @author Phuoc Ha
 */
public class Board {
    private char[][] grid = new char[0][];
    private char lastMove = ' ';
    private Map<Point, GameCharacter> charactersMap = new HashMap<>();
    private int playerX;
    private int playerY;
    private static final char PLAYER_SYMBOL = '*';
    private static final char EMPTY_SYMBOL = ' ';
    public static final char DOOR_SYMBOL = '+';
    public static final char VERTICAL_BARRIER = '-';
    public static final char HORIZONTAL_BARRIER = '|';
    private static final char GOLD_SYMBOL = 'G';
    private static final String MONSTER_SYMBOL_COLLECTIONS = "BSZ";
    private static final char NPC_SYMBOL = 'N';
    private static final int GOLD_VALUE = 25;

    private Player player = new Player("Explorer", 100, 10, 10);



    public Board(int width, int height) {
        // height preservation for other part of the game
        if (height > 0 && width > 0) {
            height -= 1;
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
        }

        // Starting position of the player in the center
        playerX = width / 2;
        playerY = height / 2;
        grid[playerY][playerX] = PLAYER_SYMBOL;

        // generating room to be placed in the board grid
        List<Room> rooms = generateRandomRooms(20, width / 4, height / 4, width / 7, height / 7);
        applyRoom(rooms);
    }

    /**
     * This functions intakes a list of rooms and draw them on the board {@link Board#grid}
     * and as well draw the door for each of them based on the information the {@link Room} class
     * provide
     * @param rooms a list of room that are randomly generated by {@link Board#generateRandomRooms(int, int, int, int, int)}
     */
    public void applyRoom(List<Room> rooms) {
        for (Room room : rooms) {
            int r = room.getY();
            int c = room.getX();
            int roomWidth = room.getWidth();
            int roomHeight = room.getHeight();
            // construct each room in the grid horizontally and vertically
            for (int i = 0; i <= roomWidth; i++) {
                grid[r][c + i] = '-';
                grid[r + roomHeight][c + i] = '-';
            }
            for (int i = 0; i < roomHeight; i++) {
                grid[r + i][c] = '|';
                grid[r + i][c + roomWidth] = '|';
            }

            // create door for the room in the grid
            for (DoorDirection doorDirection : room.getDoorDirection()) {
                switch (doorDirection) {
                    case TOP : {
                        grid[r][c + roomWidth / 2] = DOOR_SYMBOL;
                        break;
                    }
                    case BOTTOM : {
                        grid[r + roomHeight][c + roomWidth / 2] = DOOR_SYMBOL;
                        break;
                    }
                    case LEFT : {
                        grid[r + roomHeight / 2][c] = DOOR_SYMBOL;
                        break;
                    }
                    default : {
                        grid[r + roomHeight / 2][c  + roomWidth] = DOOR_SYMBOL;
                    }
                }
            }
            // create NPC
            randomlyCreateNPC(r+1, c+1);
            // generate monster in room
            createMonster(r, c, roomHeight, roomWidth);
            // generate golds in room
            generateGold(r, c, roomHeight, roomWidth);
        }
    }

    private void generateGold(int r, int c, int roomHeight, int roomWidth) {
        int totalEmptySpace = 0;
        for (int row = r + 1; row < r + roomHeight - 1; row++) {
            for (int col = c + 1; col < c + roomWidth - 1; col++) {
                totalEmptySpace++;
            }
        }
        Random rand = new Random();
        for (int row = r + 1; row < r + roomHeight - 1; row++) {
            for (int col = c + 1; col < c + roomWidth - 1; col++) {
                if (grid[row][col] == EMPTY_SYMBOL && rand.nextInt(totalEmptySpace) < 3) { // generate maximum 4 golds for each room
                    grid[row][col] = GOLD_SYMBOL;
                }
            }
        }
    }

    private void createMonster(int r, int c, int roomHeight, int roomWidth) {
        int newRow = r + roomHeight / 2;
        int newCol = c + roomWidth / 2;
        if (newRow == playerY && newCol == playerX) return;
        Monster monster;
        int randomNumber = new Random().nextInt(3);
        if (randomNumber == 0) {
            monster = Monster.createMonster(Monster.Difficulty.Easy);
        } else if (randomNumber == 1) {
            monster = Monster.createMonster(Monster.Difficulty.Medium);
        } else {
            monster = Monster.createMonster(Monster.Difficulty.Hard);
        }
        grid[newRow][newCol] = monster.getName().charAt(0);
        charactersMap.put(new Point(newRow, newCol), monster);
    }

    /**
     * Make monster randomly move in one of 4 directions: top, bottom, left, right
     */
    public void monsterMoving() {
        List<Point> toRemove = new ArrayList<>();
        Map<Point, GameCharacter> toAdd = new HashMap<>();

        for (Map.Entry<Point, GameCharacter> entry : charactersMap.entrySet()) {
            Point curPoint = entry.getKey();
            GameCharacter character = entry.getValue();


            if (character instanceof Monster ) {
                Monster monster = (Monster) character;
                int randomNumber = new Random().nextInt(4);
                int newRow = curPoint.x;
                int newCol = curPoint.y;

                if (randomNumber == 0) {
                    newRow++;
                } else if (randomNumber == 1) {
                    newRow--;
                } else if (randomNumber == 2) {
                    newCol++;
                } else {
                    newCol--;
                }
                if (isValidMove(newCol, newRow) && grid[newRow][newCol] == ' ') { // only allows the monster to move within the room
                    grid[curPoint.x][curPoint.y] = EMPTY_SYMBOL;
                    grid[newRow][newCol] = monster.getName().charAt(0);
                    toRemove.add(curPoint);
                    toAdd.put(new Point(newRow, newCol), monster);
                }

            }
        }

        // Now apply the changes
        for (Point point : toRemove) {
            charactersMap.remove(point);
        }
        charactersMap.putAll(toAdd);
    }

    private void randomlyCreateNPC(int row, int col) {
        Random random = new Random();
        int randomNumber = random.nextInt(2);
        if (randomNumber == 0) {
            grid[row][col] = NPC_SYMBOL;
            charactersMap.put(new Point(row, col), new NPC("NPC", 0, 0, "Hi, I am a merchant", true));
        }
    }

    /**
     * display the board {@link Board#grid} to the terminal with the help
     * of the ANSI escape code to move the cursor to the new line to be compatible with {@link TerminalGame}
     */
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
        System.out.print(player.getStatInfo()); // print out player stat
        for (int i = 0; i < grid[0].length / 2; i++) {
            System.out.print(" ");
        }
    }

    /**
     * move the player around with the given offset
     * @param deltaX x-axis offset
     * @param deltaY y-axis offset
     */
    public void movePlayer(int deltaX, int deltaY) {
        int newX = playerX + deltaX;
        int newY = playerY + deltaY;
        if (isValidMove(newX, newY) && isValidDestination(newX, newY)) {
            grid[playerY][playerX] = lastMove;
            playerX = newX;
            playerY = newY;
            lastMove = grid[playerY][playerX];
            grid[playerY][playerX] = PLAYER_SYMBOL;
        }
    }

    private boolean isValidDestination(int newX, int newY) {
        char cur = grid[newY][newX];
        return cur == EMPTY_SYMBOL || cur == DOOR_SYMBOL;
    }

    /**
     * checking if we are moving out of bound
     * @param x x-coordinate
     * @param y y-coordinate
     * @return boolean to indicate if the move is valid
     */
    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < grid[0].length && y >= 0 && y < grid.length;
    }

    /**
     * Random rooms generation with the maximum possible room and interval for
     * the width and height of the room. No two rooms will intersect will each other using
     * the utility function {@link Room#intersects(Room)}
     * @param maxRooms the maximum number of room that can be generated (normally not reached)
     * @param maxWidth maximum possible width of a room
     * @param maxHeight maximum possible height of a room
     * @param minWidth minimum width of a room
     * @param minHeight minimum height of a room
     * @return a list of room that later will be drawn on the board {@link Board#grid}
     */
    List<Room> generateRandomRooms(int maxRooms, int maxWidth, int maxHeight, int minWidth, int minHeight) {
        List<Room> rooms = new ArrayList<>();
        List<DoorDirection> doorDirections = new ArrayList<>();
        doorDirections.add(DoorDirection.TOP);
        doorDirections.add(DoorDirection.LEFT);
        doorDirections.add(DoorDirection.BOTTOM);
        doorDirections.add(DoorDirection.RIGHT);
        // create room in the middle where player spawns
        rooms.add(new Room(playerX - maxWidth / 2, playerY - maxHeight / 2, maxWidth , maxHeight,
                doorDirections));
        int gridWidth = grid[0].length;
        int gridHeight = grid.length;
        Random rand = new Random();
        for (int i = 0; i < maxRooms - 1; i++) {
            int width = rand.nextInt(maxWidth - minWidth + 1) + minWidth;
            int height = rand.nextInt(maxHeight - minHeight + 1) + minHeight;
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

    /**
     * Assigns suitable {@link DoorDirection} to a room by identifying
     * which segment of the board the room is located in
     * @param x the x value of the starting point of the room
     * @param y the y value of the starting point of the room
     * @return the list of {@link DoorDirection} that a room need
     */
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

    /**
     * Searches for a nearby GameCharacter around the player's current position on the grid.
     * This method scans the 3x3 area centered around the player.
     *
     * @return GameCharacter that is located next to the player. Returns null if no character is found.
     * @author Michael Galland
     */
    public GameCharacter getNearbyCharacter() {
        // Check tiles around (playerX, playerY) for characters
        for (int i = playerY - 1; i <= playerY + 1; i++) {
            for (int j = playerX - 1; j <= playerX + 1; j++) {
                if (i >= 0 && i < grid.length && j >= 0 && j < grid[i].length) { // Check boundaries
                    if (grid[i][j] == NPC_SYMBOL) { // Found an NPC
                        return charactersMap.get(new Point(i, j)); // Return the actual Character object
                    }
                }
            }
        }
        return null; // no nearby character found
    }

    public Entry getAttackableTarget() {
        // Check tiles around (playerX, playerY) for potential targets
        for (int i = playerY - 1; i <= playerY + 1; i++) {
            for (int j = playerX - 1; j <= playerX + 1; j++) {
                // Check boundaries and ensure it's not the player's position
                if (i >= 0 && i < grid.length && j >= 0 && j < grid[i].length && !(i == playerX && j == playerY)) {
                    if (MONSTER_SYMBOL_COLLECTIONS.indexOf(grid[i][j]) != -1) { // Found an NPC which is a potential target
                        Point curPoint = new Point(i, j);
                        return new Entry((Monster) charactersMap.get(curPoint), curPoint); // Return the actual Character object
                    }
                }
            }
        }
        return null; // No attackable target found
    }

    /**
     * Retrieves the player associated with the current game board.
     *
     * @return Player instance representing the main player character.
     * @author Michael Galland
     */
    public Player getPlayer() {
        return this.player;
    }

    public void removeMonster(Point value) {
        int r = value.x;
        int c = value.y;
        grid[r][c] = EMPTY_SYMBOL;
        charactersMap.remove(value);
    }

    public int countMonster() {
        int count = 0;
        for (GameCharacter gameCharacter : charactersMap.values()) {
            if (gameCharacter instanceof Monster) {
                count++;
            }
        }
        return count;
    }

    /**
     * Collecting
     */
    public void collectGold() {
        int[][] neighbors = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        for (int[] neighbor : neighbors) {
            int newRow = playerY + neighbor[0];
            int newCol = playerX + neighbor[1];
            if(isValidMove(newCol, newRow) && grid[newRow][newCol] == GOLD_SYMBOL) {
                player.earnGold(GOLD_VALUE);
                grid[newRow][newCol] = EMPTY_SYMBOL;
            }
        }
    }

    public void buyItem(Item chosenItem) {
        if(player.spendGold(chosenItem.getCost())) {
            player.setAttackStrength(getPlayer().getAttackStrength() + chosenItem.getDamage());
        }
    }

    public char[][] getGrid() {
        return grid;
    }

    public void placeCharacter(GameCharacter character, int i, int i1) {
        if (character instanceof NPC) {
            grid[i1][i] = NPC_SYMBOL;
        } else {
            Monster monster = (Monster) character;
            grid[i1][i] = monster.getName().charAt(0);
        }
        charactersMap.put(new Point(i1,i), character);
    }

    public void placePlayer(int i, int i1) {
        playerY = i1;
        playerX = i;
    }

    public static class Entry {
        private Monster monster;
        private Point point;

        public Entry(Monster monster, Point point) {
            this.monster = monster;
            this.point = point;
        }

        public Monster getMonster() {
            return monster;
        }

        public void setMonster(Monster monster) {
            this.monster = monster;
        }

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }
    }
}
