public class Player extends Character {
    private int gold;

    public Player(String name, int health, int attackStrength, int startingGold) {
        super(name, health, attackStrength);
        this.gold = startingGold;
    }

    public void earnGold(int amount) {
        this.gold += amount;
    }

    public void spendGold(int amount) {
        if (this.gold >= amount) {
            this.gold -= amount;
        } else {
            System.out.println("Not enough gold!");
        }
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
