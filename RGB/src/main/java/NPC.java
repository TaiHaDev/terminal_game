import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NPC extends Character {
    private String dialogue;
    private boolean isMerchant;

    public NPC(String name, int health, int attackStrength, String dialogue, boolean isMerchant) {
        super(name, health, attackStrength);
        this.dialogue = dialogue;
        this.isMerchant = isMerchant;
    }

    public void converse() {
        System.out.println(this.dialogue);

        if (isMerchant) {
            // the merchant sells a potion for 10 gold
            System.out.println("Would you like to buy a health potion for 10 gold? (yes/no)");

            // Read player's input from the console
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String response = "";
            try {
                response = reader.readLine().toLowerCase().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if ("yes".equals(response)) {
                if (player.getGold() >= 10) {
                    player.setGold(player.getGold() - 10);
                    //potion restores 50 health points
                    player.setHealth(player.getHealth() + 50);
                    System.out.println("You've bought a health potion! 50 health has been restored.");
                } else {
                    System.out.println("Sorry, you don't have enough gold.");
                }
            } else {
                System.out.println("Maybe next time.");
            }
        }
    }


    public String getDialogue() {
        return dialogue;
    }

    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    public boolean isMerchant() {
        return isMerchant;
    }

    public void setMerchant(boolean isMerchant) {
        this.isMerchant = isMerchant;
    }
}
