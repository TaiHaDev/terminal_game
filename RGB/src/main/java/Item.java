public class Item {
    public static String name;
    public static String description;
    public static int heal;
    public static int damage;
    public static int amount;
    public static int cost;

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

    public static void present(Item item){
        System.out.println(name + "\r\n" + description + "\r\n" + "you have: " + amount + "\r\n" + "price to sell: " + cost);
    }

    public static void main(String[] args) {
        Item sword = new Item("sword", "weapon to kill monsters", 0,10, 0,5);
        present(sword);
    }
}
