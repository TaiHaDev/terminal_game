import java.io.ByteArrayOutputStream;
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

    /**
     * reset the item in the shop, now you can buy them again
     */
    public void refresh(){
        Item sword = new Item("sword", "weapon to kill monsters", 0,20, 0,50);
        Item spear = new Item("spear", "rudimentary weapon to fight against monsters", 0, 10, 0, 25);
        Item gun = new Item("gun", "ultimate deadly weapon to kill monsters", 0,100, 0,500);
        shop.add(spear);
        shop.add(sword);
        shop.add(gun);
    }

    /**
     * welcome the customer
     */
    public void greet() {
        TerminalGame.printOut("Welcome to the shop, we can provide you :" +"\r\n" + present());
    }

    /**
     *
     * @return the string of the shop im the terminal
     */
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

    /**
     * open the shop by printing it in the terminal
     */
    public void shopOpen(){
        greet();
        TerminalGame.printOut("press the code of the item that you want or press q to exit");
    }

    /**
     *
     * @param index the index of the item you want to buy
     * @return if code not v alid, return null, else return the item
     */
    public Item getBoughtItem(int index) {
        if (index >= shop.size()) {
            return null;
        }
        return shop.get(index);
    }
}
