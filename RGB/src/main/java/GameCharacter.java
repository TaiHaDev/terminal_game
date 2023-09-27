/**
 * Represents an abstract game character within the game.
 * This class provides the foundational attributes and behaviors for game characters,
 * including managing health and executing attacks.
 * Specific types of characters should extend from this class.
 *
 * @author Michael Galland
 */
public abstract class GameCharacter {

    private String name;
    private int health;
    private int attackStrength;

    /**
     * Constructs a new GameCharacter with a specified name, health, and attack strength.
     *
     * @param name The name of the game character.
     * @param health The initial health value of the game character.
     * @param attackStrength The attack strength of the game character.
     */
    public GameCharacter(String name, int health, int attackStrength) {
        this.name = name;
        this.health = health;
        this.attackStrength = attackStrength;
    }

    /**
     * Initiates an attack on a target game character. The target's health is
     * decreased based on this character's attack strength.
     *
     * @param target The game character being attacked.
     */
    public void attack(GameCharacter target) {
        target.decreaseHealth(this.attackStrength);
    }

    /**
     * Decreases the health of this game character by a specified damage amount.
     * If health goes below zero, it's set to zero.
     *
     * @param damage The damage amount to decrease the health by.
     */
    public void decreaseHealth(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    /**
     * Retrieves the name of the game character.
     *
     * @return The name of the game character.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name of the game character.
     *
     * @param name The new name for the game character.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the current health value of the game character.
     *
     * @return The current health of the game character.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Updates the health value of the game character.
     *
     * @param health The new health value for the game character.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Retrieves the attack strength of the game character.
     *
     * @return The attack strength of the game character.
     */
    public int getAttackStrength() {
        return attackStrength;
    }

    /**
     * Updates the attack strength of the game character.
     *
     * @param attackStrength The new attack strength for the game character.
     */
    public void setAttackStrength(int attackStrength) {
        this.attackStrength = attackStrength;
    }
}
