import java.util.ArrayList;
import java.util.Scanner;

public class Shop {
    public static ArrayList<Item> shop = new ArrayList<>();
    public String refresh(){
        Item sword = new Item("sword", "weapon to kill monsters", 0,10, 10,5);
        Item heal_potion = new Item("potion", "healing 10 hp of the player", 10,0, 10,5);
        shop.add(sword);
        shop.add(heal_potion);
        return "inventory reset successfully";
    }
    public static void great(){
        System.out.println("Welcome to the shop, we can provide you :" +"\r\n" + present());
    }

    public static String present(){
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

    public static void shopOpen(){
        Shop shop = new Shop();
        shop.refresh();
        shop.great();
        System.out.println("press the code of the item that you want");
    }

    public static void main(String[] args) {
        shopOpen();
    }
}
