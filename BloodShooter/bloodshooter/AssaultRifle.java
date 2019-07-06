package bloodshooter;

/** AssaultRifle.java
 *
 * @author Mykhailo Moroz
 * 
 * @version 0.1 (Created: 14-Jun-2019; Updated: 14-Jun-2019)
 */
public class AssaultRifle extends Weapon {
    static String imageName = "Assault Rifle";
    static String bulletImageName = "Assault Rifle";
    static int price = 300;
    static int xOffset = 5;
    static int yOffset = -35;
    static double bulletYSpeed = 12;
    static double damage = 25;
    static double fireRate = 0.375;
    static double spread = 0.5;
    
    AssaultRifle() {
        super(imageName, bulletImageName, price, xOffset, yOffset, bulletYSpeed, damage, fireRate, spread);
    }
    
    AssaultRifle(String imgName, String bulletImgName,int price, int xOffset, int yOffset, int bulletYOffset, 
    		double bulletYSpeed, double damage, double fireRate, double spread) {
        super(imgName, bulletImgName, price, xOffset, yOffset, bulletYOffset, bulletYSpeed, damage, fireRate, spread);
    }
}
