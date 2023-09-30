import java.io.IOException;

/**
 * Represents a Non-Player Character (NPC) within the game.
 * NPC can have dialogues with the player and, if it's a merchant,
 * it can sell items, such as health potions, to the player.
 * It extends the functionality of the GameCharacter class.
 *
 * @author Michael Galland
 */
public class NPC extends GameCharacter {
    private String dialogue;
    private boolean isMerchant;

    /**
     * Constructs a new NPC with specified attributes and behaviors.
     *
     * @param name The name of the NPC.
     * @param health The initial health value of the NPC.
     * @param attackStrength The attack strength of the NPC.
     * @param dialogue The dialogue that the NPC will use when interacting with the player.
     * @param isMerchant Indicates if the NPC is a merchant or not.
     */
    public NPC(String name, int health, int attackStrength, String dialogue, boolean isMerchant) {
        super(name, health, attackStrength);
        this.dialogue = dialogue;
        this.isMerchant = isMerchant;
    }

    /**
     * Handles the conversation between the NPC and the player.
     * If the NPC is a merchant, it offers a potion to the player.
     *
     * @param player The player who is interacting with the NPC.
     */
    public void converse(Player player) {
        TerminalGame.printOut(this.dialogue);
        if (isMerchant) {
            TerminalGame.printOut("Would you like to buy a health potion for 10 gold? (yes/no)");
            try {
                char response = (char) System.in.read();
                if (response == 'y') {
                    if (player.getGold() >= 10) {
                        player.setGold(player.getGold() - 10);
                        player.setHealth(player.getHealth() + 50);
                        TerminalGame.printOut("You've bought a health potion! 50 health has been restored.");
                    } else {
                        TerminalGame.printOut("Sorry, you don't have enough gold.");
                    }
                } else {
                    TerminalGame.printOut("Maybe next time.");
                }
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves the dialogue of the NPC.
     *
     * @return The dialogue of the NPC.
     */
    public String getDialogue() {
        return dialogue;
    }

    /**
     * Updates the dialogue of the NPC.
     *
     * @param dialogue The new dialogue for the NPC.
     */
    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    /**
     * Checks if the NPC is a merchant.
     *
     * @return true if the NPC is a merchant; false otherwise.
     */
    public boolean isMerchant() {
        return isMerchant;
    }

    /**
     * Sets or updates the merchant status of the NPC.
     *
     * @param isMerchant true if the NPC should be a merchant; false otherwise.
     */
    public void setMerchant(boolean isMerchant) {
        this.isMerchant = isMerchant;
    }
}
