public class Player extends GameCharacter {
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


    /**
     * @author Yansheng Li
     * @param monster
     */
    public void fight(Monster monster) {
        if (monster.getAttackStrength() >= getHealth()) {
            setHealth(0); // if the monster have more strength than player's HP, lose immediately
            return;
        }
        monster.decreaseHealth(this.getAttackStrength());
        this.decreaseHealth(monster.getAttackStrength());
        if (monster.getHealth() == 0) {
            earnGold(monster.getCoin());
        }
    }
    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getStatInfo() {
        return "Gold=   " + gold + "    " +
                "Hp=    " + getHealth() + "    " +
                "Strength=  " + getAttackStrength() + "    ";
    }

}
