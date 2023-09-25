import java.io.IOException;

public class NPC extends GameCharacter {
    private String dialogue;
    private boolean isMerchant;

    public NPC(String name, int health, int attackStrength, String dialogue, boolean isMerchant) {
        super(name, health, attackStrength);
        this.dialogue = dialogue;
        this.isMerchant = isMerchant;
    }

    public void converse(Player player) {
        TerminalGame.printOut(this.dialogue);
        if (isMerchant) {
            // the merchant sells a potion for 10 gold
            TerminalGame.printOut("Would you like to buy a health potion for 10 gold? (yes/no)");
            // Read player's input from the console
            try {
                char response = (char) System.in.read();
                if (response == 'y') {
                    if (player.getGold() >= 10) {
                        player.setGold(player.getGold() - 10);
                        // Let's say a potion restores 50 health points
                        player.setHealth(player.getHealth() + 50);
                        TerminalGame.printOut("You've bought a health potion! 50 health has been restored.");
                    } else {
                        TerminalGame.printOut("Sorry, you don't have enough gold.");
                    }
                } else {
                    TerminalGame.printOut("Maybe next time.");
                }
                System.in.read(); // press any button to skip the conversation;
            } catch (IOException e) {
                e.printStackTrace();
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
