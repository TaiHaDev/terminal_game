    public class Item {
    private String name;
    private String description;
    private int heal;
    private int damage;
    private int amount;
    private int cost;

    public Item(String name, String description, int heal, int damage, int amount, int cost){
        this.name = name;
        this.description = description;
        this.heal = heal;
        this.damage = damage;
        this.amount = amount;
        this.cost = cost;
    }

    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public int getAmount(){
        return amount;
    }
    public int getCost(){
        return cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHeal() {
        return heal;
    }

    public void setHeal(int heal) {
        this.heal = heal;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void present(Item item){
        System.out.println(name + "\r\n" + description + "\r\n" + "you have: " + amount + "\r\n" + "price to sell: " + cost);
    }
}
