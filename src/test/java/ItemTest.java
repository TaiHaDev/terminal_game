import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {

    private Item item;

    @BeforeEach
    public void setUp() {
        item = new Item("Health Potion", "Restores health", 10, 0, 3, 5);
    }

    @Test
    public void testGetName() {
        assertEquals("Health Potion", item.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Restores health", item.getDescription());
    }

    @Test
    public void testGetAmount() {
        assertEquals(3, item.getAmount());
    }

    @Test
    public void testGetCost() {
        assertEquals(5, item.getCost());
    }

    @Test
    public void testGetHeal() {
        assertEquals(10, item.getHeal());
    }

    @Test
    public void testSetHeal() {
        item.setHeal(15);
        assertEquals(15, item.getHeal());
    }

    @Test
    public void testGetDamage() {
        assertEquals(0, item.getDamage());
    }

    @Test
    public void testSetDamage() {
        item.setDamage(5);
        assertEquals(5, item.getDamage());
    }

    @Test
    public void testSetAmount() {
        item.setAmount(5);
        assertEquals(5, item.getAmount());
    }

    @Test
    public void testSetCost() {
        item.setCost(10);
        assertEquals(10, item.getCost());
    }
}