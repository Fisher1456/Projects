package bloodshooter;

/** SniperRifle.java
 *
 * @author Mykhailo Moroz
 * 
 * @version 0.1 (Created: 14-Jun-2019; Updated: 14-Jun-2019)
 */
public class SniperRifle extends Weapon {
    static String imageName = "Sniper Rifle";
    static String bulletImageName = "Sniper Rifle";
    static int price = 600;
    static int xOffset = 3;
    static int yOffset = -75;
    static double bulletYSpeed = 22;
    static double damage = 150;
    static double fireRate = 0.06;
    static double spread = 0.1;
    
    SniperRifle() {
        super(imageName, bulletImageName, price, xOffset, yOffset, bulletYSpeed, damage, fireRate, spread);
    }
    
    SniperRifle(String imgName, String bulletImgName, int price, int xOffset, int yOffset, int bulletYOffset, 
    		double bulletYSpeed, double damage, double fireRate, double spread) {
        super(imgName, bulletImgName, price, xOffset, yOffset, bulletYOffset, bulletYSpeed, damage, fireRate, spread);
    }
}
