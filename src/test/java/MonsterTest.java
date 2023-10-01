import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MonsterTest {
    @Parameterized.Parameters
    public static Collection<Object[]> difficultyData(){
        return Arrays.asList(new Object[][]{{"Bat",20,10,5,Monster.Difficulty.Easy},
                {"Snake",30,15,20,Monster.Difficulty.Medium},
                {"Zombie",50,25,50,Monster.Difficulty.Hard}});
    }
    @Parameterized.Parameter(0)
    public String name;
    @Parameterized.Parameter(1)
    public int health;
    @Parameterized.Parameter(2)
    public int attackStrength;
    @Parameterized.Parameter(3)
    public int coin;
    @Parameterized.Parameter(4)
    public Monster.Difficulty difficulty;
    @Test
    public void testName(){
        Monster monster = Monster.createMonster(difficulty);
        assertEquals(monster.getName(),name);
    }
    @Test
    public void testHealth(){
        Monster monster = Monster.createMonster(difficulty);
        assertEquals(monster.getHealth(),health);
        assertEquals(monster.getAttackStrength(),attackStrength);
        assertEquals(monster.getCoin(),coin);
    }
    @Test
    public void testAttackStrength(){
        Monster monster = Monster.createMonster(difficulty);
        assertEquals(monster.getAttackStrength(),attackStrength);
    }
    @Test
    public void testCoin(){
        Monster monster = Monster.createMonster(difficulty);
        assertEquals(monster.getCoin(),coin);
    }
}
