package bloodshooter;

/** Bullet.java
 *
 * @author Mykhailo Moroz
 * 
 * @version 0.1 (Created: 27-May-2019; Updated: 27-May-2019)
 */
public class Bullet extends GameObject {
    private final double bulletDamage;
    private static double damageMp = 1;
    private double spreadRand;
    private double spreadVal;
    private final Weapon weapon;
    
    Bullet(String imgName, Weapon w) {
        super(imgName);
        this.weapon = w;
        this.xPosition = this.weapon.getXPosition() + this.weapon.getImg().getWidth(null)/2;
        this.yPosition = this.weapon.getYPosition() - this.img.getHeight(null) + this.weapon.getBulletYOffset();
        do {
        	this.spreadRand = Math.random();
        	if(this.spreadVal <= this.weapon.getSpread() * 2) {
        		this.spreadVal = this.spreadRand;
        	}
        	if(this.spreadVal > this.weapon.getSpread()) {
        		this.spreadVal = -this.spreadVal;
        	}
        } while (this.spreadRand >= this.weapon.getSpread() * 2);
        this.xSpeed = this.weapon.getBulletXSpeed() + this.spreadVal;//to increase the range of the spread
        this.ySpeed = -this.weapon.getBulletYSpeed();
        this.bulletDamage = this.weapon.getBulletDamage();
    }
    
    /** public static void setDamageMp(double dmgMp)
     * Sets the damage multiplier to the one passed in the arguments.
     * 
     * @param dmgMp
     *      The new damage multiplier.
     */
    public static void setDamageMp(double dmgMp) {
        Bullet.damageMp = dmgMp;
    }
    
    /** public double getDamageMp()
     *
     * @return
     *      The damage multiplier of this Bullet.
     */
    public double getDamageMp() {
        return Bullet.damageMp;
    }
    
    /** public double getBulletDamage()
     *
     * @return
     *      The damage of this Bullet.
     */
    public double getBulletDamage() {
        return this.bulletDamage * Bullet.damageMp;
    }
    
    /** public Weapon getWeapon() 
     *
     * @return
     *      The Weapon that fired this bullet.
     */
    public Weapon getWeapon() {
        return this.weapon;
    }
    
    @Override
    protected void outOfBounds() {
        this.destroy();
        this.setXPosition(-100);
    }
}
