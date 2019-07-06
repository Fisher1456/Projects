package bloodshooter;

/** Player.java
 *
 * @author Mykhailo Moroz
 * 
 * @version 0.1 (Created: 27-May-2019; Updated: 27-May-2019)
 */
public class Player extends Soldier {
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public boolean space;
    public final char W = 'w';
    public final char S = 's';
    public final char A = 'a';
    public final char D = 'd';
    private Integer money = 0;
    
    Player(String imgName, Weapon w, double xPos, double yPos, double xSpeed, double ySpeed, int hp) {
        super(imgName, w, xPos, yPos, xSpeed, ySpeed, hp);
    }
    
    @Override
    public void destroy() {
        this.isActive = false;
        this.xPosition = 100;
        this.yPosition = 100;
        this.xSpeed = 0;
        this.ySpeed = 0;
    }
    
    @Override
    protected void outOfBounds() {
        //makes sure that the player cannot go uot of bounds
        if(outOfBoundsSpecific.get("right")) {
            this.xPosition -= this.xSpeed * 2;
        }
        if(outOfBoundsSpecific.get("left")) {
            this.xPosition += this.xSpeed * 2;
        }
        if(outOfBoundsSpecific.get("top")) {
            this.yPosition += this.ySpeed * 2;
        }
        if(outOfBoundsSpecific.get("bottom")) {
            this.yPosition -= this.ySpeed * 2;
        }
    }
    
    @Override
    protected void move() {
        if (up) {
            this.yPosition -= this.ySpeed;
        }
        if (down) {
            this.yPosition += this.ySpeed;
        }
        if (left) {
            this.xPosition -= this.xSpeed;
        }
        if (right) {
            this.xPosition += this.xSpeed;
        }
        if (space) {
            this.startFiring();
        }
        if (!space) {
            this.stopFiring();
        }
    }
    
    /** public void addMoney(int m)
     * 
     * Increases this Player's money.
     * 
     * @param m
     *      The amount of money that this Player will get.
     */
    public void addMoney(int m) {
        this.money += m;
    }
    
    /** public void removeMoney(int m)
     * 
     * Decreases this Player's money.
     * 
     * @param m
     *      The amount of money that this Player will lose.
     */
    public void removeMoney(int m) {
        this.money -= m;
    }
    
    /** public Integer getMoney()
     *
     * @return
     *      The amount of this Player's money.
     */
    public Integer getMoney() {
        return this.money;
    }
}
