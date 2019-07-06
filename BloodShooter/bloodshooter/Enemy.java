package bloodshooter;

/** Enemy.java
 *
 * @author Mykhailo Moroz
 * 
 * @version 0.1 (Created: 27-May-2019; Updated: 27-May-2019)
 */
public class Enemy extends Soldier {
    private final String state = "Rookie";//state works like rank
    //the higher the rank, the more money player gets for killing them
    private boolean isRookie = true;//rookie by default
    private boolean isSoldier = false;
    private boolean isSniper = false;
    
    Enemy(String imgName, Weapon w, String state, double xPos, double yPos, double xSpeed, double ySpeed, int hp) {
        super(imgName, w, xPos, yPos, xSpeed, ySpeed, hp);
    }
    
    @Override
    protected void outOfBounds() {
        if(outOfBoundsSpecific.get("left") || outOfBoundsSpecific.get("right")) {
            this.xSpeed = -this.xSpeed;
        }
        if(outOfBoundsSpecific.get("bottom")) {
            this.ySpeed = -this.ySpeed;
        }
    }
    
    /** public void setState(String state)
     * 
     * Sets the state of the current enemy based on the input.
     * 
     * The available options are: 
     *      "Rookie"
     *      "Soldier"
     *      "Sniper"
     * 
     * @param state
     *      The state of the enemy.
     */
    public void setState(String state) {
        switch (state) {
            case "Rookie": {
                this.isRookie = true;
                this.isSoldier = false;
                this.isSniper = false;
            } break;
            case "Soldier": {
                this.isRookie = false;
                this.isSoldier = true;
                this.isSniper = false;
            } break;
            case "Sniper": {
                this.isRookie = false;
                this.isSoldier = false;
                this.isSniper = true;
            } break;
            default: {
                this.isRookie = true;
            } break;
        }
    }
    
    /** public void setState()
     * Sets the state of the current enemy based on the state passed in the constructor.
     */
    public void setState() {
        switch (state) {
            case "Rookie": {
                this.isRookie = true;
                this.isSoldier = false;
                this.isSniper = false;
            } break;
            case "Soldier": {
                this.isRookie = false;
                this.isSoldier = true;
                this.isSniper = false;
            } break;
            case "Sniper": {
                this.isRookie = false;
                this.isSoldier = false;
                this.isSniper = true;
            } break;
            default: {
                this.isRookie = true;
            } break;
        }
    }
    
    /** public String getState()
     *
     * @return
     *      The string representation of the current enemy's state.
     */
    public String getState() {
        if(this.isRookie) {
            return "Rookie";
        }
        else if(this.isSoldier) {
            return  "Soldier";
        }
        else if(this.isSniper) {
            return "Sniper";
        }
        else {
            return "Unknown";
        }
    }
    
    /** public boolean getState(String state)
     * 
     * Check whether the current enemy is of the same state as the input.
     * 
     * The available options are: 
     *      "Rookie"
     *      "Soldier"
     *      "Sniper"
     * 
     * @param state
     *      The state that the current enemy is checked against.
     * 
     * @return
     *      A boolean value that is true if the enemy is of the same state as the one passed in the arguments,
     *      and false if it is not the same.
     */
    public boolean getState(String state) {
        switch (state) {
            case "Rookie":
                return this.isRookie;
            case "Soldier":
                return this.isSoldier;
            case "Sniper":
                return this.isSniper;
            default:
                return false;
        }
    }
}
