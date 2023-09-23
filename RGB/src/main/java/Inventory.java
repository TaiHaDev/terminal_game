import java.util.ArrayList;

public class Inventory {

    public ArrayList<Item> inventory = new ArrayList<>();

    public void reset(){
        Item sword = new Item("sword", "weapon to kill monsters", 0,10,0, 0,5);
        Item heal_potion = new Item("healing", "healing 10 hp of the player", 10,0,0, 0,5);
        Item armor = new Item("armor", "protect player from attack with 10 hp", 0,0,10, 0,5);
        Item gold = new Item("gold", "use to buy things in the shop", 0,0,0, 0,0);
        inventory.add(sword);
        inventory.add(heal_potion);
        inventory.add(armor);
        inventory.add(gold);
    }
    // add Item
    public void addItem(Item add){
        String item_name = add.getName();
        for(Item item : inventory){
            if(item.name.equals(item_name)){
                item.amount += add.getAmount();
            }
        }
    }

    //add gold
    public void addGold(int num) {
        for (Item item : inventory) {
            if (item.name.equals("gold")) {
                item.amount += num;
            }
        }
    }

    //sell your things
    public void sellItem(Item sell){
        String item_name = sell.getName();
        for(Item item : inventory){
            if(item.name.equals(item_name)){
                int num_to_sell = sell.getAmount();
                item.amount -= num_to_sell;
                addGold(num_to_sell*(item.getCost()));
            }
        }
    }

    // method to check if the player is holding a specified key
    public boolean youHave(Item item){
        for(int i = 0; i < inventory.size(); i++){
            Item have = inventory.get(i);
            if(have.getName().equals(item.name)){
                if(have.amount > 0){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < inventory.size(); i++){
            Item item = inventory.get(i);
            s += item.getName();
            s += "   ";
            s += item.getAmount();
            s += "\r\n";
        }
        return "You have " + "\r\n" + s;
    }

    public static void main(String[] args) {
        Inventory inventory1 = new Inventory();
        inventory1.reset();
        System.out.println(inventory1);
    }
}
