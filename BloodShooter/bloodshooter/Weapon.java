package bloodshooter;

/** Weapon.java
 *
 * @author Mykhailo Moroz
 * 
 * @version 0.1 (Created: 27-May-2019; Updated: 27-May-2019)
 */
public class Weapon extends GameObject {
    private final double bulletXSpeed;
    private final double bulletYSpeed;
    private final int xOffset;//the weapon is drawn with an offset
    private final int yOffset;//to make it more realistic
    private final int bulletYOffset;//tp make sure that the enemies don't damage themselves
    private final double bulletDamage;
    private final double fireRate;
    private final double spread;
    private final int price;
    private Soldier user;
    private final String bulletImgName;
    
    Weapon(String imgName, String bulletImgName, int price, int xOffset, int yOffset, int bulletYOffset, 
    		double bulletXSpeed, double bulletYSpeed, double damage, double fireRate, double spread) {
        super("Weapons/" + imgName);
        this.bulletImgName = bulletImgName;
        this.price = price;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.bulletYOffset = bulletYOffset;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.bulletXSpeed = bulletXSpeed;
        this.bulletYSpeed = bulletYSpeed;
        this.bulletDamage = damage;
        this.fireRate = fireRate;
        this.spread = spread;
    }
    
    Weapon(String imgName, String bulletImgName, int price, int xOffset, int yOffset, int bulletYOffset, 
    		double bulletYSpeed, double damage, double fireRate, double spread) {
        super("Weapons/" + imgName);
        this.bulletImgName = bulletImgName;
        this.price = price;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.bulletYOffset = bulletYOffset;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.bulletXSpeed = 0;
        this.bulletYSpeed = bulletYSpeed;
        this.bulletDamage = damage;
        this.fireRate = fireRate;
        this.spread = spread;
    }
    
    Weapon(String imgName, String bulletImgName, int price,  int xOffset, int yOffset, 
    		double bulletYSpeed, double damage, double fireRate, double spread) {
        super("Weapons/" + imgName);
        this.bulletImgName = bulletImgName;
        this.price = price;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.bulletYOffset = 0;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.bulletXSpeed = 0;
        this.bulletYSpeed = bulletYSpeed;
        this.bulletDamage = damage;
        this.fireRate = fireRate;
        this.spread = spread;
    }
    
    /** public void setUser(Soldier s)
     * Sets the user of this Weapon to the one passed in the arguments.
     * 
     * @param s
     *      The new user of this Weapon.
     */
    public void setUser(Soldier s) {
        this.user = s;
    }
    
    /** public Soldier getUser()
     *
     * @return
     *      The user of this Weapon.
     */
    public Soldier getUser() {
        return this.user;
    }
    
    /** public String getBulletImgName()
     *
     * @return
     *      The name of the bullet that this Weapon fires.
     */
    public String getBulletImgName() {
        return this.bulletImgName;
    }
    
    /** public int getPrice()
     *
     * @return
     *      The price of this Weapon.
     */
    public int getPrice() {
        return this.price;
    }
    
    /** public double getBulletXSpeed()
     *
     * @return
     *      The horizontal velocity of the bullet fired from this Weapon.
     */
    public double getBulletXSpeed() {
        return this.bulletXSpeed;
    }
    
    /** public double getBulletYSpeed()
     *
     * @return
     *      The vertical velocity of the bullet fired from this Weapon.
     */
    public double getBulletYSpeed() {
        return this.bulletYSpeed;
    }
    
    /** public double getBulletYOffset()
     *
     * @return
     *      The vertical offset of the bullet fired from of this Weapon.
     */
    public double getBulletYOffset() {
        return this.bulletYOffset;
    }
    
    /** public double getBulletDamage()
     *
     * @return
     *      The damage of the bullet fired from of this Weapon.
     */
    public double getBulletDamage() {
        return this.bulletDamage;
    }
    
    /** public int getXOffset()
     *
     * @return
     *      The horizontal offset of this Weapon.
     */
    public int getXOffset() {
        return this.xOffset;
    }
    
    /** public int getYOffset()
     *
     * @return
     *      The vertical offset of this Weapon.
     */
    public int getYOffset() {
        return this.yOffset;
    }
    
    /** public double getFireRate()
     *
     * @return
     *      The fire rate of this Weapon.
     */
    public double getFireRate() {
        return this.fireRate;
    }
    
    /** public double getSpread()
     *
     * @return
     *      The spread of this Weapon.
     */
    public double getSpread() {
        return this.spread;
    }
}
