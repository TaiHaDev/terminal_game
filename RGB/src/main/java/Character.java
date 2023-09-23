public abstract class Character {
    private String name;
    private int health;
    private int attackStrength;

    public Character(String name, int health, int attackStrength) {
        this.name = name;
        this.health = health;
        this.attackStrength = attackStrength;
    }

    public static void attack(Character target) {
        target.decreaseHealth(this.attackStrength);
    }

    public void decreaseHealth(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackStrength() {
        return attackStrength;
    }

    public void setAttackStrength(int attackStrength) {
        this.attackStrength = attackStrength;
    }
}
