package bloodshooter;

/** LMG.java
 *
 * @author Mykhailo Moroz
 * 
 * @version 0.1 (Created: 14-Jun-2019; Updated: 14-Jun-2019)
 */
public class LMG extends Weapon {
    static String imageName = "LMG";
    static String bulletImageName = "Pistol";
    static int price = 1276;
    static int xOffset = 3;
    static int yOffset = -32;
    static double bulletYSpeed = 20;
    static double damage = 5;
    static double fireRate = 1;
    static double spread = 1;
    
    LMG() {
        super(imageName, bulletImageName,  price, xOffset, yOffset, bulletYSpeed, damage, fireRate, spread);
    }
    
    LMG(String imgName, String bulletImgName, int price, int xOffset, int yOffset, int bulletYOffset, 
    		double bulletYSpeed, double damage, double fireRate, double spread) {
        super(imgName, bulletImgName, price, xOffset, yOffset, bulletYOffset, bulletYSpeed, damage, fireRate, spread);
    }
}
