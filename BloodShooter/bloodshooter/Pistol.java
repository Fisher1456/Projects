package bloodshooter;

/** Pistol.java
 *
 * @author Mykhailo Moroz
 * 
 * @version 0.1 (Created: 14-Jun-2019; Updated: 14-Jun-2019)
 */
public class Pistol extends Weapon {
    static String imageName = "Pistol";
    static String bulletImageName = "Pistol";
    static int price = 0;
    static int xOffset = 2;
    static int yOffset = -32;
    static double bulletYSpeed = 10;
    static double damage = 20;
    static double fireRate = 0.2;
    static double spread = 0.2;
    
    Pistol() {
        super(imageName, bulletImageName, price, xOffset, yOffset, bulletYSpeed, damage, fireRate, spread);
    }
    
    Pistol(String imgName, String bulletImgName, int price, int xOffset, int yOffset, int bulletYOffset, 
    		double bulletYSpeed, double damage, double fireRate, double spread) {
        super(imgName, bulletImgName, price, xOffset, yOffset, bulletYOffset, bulletYSpeed, damage, fireRate, spread);
    }
}
