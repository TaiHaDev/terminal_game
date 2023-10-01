

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {

    private Board board = new Board(100, 100);

    @BeforeEach
    public void setUp() {
        board = new Board(100, 100); // Assume this is a reasonable board size for testing.
    }

    @Test
    public void testGenerateRandomRooms() {
        int maxRooms = 10;
        int maxWidth = 20;
        int maxHeight = 20;
        int minWidth = 5;
        int minHeight = 5;

        List<Room> rooms = board.generateRandomRooms(maxRooms, maxWidth, maxHeight, minWidth, minHeight);
        assertFalse(rooms.isEmpty());

        // Check if the rooms generated are not greater than maxRooms.
        assertTrue(rooms.size() <= maxRooms);

        // Check if any two rooms intersect.
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = i + 1; j < rooms.size(); j++) {
                assertFalse(rooms.get(i).intersects(rooms.get(j)));
            }
        }

        // Check if the rooms dimensions are within the specified range.
        for (Room room : rooms) {
            if (room.getDoorDirection().size() == 4) continue;
            assertTrue(room.getWidth() >= minWidth);
            assertTrue(room.getWidth() <= maxWidth);
            assertTrue(room.getHeight() >= minHeight);
            assertTrue(room.getHeight() <= maxHeight);
        }
    }

    @Test
    public void testApplyRoom() {
        List<DoorDirection> doorDirections = new ArrayList<>();
        doorDirections.add(DoorDirection.TOP);
        doorDirections.add(DoorDirection.LEFT);
        doorDirections.add(DoorDirection.BOTTOM);
        doorDirections.add(DoorDirection.RIGHT);
        Room mockRoom = new Room(10, 10, 10, 10, doorDirections);
        List<Room> input = new ArrayList<>();
        input.add(mockRoom);
        board.applyRoom(input);

        // Assert that the room boundaries exist.
        for (int x = mockRoom.getX() + 1; x <= mockRoom.getX() - 1 + mockRoom.getWidth(); x++) {
            char cur1 = board.getGrid()[mockRoom.getY()][x];
            assertTrue(cur1 == Board.VERTICAL_BARRIER || cur1 == Board.DOOR_SYMBOL); // Top
            char cur2 = board.getGrid()[mockRoom.getY() + mockRoom.getHeight()][x];
            assertTrue(cur2 == Board.VERTICAL_BARRIER || cur2 == Board.DOOR_SYMBOL); // Bottom
        }
        for (int y = mockRoom.getY(); y <= mockRoom.getY() - 1 + mockRoom.getHeight(); y++) {
            char cur1 = board.getGrid()[y][mockRoom.getX()];

            assertTrue(cur1 == Board.HORIZONTAL_BARRIER || cur1 == Board.DOOR_SYMBOL); // Left
            char cur2 = board.getGrid()[y][mockRoom.getX() + mockRoom.getWidth()];
            assertTrue(cur2 == Board.HORIZONTAL_BARRIER || cur2 == Board.DOOR_SYMBOL); // Right
        }

        // Assert that doors exist.
        assertTrue(board.getGrid()[mockRoom.getY()][mockRoom.getX() + mockRoom.getWidth() / 2] == Board.DOOR_SYMBOL); // Top door
        assertTrue(board.getGrid()[mockRoom.getY() + mockRoom.getHeight() / 2][mockRoom.getX()] == Board.DOOR_SYMBOL); // Left door
    }

}