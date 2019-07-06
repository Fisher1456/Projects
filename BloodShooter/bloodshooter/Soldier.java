package bloodshooter;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.HashMap;

/** Soldier.java
 *
 * @author Mykhailo Moroz
 * 
 * @version 0.1 (Created: 27-May-2019; Updated: 27-May-2019)
 */
public class Soldier extends GameObject {
    protected int counter;//used to help with fire rate
    private boolean isFiring;
    protected Weapon weapon;
    //soldiers need a more specific out of bounds boolean
    protected HashMap<String, Boolean> outOfBoundsSpecific = new HashMap<>();
    protected int hp;//health points
    protected double fireRateMp = 1;//not implemented yet
    private String bulletImgName;
    
    Soldier(String imgName, Weapon w, double xPos, double yPos, double xSpeed, double ySpeed, int hp) {
        super(imgName, xPos, yPos, xSpeed, ySpeed);
        this.weapon = w;
        this.hp = hp;
    }
    
    @Override
    public void destroy() {
        this.isActive = false;
        this.xPosition = -100;
        this.yPosition = -100;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.weapon.destroy();
    }
    
    /** public void startFiring()
     * Forces the current Soldier to start firing.
     */
    public void startFiring() {
        this.isFiring = true;
    }
    
    /** public void stopFiring()
     * Forces the current Soldier to stop firing.
     */
    public void stopFiring() {
        this.isFiring = false;
    }
    
    /** public void setWeapon(Weapon w)
     * 
     * Set this Soldier's weapon to the one passed in the arguments.
     * 
     * @param w
     *      The new Soldier's weapon
     */
    public void setWeapon(Weapon w) {
        this.weapon = w;
    }
    
    /** public Weapon getWeapon()
     *
     * @return
     *      The Weapon that this Soldier is holding.
     */
    public Weapon getWeapon() {
        return this.weapon;
    }
    
    /** public void setHp(int hp)
     * 
     * Sets the amount of this Soldier's health points to the one passed in the arguments
     * 
     * @param hp
     *      The new amount of health points.
     */
    public void setHp(int hp) {
        this.hp = hp;
    }
    
    /** public int getHp()
     *
     * @return
     *      This Soldier's amount of health points
     */
    public int getHp() {
        return this.hp;
    }
    
    /** public void setFireRateMp(double frMp)
     * 
     * Sets a new fire rate multiplier for this Soldier.
     * 
     * @param frMp
     *      The new fire rate multiplier.
     */
    public void setFireRateMp(double frMp) {
        this.fireRateMp = frMp;
    }
    
    /** public double getFireRateMp()
     *
     * @return
     *      The current Soldier's fire rate multiplier.
     */
    public double getFireRateMp() {
        return this.fireRateMp;
    }
    
    /** public void hit(Bullet b)
     * 
     * This method should be called when the current Soldier is hit by a Bullet b.
     * This method reduces this Soldier's health points by the Bullet's damage.
     * 
     * @param b
     *      The bullet that hit this Soldier.
     */
    public void hit(Bullet b) {
        this.hp -= b.getBulletDamage();
    }
    
    @Override
    public void update(Graphics g, ImageObserver io) {
        this.outOfBounds = this.xPosition > BloodShooter.WIDTH - this.img.getWidth(null)/2 || this.xPosition < 0 + this.img.getWidth(null)/2 || 
            this.yPosition < 0 + this.img.getHeight(null)/2 || this.yPosition > BloodShooter.HEIGHT - this.img.getHeight(null)/2;
        
        //same as the GameObject, only specific to each side
        outOfBoundsSpecific.put("right", this.xPosition > BloodShooter.WIDTH - this.img.getWidth(null)/2);
        outOfBoundsSpecific.put("left", this.xPosition < 0 + this.img.getWidth(null)/2);
        outOfBoundsSpecific.put("top", this.yPosition < 0 + this.img.getHeight(null)/2);
        outOfBoundsSpecific.put("bottom", this.yPosition > BloodShooter.HEIGHT - this.img.getHeight(null)/2);
        
        if(this.outOfBounds) {
            outOfBounds();
        }
        
        if(this.isActive) {
            counter ++;
            this.draw(g, io);
            this.drawWeapon(g, io);
            this.move();
            this.fire();
        }
        
        if(this.hp <= 0) {
            this.destroy();
            this.getWeapon().destroy();
        }
    }
    
    /** protected void drawWeapon(Graphics g, ImageObserver io)
     * 
     * Draws this Soldier's Weapon based on the Solider's position.
     * 
     * @param g
     *      The graphics object that the weapon will be drawn in.
     * 
     * @param io
     *      The ImageObserver object that the weapon will be drawn in.
     */
    private void drawWeapon(Graphics g, ImageObserver io) {
        //draws the weapon with that weapon's offset
        this.weapon.setXPosition(this.xPosition + this.weapon.getXOffset());
        this.weapon.setYPosition(this.yPosition + this.weapon.getYOffset());
        
        g.drawImage(this.weapon.getImg(), (int) this.weapon.getXPosition(), 
                    (int) this.weapon.getYPosition(), io);
    }
    
    /** protected void fire()
     * Fires the Bullet based on the fire rate and if the Soldier is not out of bounds.
     */
    protected void fire() {
        if(this.isFiring && this.counter > 4/this.getWeapon().getFireRate() + 3 && !this.outOfBounds) {
            this.startFiring();
            //create a new bullet, add it to the array, enable it
            //FIX
            //this doesn't work with the new image system
            //create a new var bulletName, set it up with the same angle as the user
            this.bulletImgName = this.weapon.getBulletImgName() + " Bullet";
            
            Bullet b = new Bullet("Bullets/" + bulletImgName, this.getWeapon());
            BloodShooter.bullets.add(b);
            b.start();
            this.stopFiring();
            counter = 0;
        }
    }
}
