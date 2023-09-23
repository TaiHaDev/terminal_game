public class Item {
    public String name;
    public String description;
    public int heal;
    public int damage;
    public int amount;
    public int cost;

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

    @Override
    public String toString() {
        return name + "\r\n" + description + "\r\n" + "you have " + amount + "\r\n" + "sell " + cost;
    }
}
