public class Item {
    private String name;
    private String description;
    private int heal;
    private int damage;
    private int amount;
    private int cost;

    public Item(String name, String description, int heal, int damage, int amount, int cost){
        this.name = name;
        this.description = description;
        this.heal = heal;
        this.damage = damage;
        this.amount = amount;
        this.cost = cost;
    }

    /**
     *
     * @return return the name of item
     */
    public String getName(){
        return name;
    }

    /**
     *
     * @return return the description of the item
     */
    public String getDescription(){
        return description;
    }

    /**
     *
     * @return return the amount of item
     */
    public int getAmount(){
        return amount;
    }

    /**
     *
     * @return get the cost of this item
     */
    public int getCost(){
        return cost;
    }

    /**
     *
     * @param name the nam that you want to set the current item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param description the description of the item that yuu want to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return return the player's heal num
     */
    public int getHeal() {
        return heal;
    }

    /**
     * ]
     * @param heal set the heal of the player
     */
    public void setHeal(int heal) {
        this.heal = heal;
    }

    /**
     *
     * @return the damage that the player will produce
     */
    public int getDamage() {
        return damage;
    }

    /**
     *
     * @return set the damage that the player will produce
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     *
     * @param amount the amount of the item that you want to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     *
     * @param cost the cost of the item that you want to set
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     *
     * @param item preset the item's information in string
     */
    public void present(Item item){
        System.out.println(name + "\r\n" + description + "\r\n" + "you have: " + amount + "\r\n" + "price to sell: " + cost);
    }
}
