package bloodshooter;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;

/** GameObject.java
 *
 * @author Mykhailo Moroz
 * 
 * @version 0.1 (Created: 27-May-2019; Updated: 27-May-2019)
 */
public class GameObject {
    protected double xPosition;
    protected double yPosition;
    protected double xSpeed;
    protected double ySpeed;
    protected boolean isActive;//the objects are drawn only if they are active
    protected boolean outOfBounds;
    protected boolean isColliding;
    protected Image img;
    protected String imgName;
    
    GameObject(String imgName, double xPos, double yPos, double xSpeed, double ySpeed) {
        this.imgName = imgName;
        this.img = Toolkit.getDefaultToolkit().getImage("Images/" + this.imgName + ".png");
        this.xPosition = xPos;
        this.yPosition = yPos;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.isActive = false;
    }
    
    GameObject(String imgName, double xPos, double yPos) {
        this.imgName = imgName;
        this.img = Toolkit.getDefaultToolkit().getImage("Images/" + this.imgName + ".png");
        this.xPosition = xPos;
        this.yPosition = yPos;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.isActive = false;
    }
    
    GameObject(String imgName) {
        this.imgName = imgName;
        this.img = Toolkit.getDefaultToolkit().getImage("Images/" + this.imgName + ".png");
        this.xPosition = 0;
        this.yPosition = 0;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.isActive = false;
    }
    
    /** public void start()
     * Activates the GameObject.
     */
    public void start() {
        this.isActive = true;
    }
    
    /** public void destroy()
     * Deactivates the object and places it outside of the map.
     */
    public void destroy() {
        this.isActive = false;
        //moves the object outside of the map
        this.xPosition = -100;
        this.yPosition = -100;
        this.xSpeed = 0;
        this.ySpeed = 0;
    }
    
    /** public boolean getActive()
     * 
     * Determines if a GameObject is active.
     * 
     * @return
     *      A boolean variable that is true if the object is active (and false if it is not).
     */
    public boolean getActive() {
        return this.isActive;
    }
    
    /** public double getXPosition()
     * 
     * @return
     *      The x location of the current object.
     */
    public double getXPosition() {
        return this.xPosition;
    }
    
    /** public double getYPosition()
     * 
     * @return
     *      The y location of the current object.
     */
    public double getYPosition() {
        return this.yPosition;
    }
    
    /** public void setXPosition(double xPos)
     * 
     * Sets the x location of the current object to the one passed as the argument.
     * 
     * @param xPos
     *      The new x location.
     */
    public void setXPosition(double xPos) {
        this.xPosition = xPos;
    }
    
    /** public void setYPosition(double yPos)
     * 
     * Sets the y location of the current object to the one passed as the argument.
     * 
     * @param yPos
     *      The new y location.
     */
    public void setYPosition(double yPos) {
        this.yPosition = yPos;
    }
    
    /** public double getXSpeed()
     * 
     * @return
     *      The horizontal velocity of the current object.
     */
    public double getXSpeed() {
        return this.xSpeed;
    }
    
    /** public double getYSpeed()
     * 
     * @return
     *      The vertical velocity of the current object.
     */
    public double getYSpeed() {
        return this.ySpeed;
    }
    
    /** public void setXSpeed(double xSpeed)
     * 
     * Sets the horizontal velocity of the current object to the one passed as the argument.
     * 
     * @param xSpeed
     *      The new horizontal velocity.
     */
    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }
    
    /** public void setYSpeed(double ySpeed)
     * 
     * Sets the vertical velocity of the current object to the one passed as the argument.
     * 
     * @param ySpeed
     *      The new vertical velocity.
     */
    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }
    
    /** public Image getImg()
     * 
     * @return
     *      The image of the current object.
     */
    public Image getImg() {
        return this.img;
    }
    
    /** public String getImgName
     * 
     * @return
     *      The name of the current object's image (the one passed in the constructor).
     */
    public String getImgName() {
        return this.imgName;
    }
    
    /** public void update(Graphics g, ImageObserver io)
     * 
     * Draws and moves the current object.
     * Also check if the object is outside of the map, and if it is calls the outOfBounds() method.
     * 
     * @param g
     *      The graphics object that the current object will be updated in.
     * 
     * @param io
     *      The ImageObserver object that the current object will be updated in.
     */
    public void update(Graphics g, ImageObserver io) {
    	
        //checks if the object is out of bounds
        this.outOfBounds = this.xPosition > BloodShooter.WIDTH + this.img.getWidth(null) || this.xPosition < 0 - this.img.getWidth(null) || 
            this.yPosition < 0 - this.img.getHeight(null) || this.yPosition > BloodShooter.HEIGHT + this.img.getHeight(null);
        
        if(this.outOfBounds) {
            //this method is executed when the project is out of bounds
            outOfBounds();
        }
        
        if(this.isActive) {
            //the object is drawn only if it is active
            this.draw(g, io);
            this.move();
        }
    }
    
    /** protected void draw(Graphics g, ImageObserver io)
     * 
     * Draws the current object.
     * 
     * @param g
     *      The graphics object that the current object will be updated in.
     * @param io
     *      The ImageObserver object that the current object will be updated in.
     */
    protected void draw(Graphics g, ImageObserver io) {
        g.drawImage(this.img, (int) this.xPosition - this.img.getWidth(null)/2, (int) this.yPosition - this.img.getWidth(null)/2, io);
    }
    
    /** protected void move()
     * moves the current object.
     */
    protected void move() {
        this.xPosition += this.xSpeed;
        this.yPosition += this.ySpeed;
    }
    
    /** protected void outOfBounds()
     * This metho is executed if the current object is outside of the map. 
     * Each subclass will specify what to do when they are out of bounds.
     */
    protected void outOfBounds() {
        
    }
}
