import java.util.ArrayList;

public class Shop {
    private ArrayList<Item> shop = new ArrayList<>();
    private static Shop instance;

    private Shop() {}

    /**
     * Singleton design pattern because we only need one instance of {@link Shop} within the
     * entire game
     * @author Phuoc Ha
     */
    public static Shop getInstance() {
        if (instance == null) {
            instance = new Shop();
            instance.refresh();
        }
        return instance;
    }

    public void refresh(){
        Item sword = new Item("sword", "weapon to kill monsters", 0,10, 10,5);
        Item heal_potion = new Item("potion", "healing 10 hp of the player", 10,0, 10,5);
        shop.add(sword);
        shop.add(heal_potion);
    }
    public void greet() {
        System.out.println("Welcome to the shop, we can provide you :" +"\r\n" + present());
    }

    public String present(){
        String s = "Code     Name     Amount     Cost";
        s += "\r\n";
        for(int i = 0; i < shop.size(); i++){
            Item item = shop.get(i);
            s += i+1;
            s += "        ";
            s += item.getName();
            s += "        ";
            s += item.getCost();
            s += "        ";
            s += item.getAmount();
            s += "\r\n";
        }
        return s;
    }

    public void shopOpen(){
        refresh();
        greet();
        System.out.println("press the code of the item that you want");
    }

}
