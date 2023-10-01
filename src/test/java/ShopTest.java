import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {

    private Shop shop;

    @BeforeEach
    public void setUp() {
        shop = Shop.getInstance();
    }

    @Test
    public void testGetInstance() {
        Shop instance1 = Shop.getInstance();
        Shop instance2 = Shop.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void testPresent() {
        String shopPresentation = shop.present();
        assertNotNull(shopPresentation);
        assertTrue(shopPresentation.contains("sword"));
        assertTrue(shopPresentation.contains("spear"));
        assertTrue(shopPresentation.contains("gun"));
    }


    @Test
    public void testGetBoughtItem() {
        assertNull(shop.getBoughtItem(3));  // Invalid index
        assertNotNull(shop.getBoughtItem(0)); // Valid index
    }
}