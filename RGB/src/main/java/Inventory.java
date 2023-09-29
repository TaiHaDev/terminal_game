import java.util.ArrayList;

public class Inventory {

    public static ArrayList<Item> inventory = new ArrayList<>();

    /**
     *
     * @return reseted inventory system
     */
    public String reset(){
        Item sword = new Item("sword", "weapon to kill monsters", 0,10, 0,5);
        Item heal_potion = new Item("healing", "healing 10 hp of the player", 10,0, 0,5);
        Item gold = new Item("gold", "use to buy things in the shop", 0,0, 0,0);
        inventory.add(sword);
        inventory.add(heal_potion);
        inventory.add(gold);
        return "inventory reset successfully";
    }

    /**
     *
     * @param add the Item that you want to add to the inventory
     * @return the changed inventory
     */
    private String addItem(Item add){
        String item_name = add.getName();
        for(Item item : inventory){
            if(item.getName().equals(item_name)){
                item.setAmount(item.getAmount() + add.getAmount());
                return "You have got " + item.getAmount() + item_name;
            }
        }
        return "item does not exist";
    }

    //add gold
    private void addGold(int num) {
        for (Item item : inventory) {
            if (item.getName().equals("gold")) {
                item.setAmount(item.getAmount() + num);
            }
        }
    }

    //sell your things
    public void sellItem(Item sell){
        String item_name = sell.getName();
        for(Item item : inventory){
            if(item.getName().equals(item_name)){
                int num_to_sell = sell.getAmount();
                item.setAmount( item.getAmount() - num_to_sell);
                addGold(num_to_sell*(item.getCost()));
            }
        }
    }

    // method to check if the player is holding a specified key
    public boolean youHave(Item item){
        for(int i = 0; i < inventory.size(); i++){
            Item have = inventory.get(i);
            if(have.getName().equals(item.getName())){
                if(have.getAmount() > 0){
                    return true;
                }
            }
        }
        return false;
    }

    public static void presentInventory(){
        String s = "";
        for(int i = 0; i < inventory.size(); i++){
            Item item = inventory.get(i);
            s += item.getName();
            s += "   ";
            s += item.getAmount();
            s += "\r\n";
        }
        System.out.println("You have " + "\r\n" + s);
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
        return "You have: " + "\r\n" + s;
    }


}
