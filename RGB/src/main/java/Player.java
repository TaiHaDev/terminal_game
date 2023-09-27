/**
 * Represents the player character in the game.
 * The player can earn and spend gold, and has unique methods to interact with game world such as fighting monsters.
 * This class extends from the base {@link GameCharacter} class.
 *
 * @author Michael Galland
 */
public class Player extends GameCharacter {
    private int gold;

    /**
     * Constructs a new Player with specified attributes and initial gold.
     *
     * @param name Name of the player.
     * @param health Initial health value of the player.
     * @param attackStrength Attack strength of the player.
     * @param startingGold Initial gold amount for the player.
     */
    public Player(String name, int health, int attackStrength, int startingGold) {
        super(name, health, attackStrength);
        this.gold = startingGold;
    }

    /**
     * Adds gold to the player's gold count.
     *
     * @param amount The amount of gold to be added.
     */
    public void earnGold(int amount) {
        this.gold += amount;
    }

    /**
     * Deducts gold from the player's gold count.
     *
     * @param amount The amount of gold to be deducted.
     */
    public void spendGold(int amount) {
        if (this.gold >= amount) {
            this.gold -= amount;
        } else {
            System.out.println("Not enough gold!");
        }
    }

    /**
     * Makes the player engage in combat with a specified monster.
     * Health points and gold are adjusted based on the outcome of the fight.
     *
     * @author Yansheng Li
     * @param monster The monster that the player is fighting against.
     */
    public void fight(Monster monster) {
        if (monster.getAttackStrength() >= getHealth()) {
            setHealth(0);
            return;
        }
        monster.decreaseHealth(this.getAttackStrength());
        decreaseHealth(monster.getAttackStrength());
        if (monster.getHealth() == 0) {
            earnGold(monster.getCoin());
        } else {
            this.decreaseHealth(monster.getAttackStrength());
        }
    }

    /**
     * Retrieves the amount of gold the player has.
     *
     * @return The gold amount of the player.
     */
    public int getGold() {
        return gold;
    }

    /**
     * Sets or updates the gold amount for the player.
     *
     * @param gold The new gold amount to be set for the player.
     */
    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * Generates a string representation of the player's statistics including gold, health, and attack strength.
     *
     * @author Phuoc Ha
     * @return A string displaying the player's current statistics.
     */
    public String getStatInfo() {
        return "Gold=   " + gold + "    " +
                "Hp=    " + getHealth() + "    " +
                "Strength=  " + getAttackStrength() + "    ";
    }
}
