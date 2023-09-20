public class GameStat {
    private int hp;
    private int gold;
    private int strength;
    private int armor;

    public GameStat(int hp, int gold, int strength, int armor) {
        this.hp = hp;
        this.gold = gold;
        this.strength = strength;
        this.armor = armor;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    @Override
    public String toString() {
        return "Gold=   " + gold + "    " +
                "Hp=    " + hp + "    " +
                "Strength=  " + strength + "    " +
                "Armor=    " + armor + "    ";
    }
}
