public class Monster extends GameCharacter {

    /**
     * @author Yansheng Li
     */
    private int coin;
    public Monster(String name, int health, int attackStrength, int coin) {
        super(name, health, attackStrength);
        this.coin = coin;
    }
    public int getCoin(){return this.coin;}

    public static Monster createMonster(Difficulty difficulty) {
        if (difficulty == Difficulty.Easy) {
            return new Monster("Bat", 20, 10, 5);
        } else if (difficulty == Difficulty.Medium) {
            return new Monster("Snake", 30, 15, 20);
        } else {
            return new Monster("Zombie", 50, 25, 50);
        }
    }
    public static enum Difficulty {
        Hard, Medium, Easy;
    }
}

