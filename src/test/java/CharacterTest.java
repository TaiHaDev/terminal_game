import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CharacterTest {
    private Board board;

    @Before
    public void setup() {
        board = new Board(100, 100); // Assuming Board has a constructor with width and height
        Player player = new Player("john", 100, 10, 20);
        board.placePlayer(5, 5); // Assuming you have a method to place the player at (5, 5)
    }

    @Test
    public void testGetNearbyCharacterNoNPC() {
        assertNull(board.getNearbyCharacter()); // No NPCs are near the player
    }

    @Test
    public void testGetNearbyCharacterWithNPC() {
        NPC npc = new NPC("Bob", 100, 5, "Hi", true);
        board.placeCharacter(npc, 6, 5); // Assuming you have a method to place a character

        GameCharacter nearbyCharacter = board.getNearbyCharacter();
        assertNotNull(nearbyCharacter);
        assertTrue(nearbyCharacter instanceof NPC);
    }

    @Test
    public void testGetAttackableTargetNoNPC() {
        assertNull(board.getAttackableTarget()); // No NPCs are near the player
    }

    @Test
    public void testGetAttackableTargetWithNPC() {
        Monster npc = Monster.createMonster(Monster.Difficulty.Easy);
        board.placeCharacter(npc, 5, 6); // Assuming you have a method to place a character

        Board.Entry attackableTarget = board.getAttackableTarget();
        assertNotNull(attackableTarget);
    }

    @Test
    public void testGetPlayer() {
        Player retrievedPlayer = board.getPlayer();
        assertNotNull(retrievedPlayer);
    }
}
