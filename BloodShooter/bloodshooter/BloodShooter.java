package bloodshooter;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** BloodShooter.java
 *
 * @author Mykhailo Moroz
 * 
 * @version 0.1 (Created: 27-May-2019; Updated: 27-May-2019)
 */
public class BloodShooter extends JFrame implements ActionListener, KeyListener, Runnable {
	
	private static final long serialVersionUID = 1L;
	
	public int counter = 0;//after a wave is completed, the user can play for about 2 seconds.
                           //counter is used for counting the frames that the user spends in this "post-wave" mode
    public boolean enemyCollidedWith = false;//used to check if the bullet has collided with an enemy.
                                             //helps in removing the concurrent modifier exception
    public Canvas c;//canvas where the images are drawn onto 
    public boolean isRunning = false;//boolean that controls whether a thread is running.
                                     //a new thread should be created every time a new wave/level is loaded
    
    public static Image floor;//the background of each level
    
    public static Color col = new Color(200, 0, 0);//background colour (for menus and shops)
    
    public static int difficulty = 2;//game difficulty
    public static int currentLevel = 1;
    
    //************************ARRAY LISTS******************************
    public static ArrayList<Bullet> bullets = new ArrayList<>();
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    
    //toRemove lists. used to avoid Concurrent Modification Exception
    public static ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
    public static ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
    
    //*******************WEAPONS*******************
    //arguments: imgName, bulletImgName, price, xOffset, yOffset, bulletYoffset, bulletXSpeed, bulletYSpeed, damage, fire rate
    //arguments: imgName, bulletImgName, price, xOffset, yOffset, bulletYoffset, bulletYSpeed, damage, fire rate
    //arguments: imgName, bulletImgName, price, xOffset, yOffset, bulletYSpeed, damage, fire rate
    public static Pistol pistol = new Pistol();
    public static AssaultRifle aRifle = new AssaultRifle();
    public static SniperRifle sRifle = new SniperRifle();
    public static LMG lMG = new LMG();//light machine gun. as of this version, not implemented yet
    
    //Enemy weapons
    public static Pistol enemyPistol = new Pistol("EnemyPistol", "Pistol", 0, -28, -3, 30, -10, 5, 0.07, 0.2);
    public static AssaultRifle enemyARifle = new AssaultRifle("EnemyAssaultRifle", "Assault Rifle", 0,  -28, -8, 50, -12, 8, 0.2, 0.4);
    public static SniperRifle enemySRifle = new SniperRifle("EnemySniperRifle", "Sniper Rifle", 0, -31, -2, 85, -17, 45, 0.04, 0.1);
    
    public static Weapon currentWeapon = pistol;//Player's current weapon.
    
    //*************POS AND SPEED CONSTANTS**************
    //Player constants:
    public static final double DEFAULT_PLAYER_X_POS = 100;
    public static final double DEFAULT_PLAYER_Y_POS = 490;
    public static final double DEFAULT_PLAYER_X_SPEED = 3;
    public static final double DEFAULT_PLAYER_Y_SPEED = 2.5;
    public static final int DEFAULT_PLAYER_HP = 100;
    
    //Enemy constants:
    public static final double DEFAULT_ENEMY_X_POS = 100;
    public static final double DEFAULT_ENEMY_Y_POS = 490;
    public static final double DEFAULT_ENEMY_X_SPEED = 1.5;
    public static final double DEFAULT_ENEMY_Y_SPEED = 0.2;
    //Standing enemy constants
    public static final double DEFAULT_SNIPER_ENEMY_X_SPEED = 0;
    public static final double DEFAULT_SNIPER_ENEMY_Y_SPEED = 0;
    public static final int DEFAULT_ENEMY_HP = 100;
    
    //arguments for the player:
    //image name, Weapon, starting xPosition, yPosition, xSpeed, ySpeed, hp
    public static Player pl = new Player("PlayerImage", pistol, DEFAULT_PLAYER_X_POS, DEFAULT_PLAYER_Y_POS, 
                        DEFAULT_PLAYER_X_SPEED, DEFAULT_PLAYER_Y_SPEED, DEFAULT_PLAYER_HP);
    
    //***********************JFRAME CONST***********************
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int FONTSIZE = 16;
    public static final String CONSOLE_TEXT = "BloodShooter";
    
    //menu dimensions. used by all JFrames except for the main game
    public static final int MENU_WIDTH = 1024;
    public static final int MENU_HEIGHT = 768;
    public static final int MENU_FONTSIZE = 16;
    public static final String MENU_CONSOLE_TEXT = "Menu";
    public static final String DIFFICULTY_MENU_CONSOLE_TEXT = "Difficulty";
    public static final String SHOP_MENU_CONSOLE_TEXT = "Shop";
    public static final String WELCOME = "BloodShooter";
    
    
    //*****************JFRAMES***************
    public static JFrame menu = new JFrame("Menu");
    public static JFrame difficultyFrame = new JFrame("Difficulty");
    public static JFrame shopMenuFrame = new JFrame("Shop");
    public static JFrame deadFrame = new JFrame("You are dead now, if you didn't notice");
    public static JFrame creditsFrame = new JFrame("Credits");
    public static JFrame instrFrame = new JFrame("Instructions");
    
    
    //***************BUTTONS***************
    //main menu buttons
    public static JButton startButton = new JButton("Start");//starts the game
    public static JButton difficultyButton = new JButton("Difficulty");//opens the difficulty menu
    public static JButton creditsButton = new JButton("Credits");//opens the credits menu
    public static JButton instrButton = new JButton("Instructions");//opens the credits menu
    public static JButton backButton = new JButton("<- Go back");//goes back to the main menu
    public static JButton exitButton = new JButton("Exit");//leaves the game
    
    //difficulty menu buttons
    public static JButton easyDiffButton = new JButton("Easy");//selects easy difficulty
    public static JButton mediumDiffButton = new JButton("Medium");//selects medium difficulty
    public static JButton hardDiffButton = new JButton("Hard");//selects hard difficulty
    public static JButton veryHardDiffButton = new JButton("VERY Hard");//selects very hard difficulty
    
    //shop buttons
    public static JButton[] shopButtons = {
		new JButton("Pistol"),
		new JButton("Assault Rifle"),
		new JButton("Sniper Rifle"),
		new JButton("MiniGun (this should be fun!)"),
		new JButton("Continue")
    };
    
    //shop booleans
    //they make sure that the program doesn't register double clicks
    //more info in the text_log 1.x
    public static boolean[] gunButtonWasClicked = {
		false,
		false,
		false,
		false,
		false
    };
    
    
    //**************LABELS************
    //main menu labels
    //There are 2 "BloodShooter" labels to make sure that the main menu doesn't break
    //because it does if there is only one variable
    public static JLabel BSLabel = new JLabel("BloodShooter");
    public static JLabel welcomeLabel = new JLabel("BloodShooter");
    public static JLabel menuLabel = new JLabel("Menu");
    
    //difficulty menu labels
    public static JLabel difficultyLabel = new JLabel("Choose the difficulty:");
    public static JLabel difficultyExplanationLabel = new JLabel("Higher difficulties increase both enemy damage and user damage, and also movement speed.");
    public static JLabel difficultySpaceLabel = new JLabel("________");//to make some space
    
    //shop labels
    public static JLabel shopLabel = new JLabel("Shop");
    public static JLabel playerMoney = new JLabel("Your money: " + pl.getMoney());//displays player's money
    public static JLabel playerCurrentWeapon = new JLabel("Currnet Weapon: " + pl.getWeapon().getImgName());//displays player's weapon
    public static JLabel sellingExplanationLabel = new JLabel("You can sell your current weapon by clicking on this button");
    public static JPanel shopPanel = new JPanel();
    public static JLabel[] shopLabels = {
		new JLabel("Damage: 20"),
		new JLabel("Fire Rate: 15"),
		new JLabel("Price: $" + pistol.getPrice()),
		new JLabel("Damage: 30"),
		new JLabel("Fire Rate: 25"),
		new JLabel("Price: $" + aRifle.getPrice()),
		new JLabel("Damage: 150"),
		new JLabel("Fire Rate: 5"),
		new JLabel("Price: $" + sRifle.getPrice()),
		new JLabel("Damage: 5"),
		new JLabel("Fire Rate: 69"),
		new JLabel("Price: $" + lMG.getPrice())
    };
    
    //dead image
    public static JLabel deadLabel = new JLabel(new ImageIcon("Images/DeathScreen.png"));//the death screen
    
    //credits
    public static JLabel creditsLabel = new JLabel("Credits");
    public static JLabel creditsCodeLabel = new JLabel("Code and images: Mykhailo Moroz.");
    public static JLabel creditsMusicLabel = new JLabel(/*"Menu and shop music: JoltzDude139."*/ "_____");
    
    //instructions labels
    public static JLabel instrLabel = new JLabel("Instructions");
    public static JPanel instrPanel = new JPanel();
    public static JLabel[] instrLabels = {
        new JLabel("_____________"),
        new JLabel("Move with WASD or arrow keys."),
        new JLabel("Shoot with Spacebar."),
        new JLabel("In order to win, kill all the enemies."),
        new JLabel("You loose if you die."),
        new JLabel("_____________________"),
        new JLabel("After each level, you will enter the shop,"),
        new JLabel("where you can buy guns."),
        new JLabel("If you buy a pistol, your current weapon is sold."),
        new JLabel("Press coontinue to advancce to the next level."),
        new JLabel("__________")
    };
    
    
    //*************JFRAME BOOLEANS**************
    //these booleans make sure that the game runs sequentially, 
    //and at no point in time there are 2 JFrames running at the same time
    public static boolean isInMenu = true;
    public static boolean isInShop = false;
    public static boolean isInGame = false;
    public static boolean isDead = false;
    
    //*********AUDIO*******
    //as of this version, there is no audio in the game
    //the code exists, but there is no time to make it work properly
    AudioClip earlyMusic;
    AudioClip mediumMusic;
    
    
    BloodShooter() {
        init();
    }

    /** private void init()
     * Called at the beginning of the game. 
     * This method creates and setups the JFrame of the main game,
     * but doesn't start it.
     */
    private void init() {
        this.setSize(WIDTH, HEIGHT);
        this.setName(CONSOLE_TEXT);
        this.setTitle(CONSOLE_TEXT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//the program exits when the user closes the JFrame
        this.setLocationRelativeTo(null);//make the JFrame start at the center of the screen
        this.setResizable(false);//make sure that the JFrame is not resizable
    }
    
    /** public void start()
     * Starts the JFrame. Also starts the Thread that will update the game code.
     */
    public void start() {
        c = new Canvas();//create a new canvas on which the images will be drawn
        c.addKeyListener(this);
        this.add(c);//add the canvas to the JFrame
        this.setVisible(true);
        c.setVisible(true);
        isRunning = true;//run a section of the code in the "run()" methos of this Thread
        new Thread(this).start();//start the Thread
        
        System.out.println("start() was called");//for debugging purposes
    }
    
    /** public void reset()
     * Like the start(), only removes the previous canvas and adds a new one instead.
     */
    public void reset() {
        this.remove(c);//remove the canvas
        c.setEnabled(false);//disable the canvas
        c = new Canvas();//create a new canvas
        c.addKeyListener(this);
        this.addKeyListener(this);
        this.add(c);//add the new canvas to the JFrame
        this.setVisible(true);
        c.setVisible(true);
        isRunning = true;//run a section of the code in the "run()" methos of this Thread
        new Thread(this).start();//start the Thread
        
        System.out.println("reset() was called");//for debugging purposes
    }
    
    /** public void stop()
     * Stops the current Thread.
     */
    public void stop() {
        try {
            Thread.currentThread().join();
        } catch (InterruptedException ex) {
            
        }
        isRunning = false;
    }
    
    /** public void updateGameObgects(Graphics g)
     * Updates the main game objects. This method is used to clear up the tick() method.
     * 
     * @param g
     *      Graphics object where the GameObgects will be updated.
     */
    public void updateGameObjects(Graphics g) {
        pl.update(g, this);//updates player

        for (Enemy e : enemies) {
            e.update(g, this);//updates enemies
            e.startFiring();//forces enemies to fire
            
            //if the enemies are inactive, break the loop and remove the enemy
            if(!e.getActive()) {
                enemiesToRemove.add(e);
                e.setXPosition(-100);
                e.setXSpeed(0);
                e.setYSpeed(0);
                break;
            }
        }
        
        for (Bullet b : bullets) {
            b.update(g, this);//updates bullets
            
            if(isColliding(b, pl) && !b.getWeapon().equals(pl.getWeapon())) {
                //if the bullet hits the player
                pl.hit(b);//reduce player's hp
                b.destroy();//destroy the bullet

                if(pl.getHp() <= 0) {
                    //if player is dead
                    pl.destroy();//destroy the player
                    isDead = true;//play the death screen
                    isInGame = false;
                    isRunning = false;//stop running
                }
                break;
            }
            
            for (Enemy e : enemies) {
                //if the bullet hit an enemy and it was the player's bullet
                //this removes friendly fire from enemies
                if (isColliding(b, e) && b.getWeapon().equals(pl.getWeapon())) {
                    e.hit(b);//remove so me hp
                    b.destroy();//destroy the bullet
                    enemyCollidedWith = true;
                    
                    if(e.getHp() <= 0) {
                        //if the enemy is dead
                        e.destroy();//destroy the enemy
                        enemiesToRemove.add(e);//remove the enemy
                        //add money to the player based on the enemy
                        if(e.getState("Rookie")) {
                            pl.addMoney(75);
                        }
                        if(e.getState("Soldier")) {
                            pl.addMoney(150);
                        }
                        else if(e.getState("Sniper")) {
                            pl.addMoney(250);
                        }
                    }
                    break;
                }
            }

            if(enemyCollidedWith) {
                //break the for loop if a bullet collided with an enemy
                //helps with the concurrentModifierException
                enemyCollidedWith = false;
                break;
            }

            if(!b.getActive()) {
                //if the bullet was destroyed
                bulletsToRemove.add(b);//remove the bullet
                b.setXPosition(-100);
                b.setXSpeed(0);
                b.setYSpeed(0);
                break;
            }
        }
        
        //remove the objects
        bullets.removeAll(bulletsToRemove);
        enemies.removeAll(enemiesToRemove);
    }
    
    /** public void tick()
     * Code inside of this method is executed at a certain fps that is set in the run() method.
     */
    public void tick() {
        if (isInMenu) {
            //if the user is in the menu
            menu(this);//start the menu
        }
        
        if(!isInGame && !isInMenu && !isInShop) {
            //this if satement should execute between each level
            //or when not in menu, game or shop
            startLevel(currentLevel);//start the level
            isInGame = true;
            currentLevel ++;//increases the current level by one
            //this is teh best place to increse the level because this if statement will only get executed once
            System.out.println("Current level selected, isInGame");//for debugging purposes
        }
        
        if(isInGame && !isInMenu && !isInShop) {
            //this if statement is where the "magic" happens
            if(c == null) {
                c = new Canvas();//if there is no canvas, create one
                c.addKeyListener(this);
                this.add(c);
                c.setVisible(true);
                return;
            }
            
            //create a buffer strategy
            BufferStrategy bs = c.getBufferStrategy();
            if(bs == null) {
                c.createBufferStrategy(3);
                return;
            }
            Graphics g = bs.getDrawGraphics();//create graphics
            
            g.drawImage(floor, 0, 0, this);//draw the background
            
            updateGameObjects(g);//update the main game objects
            
            //display health, money and current weapon
            g.setColor(Color.BLACK);
            g.setFont(new Font("Times New Roman", Font.BOLD, 18));
            g.drawString("Health: " + pl.getHp(), 40, HEIGHT - 65);
            g.drawString("Level: " + (currentLevel - 1), 325, HEIGHT - 65);
            g.drawString("Money: " + pl.getMoney(), WIDTH/2 - (-50 + pl.getMoney().toString().length()), HEIGHT - 65);
            g.drawString("Weapon: " + pl.getWeapon().getImgName(), WIDTH - (150 + pl.getWeapon().toString().length()), HEIGHT - 65);
            
            if(enemies.isEmpty()) {
                //if there are no enemies left
                counter ++;//wait for about 2 seconds
                if(counter >= 100) {
                    //remove all bullets, disable this frame, go into the shop
                    bullets.removeAll(bullets);
                    this.setVisible(false);
                    isInShop = true;
                    isInGame = false;
                    isRunning = false;
                    if(earlyMusic != null) {
                        earlyMusic.stop();
                    }
                    if(mediumMusic != null) {
                        mediumMusic.stop();
                    }
                }
            }
            
            bs.show();//repaint
            g.dispose();//remove the graphics
        }
        
        if(isInShop && !isInGame && !isInMenu) {
            //when in shop
            counter = 0;//reset the counter
            shopMenu(this);//start the shop menu
            System.out.println("Entered the shop");//for debugging purposes
        }
    }
    
    /** public void render()
     * This method is run way more times than the tick() method and is mostly used for drawing.
     */
    public void render() {
        
    }
    
    /** public AudioClip createClip(String address)
     * Creates a new AudioClip object.
     * 
     * @param address
     *      The address/URL to the sound file.
     * @return
     *      A new AudioClip object.
     */
    public AudioClip createClip(String address) {
        AudioClip clip = Applet.newAudioClip(getClass().getClassLoader().getResource(address));
        return clip;
    }
    
    /** public void playMusic(AudioClip clip)
     * Creates a new thread that will play an AudioClip.
     * 
     * @param clip
     *      The clip that will be played.
     */
    public void playMusic(AudioClip clip) {
        new Thread() {
            @Override
            public void run() {
                clip.play();
            }
        }.start();
        //System.out.println("Music is playing");
    }
    
    /** public void loopMusic(AudioClip clip)
     * Creates a new thread that will loop an AudioClip.
     * 
     * @param clip
     *      The clip that will be looped.
     */
    public void loopMusic(AudioClip clip) {
        new Thread() {
            @Override
            public void run() {
                clip.play();
            }
        }.start();
        //System.out.println("Music is looping");
    }
    
    /** public void setupLevel()
     * Starts all of the GameObjects, sets the difficulty and so on. Used to simplify each of the level methods
     */
    public void setupLevel() {
        pl.start();//start the player
        //move the player to the default position
        pl.setXPosition(DEFAULT_PLAYER_X_POS);
        pl.setYPosition(DEFAULT_PLAYER_Y_POS);
        pl.setHp(100);//reset the hp
        
        //stop the player, force the plyer to stop firing
        pl.up = false;
        pl.down = false;
        pl.left = false;
        pl.right = false;
        pl.space = false;
        
        for (Enemy e : enemies) {
            e.start();//start the enemies
            e.setState();//set their states
        }
        
        currentWeapon = pl.getWeapon();//set the current weapon
        setDifficulty(difficulty);//set the difficulty
    }
    
    /** public void level1()
     * Creates the first level.
     */
    public void level1() {
        floor = Toolkit.getDefaultToolkit().getImage("Images/LevelFloor" + currentLevel + ".png");//load the background
        //arguments for the enemy:
        //image name, weapon, state, xPosition, yPosition, xSpeed, ySpeed, hp
        enemies.add(new Enemy("EnemyImage", enemyPistol, "Rookie", 200, -100, 
                                DEFAULT_ENEMY_X_SPEED, DEFAULT_ENEMY_Y_SPEED, 100));
        enemies.add(new Enemy("EnemyImage", enemyPistol, "Rookie", 300, 100, 
                                DEFAULT_ENEMY_X_SPEED, DEFAULT_ENEMY_Y_SPEED, 100));
        enemies.add(new Enemy("EnemyImage", enemyPistol, "Rookie", 400, -50, 
                                DEFAULT_ENEMY_X_SPEED/2, DEFAULT_ENEMY_Y_SPEED, 200));
        enemies.add(new Enemy("EnemyImage", enemyPistol, "Rookie", 650, 300, 
                                DEFAULT_ENEMY_X_SPEED, DEFAULT_ENEMY_Y_SPEED, 100));
        
        setupLevel();//start the game objects
        
        //earlyMusic = createClip("Sounds/EarlyLevelsMusic.wav");
        //loopMusic(earlyMusic);
        
        System.out.println("Level " + (currentLevel) + " initialized");//for debugging purposes
    }
    
    /** public void level2()
     * Creates the second level.
     */
    public void level2() {
        floor = Toolkit.getDefaultToolkit().getImage("Images/LevelFloor" + currentLevel + ".png");//load the background
        //arguments for the enemy:
        //image name, starting xPosition, yPosition, xSpeed, ySpeed, hp
        enemies.add(new Enemy("EnemyImage", enemyPistol, "Soldier", 400, 100, 
                                DEFAULT_SNIPER_ENEMY_X_SPEED, DEFAULT_SNIPER_ENEMY_Y_SPEED, 70));
        enemies.add(new Enemy("EnemyImage", enemyPistol, "Rookie", 650, 250, 
                                DEFAULT_SNIPER_ENEMY_X_SPEED, DEFAULT_SNIPER_ENEMY_Y_SPEED, 80));
        enemies.add(new Enemy("EnemyImage", enemyPistol, "Rookie", 700, 300, 
                                DEFAULT_SNIPER_ENEMY_X_SPEED, DEFAULT_SNIPER_ENEMY_Y_SPEED, 80));
        enemies.add(new Enemy("EnemyImage", enemyPistol, "Soldier", 500, 200, 
                                DEFAULT_ENEMY_X_SPEED, DEFAULT_SNIPER_ENEMY_Y_SPEED, 80));
        
        setupLevel();//start the game objects
        
        //earlyMusic = createClip("Sounds/EarlyLevelsMusic.wav");
        //loopMusic(earlyMusic);
        
        System.out.println("Level " + (currentLevel) + " initialized");//for debugging purposes
    }
    
    /** public void level3()
     * Creates the third level.
     */
    public void level3() {
        floor = Toolkit.getDefaultToolkit().getImage("Images/LevelFloor" + currentLevel + ".png");//load the background
        //arguments for the enemy:
        //image name, starting xPosition, yPosition, xSpeed, ySpeed, hp
        enemies.add(new Enemy("EnemyImage", enemyARifle, "Soldier", 100, -50, 
                                DEFAULT_ENEMY_X_SPEED, DEFAULT_ENEMY_Y_SPEED, 60));
        enemies.add(new Enemy("EnemyImage", enemyARifle, "Soldier", 400, 0, 
                                DEFAULT_ENEMY_X_SPEED, DEFAULT_ENEMY_Y_SPEED/3, 60));
        enemies.add(new Enemy("EnemyImage", enemyPistol, "Soldier", 650, 250, 
                                DEFAULT_ENEMY_X_SPEED, DEFAULT_ENEMY_Y_SPEED/2, 80));
        enemies.add(new Enemy("EnemyImage", enemyPistol, "Soldier", 700, 300, 
                                DEFAULT_SNIPER_ENEMY_X_SPEED, DEFAULT_SNIPER_ENEMY_Y_SPEED, 80));
        enemies.add(new Enemy("EnemyImage", enemyPistol, "Soldier", 500, 200, 
                                DEFAULT_ENEMY_X_SPEED, DEFAULT_SNIPER_ENEMY_Y_SPEED, 80));
        
        setupLevel();//start the game objects
        
        //mediumMusic = createClip("Sounds/MediumLevelsMusic.wav");
        //(mediumMusic);
        
        System.out.println("Level " + (currentLevel) + " initialized");//for debugging purposes
    }
    
    /** public void level4()
     * Creates the fourth level.
     */
    public void level4() {
        floor = Toolkit.getDefaultToolkit().getImage("Images/LevelFloor" + currentLevel + ".png");//load the background
        //arguments for the enemy:
        //image name, starting xPosition, yPosition, xSpeed, ySpeed, hp
        enemies.add(new Enemy("EnemyImage", enemyARifle, "Soldier", 100, -50, 
                                DEFAULT_ENEMY_X_SPEED, DEFAULT_ENEMY_Y_SPEED, 70));
        enemies.add(new Enemy("EnemyImage", enemyARifle, "Soldier", 400, 0, 
                                DEFAULT_ENEMY_X_SPEED, DEFAULT_ENEMY_Y_SPEED/3, 70));
        enemies.add(new Enemy("EnemyImage", enemyARifle, "Sniper", 650, 250, 
                                DEFAULT_ENEMY_X_SPEED, DEFAULT_ENEMY_Y_SPEED/2, 85));
        enemies.add(new Enemy("EnemyImage", enemySRifle, "Sniper", 500, 200, 
                                DEFAULT_ENEMY_X_SPEED, DEFAULT_SNIPER_ENEMY_Y_SPEED, 150));
        enemies.add(new Enemy("EnemyImage", enemyARifle, "Sniper", 800, 200, 
                DEFAULT_ENEMY_X_SPEED, DEFAULT_SNIPER_ENEMY_Y_SPEED, 150));
        
        setupLevel();//start the game objects
        
        //mediumMusic = createClip("Sounds/MediumLevelsMusic.wav");
        //loopMusic(mediumMusic);
        
        System.out.println("Level " + (currentLevel) + " initialized");//for debugging purposes
    }

    /** public void level5()
     * Creates the 5th level.
     */
    public void level5() {
        floor = Toolkit.getDefaultToolkit().getImage("Images/LevelFloor" + currentLevel + ".png");//load the background
        //arguments for the enemy:
        //image name, starting xPosition, yPosition, xSpeed, ySpeed, hp
        enemies.add(new Enemy("EnemyImage", enemyARifle, "Soldier", 100, -50, 
                                -DEFAULT_ENEMY_X_SPEED, DEFAULT_ENEMY_Y_SPEED, 85));
        enemies.add(new Enemy("EnemyImage", enemyARifle, "Soldier", 400, 0, 
                                DEFAULT_ENEMY_X_SPEED, DEFAULT_ENEMY_Y_SPEED/3, 85));
        enemies.add(new Enemy("EnemyImage", enemyARifle, "Sniper", 650, 250, 
                                DEFAULT_SNIPER_ENEMY_X_SPEED, DEFAULT_ENEMY_Y_SPEED, 90));
        enemies.add(new Enemy("EnemyImage", enemySRifle, "Sniper", 500, 200, 
                                DEFAULT_ENEMY_X_SPEED, DEFAULT_SNIPER_ENEMY_Y_SPEED, 150));
        enemies.add(new Enemy("EnemyImage", enemyARifle, "Sniper", 800, 100, 
                DEFAULT_ENEMY_X_SPEED, DEFAULT_SNIPER_ENEMY_Y_SPEED, 100));
        
        setupLevel();//start the game objects
        
        //mediumMusic = createClip("Sounds/MediumLevelsMusic.wav");
        //loopMusic(mediumMusic);
        
        System.out.println("Level " + (currentLevel) + " initialized");//for debugging purposes
    }
    
    /** public void level6()
     * Creates the 6th level.
     */
    public void level6() {
        floor = Toolkit.getDefaultToolkit().getImage("Images/LevelFloor" + currentLevel + ".png");//load the background
        //arguments for the enemy:
        //image name, starting xPosition, yPosition, xSpeed, ySpeed, hp
        enemies.add(new Enemy("EnemyImage", enemyARifle, "Soldier", 50, 0, 
                                DEFAULT_ENEMY_X_SPEED/3, DEFAULT_ENEMY_Y_SPEED, 85));
        enemies.add(new Enemy("EnemyImage", enemyARifle, "Soldier", 500, -50, 
                                -DEFAULT_ENEMY_X_SPEED, DEFAULT_ENEMY_Y_SPEED/3, 85));
        enemies.add(new Enemy("EnemyImage", enemyARifle, "Sniper", 300, 100, 
                                DEFAULT_SNIPER_ENEMY_X_SPEED, DEFAULT_ENEMY_Y_SPEED/2, 90));
        enemies.add(new Enemy("EnemyImage", enemySRifle, "Sniper", 200, 150, 
                                DEFAULT_SNIPER_ENEMY_X_SPEED, DEFAULT_SNIPER_ENEMY_Y_SPEED, 150));
        enemies.add(new Enemy("EnemyImage", enemySRifle, "Sniper", 800, 120, 
                				DEFAULT_SNIPER_ENEMY_X_SPEED, DEFAULT_SNIPER_ENEMY_Y_SPEED, 150));
        
        setupLevel();//start the game objects
        
        //mediumMusic = createClip("Sounds/MediumLevelsMusic.wav");
        //loopMusic(mediumMusic);
        
        System.out.println("Level " + (currentLevel) + " initialized");//for debugging purposes
    }

    /** public void startLevel(int level)
     * Starts the current level.
     * 
     * @param level
     *      The current level.
     */
    public void startLevel(int level) {
        switch(level) {
            case 1: {
                level1();
            } break;
            case 2: {
                level2();
            } break;
            case 3: {
                level3();
            } break;
            case 4: {
                level4();
            } break;
            case 5: {
                level5();
            } break;
            case 6: {
                level6();
            } break;
            default : {
                System.err.println("There are no more levels. The last level was " + (level - 1));
            	System.exit(0);
            } break;
        }
    }
    
    /** public static void menu(BloodShooter blf)
     * Creates a JFrame that acts as the main menu of the game.
     * The user can choose to set the difficulty, view credits, start or exit the game.
     * 
     * @param blf
     *      The BloodShooter object that the menu will be displayed in.
     */
    public static void menu(BloodShooter blf) {
        menu.setSize(MENU_WIDTH, MENU_HEIGHT);
        menu.setResizable(false);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setLocationRelativeTo(null);
        menu.setLayout(new BoxLayout(menu.getContentPane(), BoxLayout.Y_AXIS));
        menu.getContentPane().setBackground(col);
        
        menu.setVisible(true);
        
        
        BSLabel.setFont(new Font("Viner Hand ITC", Font.PLAIN, 50));
        menu.add(BSLabel);
        BSLabel.setAlignmentX(CENTER_ALIGNMENT);
        BSLabel.setAlignmentY(CENTER_ALIGNMENT);
        
        menuLabel.setFont(new Font("Informal Roman", Font.PLAIN, 40));
        menu.add(menuLabel);
        menuLabel.setAlignmentX(CENTER_ALIGNMENT);
        menuLabel.setAlignmentY(CENTER_ALIGNMENT);
        
        menu.add(startButton);
        startButton.setAlignmentX(CENTER_ALIGNMENT);
        startButton.setAlignmentY(CENTER_ALIGNMENT);
        
        menu.add(difficultyButton);
        difficultyButton.setAlignmentX(CENTER_ALIGNMENT);
        difficultyButton.setAlignmentY(CENTER_ALIGNMENT);
        
        menu.add(creditsButton);
        creditsButton.setAlignmentX(CENTER_ALIGNMENT);
        creditsButton.setAlignmentY(CENTER_ALIGNMENT);
        
        menu.add(instrButton);
        instrButton.setAlignmentX(CENTER_ALIGNMENT);
        instrButton.setAlignmentY(CENTER_ALIGNMENT);
        
        menu.add(exitButton);
        exitButton.setAlignmentX(CENTER_ALIGNMENT);
        exitButton.setAlignmentY(CENTER_ALIGNMENT);
        
        startButton.setFocusPainted(false);
        difficultyButton.setFocusPainted(false);
        creditsButton.setFocusPainted(false);
        instrButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);
        
        startButton.addActionListener(blf);
        difficultyButton.addActionListener(blf);
        creditsButton.addActionListener(blf);
        instrButton.addActionListener(blf);
        exitButton.addActionListener(blf);
    }
    
    /** public static void deadMenu(BloodShooter blf)
     * Creates a JFrame that acts as the death screen.
     * The user can choose to exit the game, or to wait a few seconds and return to the main menu.
     *
     * @param blf
     *      The BloodShooter object that the death screen will be displayed in.
     */
    public static void deadMenu(BloodShooter blf) {
        deadFrame.setSize(893, 625);//1/2 of the actual image
        deadFrame.setResizable(false);
        deadFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        deadFrame.setLocationRelativeTo(null);
        deadFrame.setLayout(new BoxLayout(deadFrame.getContentPane(), BoxLayout.Y_AXIS));
        deadFrame.getContentPane().setBackground(Color.BLACK);
        deadFrame.setVisible(true);
        
        
        deadFrame.add(deadLabel);
        deadLabel.setAlignmentX(CENTER_ALIGNMENT);
        deadLabel.setAlignmentY(CENTER_ALIGNMENT);
        
        deadFrame.add(exitButton);
        exitButton.setAlignmentX(CENTER_ALIGNMENT);
        exitButton.setAlignmentY(BOTTOM_ALIGNMENT);
        
        exitButton.addActionListener(blf);
    }
    
    /** public static void difficultyMenu(BloodShooter blf)
     * Creates a JFrame that acts as the difficulty menu.
     * The user can choose to increase or decrease the difficulty, or go back to the main menu.
     *
     * @param blf
     *      The BloodShooter object that the difficulty menu will be displayed in.
     */
    public static void difficultyMenu(BloodShooter blf) {
        difficultyFrame.setSize(MENU_WIDTH, MENU_HEIGHT);
        difficultyFrame.setResizable(false);
        difficultyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        difficultyFrame.setLocationRelativeTo(null);
        difficultyFrame.setLayout(new BoxLayout(difficultyFrame.getContentPane(), BoxLayout.Y_AXIS));
        difficultyFrame.getContentPane().setBackground(col);
        
        difficultyFrame.setVisible(true);
        

        welcomeLabel.setFont(new Font("Viner Hand ITC", Font.PLAIN, 50));
        difficultyFrame.add(welcomeLabel);
        welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);
        welcomeLabel.setAlignmentY(CENTER_ALIGNMENT);
        
        difficultyLabel.setFont(new Font("Informal Roman", Font.PLAIN, 40));
        difficultyFrame.add(difficultyLabel);
        difficultyLabel.setAlignmentX(CENTER_ALIGNMENT);
        difficultyLabel.setAlignmentY(CENTER_ALIGNMENT);
        
        difficultyExplanationLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        difficultyFrame.add(difficultyExplanationLabel);
        difficultyExplanationLabel.setAlignmentX(CENTER_ALIGNMENT);
        difficultyExplanationLabel.setAlignmentY(CENTER_ALIGNMENT);
        
        difficultyFrame.add(difficultySpaceLabel);
        difficultySpaceLabel.setAlignmentX(CENTER_ALIGNMENT);
        difficultySpaceLabel.setAlignmentY(CENTER_ALIGNMENT);
        
        difficultyFrame.add(easyDiffButton);
        easyDiffButton.setAlignmentX(CENTER_ALIGNMENT);
        easyDiffButton.setAlignmentY(CENTER_ALIGNMENT);
        
        difficultyFrame.add(mediumDiffButton);
        mediumDiffButton.setAlignmentX(CENTER_ALIGNMENT);
        mediumDiffButton.setAlignmentY(CENTER_ALIGNMENT);
        
        difficultyFrame.add(hardDiffButton);
        hardDiffButton.setAlignmentX(CENTER_ALIGNMENT);
        hardDiffButton.setAlignmentY(CENTER_ALIGNMENT);
        
        difficultyFrame.add(veryHardDiffButton);
        veryHardDiffButton.setAlignmentX(CENTER_ALIGNMENT);
        veryHardDiffButton.setAlignmentY(CENTER_ALIGNMENT);
        
        difficultyFrame.add(backButton);
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setAlignmentY(CENTER_ALIGNMENT);
        
        easyDiffButton.setFocusPainted(false);
        mediumDiffButton.setFocusPainted(false);
        hardDiffButton.setFocusPainted(false);
        veryHardDiffButton.setFocusPainted(false);
        backButton.setFocusPainted(false);
        
        easyDiffButton.addActionListener(blf);
        mediumDiffButton.addActionListener(blf);
        hardDiffButton.addActionListener(blf);
        veryHardDiffButton.addActionListener(blf);
        backButton.addActionListener(blf);
    }
    
    /** public static void creditsMenu(BloodShooter blf)
     * Creates a JFrame that acts as the credits.
     * The user can choose to read the credits or go back to the main menu.
     *
     * @param blf
     *      The BloodShooter object that the credits will be displayed in.
     */
    public static void creditsMenu(BloodShooter blf) {
        creditsFrame.setSize(MENU_WIDTH, MENU_HEIGHT);
        creditsFrame.setResizable(false);
        creditsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        creditsFrame.setLocationRelativeTo(null);
        creditsFrame.setLayout(new BoxLayout(creditsFrame.getContentPane(), BoxLayout.Y_AXIS));
        creditsFrame.getContentPane().setBackground(col);
        
        creditsFrame.setVisible(true);
        

        welcomeLabel.setFont(new Font("Viner Hand ITC", Font.PLAIN, 50));
        creditsFrame.add(welcomeLabel);
        welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);
        welcomeLabel.setAlignmentY(CENTER_ALIGNMENT);
        
        creditsLabel.setFont(new Font("Informal Roman", Font.PLAIN, 40));
        creditsFrame.add(creditsLabel);
        creditsLabel.setAlignmentX(CENTER_ALIGNMENT);
        creditsLabel.setAlignmentY(CENTER_ALIGNMENT);
        
        creditsCodeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        creditsFrame.add(creditsCodeLabel);
        creditsCodeLabel.setAlignmentX(CENTER_ALIGNMENT);
        creditsCodeLabel.setAlignmentY(CENTER_ALIGNMENT);
        
        creditsMusicLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        creditsFrame.add(creditsMusicLabel);
        creditsMusicLabel.setAlignmentX(CENTER_ALIGNMENT);
        creditsMusicLabel.setAlignmentY(CENTER_ALIGNMENT);
        
        creditsFrame.add(backButton);
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setAlignmentY(CENTER_ALIGNMENT);
        
        
        backButton.setFocusPainted(false);
        backButton.addActionListener(blf);
    }
    
    /** public static void instrMenu(BloodShooter blf)
     * Creates a JFrame that acts as the instructions.
     * The user can choose to read the instructions or go back to the main menu.
     *
     * @param blf
     *      The BloodShooter object that the instructions will be displayed in.
     */
    public static void instrMenu(BloodShooter blf) {
        instrFrame.setSize(MENU_WIDTH, MENU_HEIGHT);
        instrFrame.setResizable(false);
        instrFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instrFrame.setLocationRelativeTo(null);
        instrFrame.setLayout(new BoxLayout(instrFrame.getContentPane(), BoxLayout.Y_AXIS));
        instrFrame.getContentPane().setBackground(col);
        instrFrame.setVisible(true);
        
        //the JPanel with the instructions
        for(JLabel jl : instrLabels) {
            jl.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            instrPanel.add(jl);
            jl.setAlignmentX(CENTER_ALIGNMENT);
            jl.setAlignmentY(CENTER_ALIGNMENT);
        }
        instrPanel.setLayout(new BoxLayout(instrPanel, BoxLayout.Y_AXIS));
        instrPanel.setBackground(col);
        
        
        welcomeLabel.setFont(new Font("Viner Hand ITC", Font.PLAIN, 50));
        instrFrame.add(welcomeLabel);
        welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);
        welcomeLabel.setAlignmentY(CENTER_ALIGNMENT);
        
        instrLabel.setFont(new Font("Informal Roman", Font.PLAIN, 40));
        instrFrame.add(instrLabel);
        instrLabel.setAlignmentX(CENTER_ALIGNMENT);
        instrLabel.setAlignmentY(CENTER_ALIGNMENT);
        
        instrFrame.add(instrPanel);
        
        instrFrame.add(backButton);
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setAlignmentY(CENTER_ALIGNMENT);
        
        
        backButton.setFocusPainted(false);
        backButton.addActionListener(blf);
    }
    
    /** public static void shopMenu(BloodShooter blf)
     * Creates a JFrame that acts as the shop.
     * This shop will be displayed after each wave.
     * The user can choose to buy a gun, to sell their gun (by pressing on "Pistol" button, or to continue the game.
     *
     * @param blf
     *      The BloodShooter object that the shop menu will be displayed in.
     */
    public static void shopMenu(BloodShooter blf) {
        shopMenuFrame.setEnabled(true);
        shopMenuFrame.setSize(MENU_WIDTH, MENU_HEIGHT);
        shopMenuFrame.setResizable(false);
        shopMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        shopMenuFrame.setLocationRelativeTo(null);
        shopMenuFrame.setLayout(new BoxLayout(shopMenuFrame.getContentPane(), BoxLayout.Y_AXIS));
        shopMenuFrame.getContentPane().setBackground(col);
        shopMenuFrame.setVisible(true);
        
        
        BSLabel.setFont(new Font("Viner Hand ITC", Font.PLAIN, 50));
        shopMenuFrame.add(BSLabel);
        BSLabel.setAlignmentX(LEFT_ALIGNMENT);
        BSLabel.setAlignmentY(CENTER_ALIGNMENT);
        
        shopLabel.setFont(new Font("Informal Roman", Font.PLAIN, 40));
        shopMenuFrame.add(shopLabel);
        shopLabel.setAlignmentX(LEFT_ALIGNMENT);
        shopLabel.setAlignmentY(CENTER_ALIGNMENT);
        
        playerMoney.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        shopMenuFrame.add(playerMoney);
        playerMoney.setText("Your money: " + pl.getMoney());
        playerMoney.setAlignmentX(LEFT_ALIGNMENT);
        playerMoney.setAlignmentY(CENTER_ALIGNMENT);
        
        playerCurrentWeapon.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        shopMenuFrame.add(playerCurrentWeapon);
        playerCurrentWeapon.setText("Currnet Weapon: " + pl.getWeapon().getImgName());
        playerCurrentWeapon.setAlignmentX(LEFT_ALIGNMENT);
        playerCurrentWeapon.setAlignmentY(CENTER_ALIGNMENT);
        
        sellingExplanationLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        shopMenuFrame.add(sellingExplanationLabel);
        sellingExplanationLabel.setAlignmentX(LEFT_ALIGNMENT);
        sellingExplanationLabel.setAlignmentY(LEFT_ALIGNMENT);
        
    	int counter = 1;
    	int bCounter = 0;
        for(JLabel jl : shopLabels) {
            jl.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            shopPanel.add(jl);
            jl.setAlignmentX(LEFT_ALIGNMENT);
            jl.setAlignmentY(LEFT_ALIGNMENT);
            if(counter % 3 == 0) {
            	shopPanel.add(shopButtons[bCounter]);
            	shopButtons[bCounter].setAlignmentX(LEFT_ALIGNMENT);
            	shopButtons[bCounter].setAlignmentY(LEFT_ALIGNMENT);
                
            	shopButtons[bCounter].setEnabled(true);
            	shopButtons[bCounter].setFocusPainted(false);
            	shopButtons[bCounter].addActionListener(blf);
                gunButtonWasClicked[bCounter] = false;
                bCounter ++;
            }
            counter ++;
        }
        //Continue button:
    	shopPanel.add(shopButtons[bCounter]);
    	shopButtons[bCounter].setAlignmentX(LEFT_ALIGNMENT);
    	shopButtons[bCounter].setAlignmentY(LEFT_ALIGNMENT);
        
    	shopButtons[bCounter].setEnabled(true);
    	shopButtons[bCounter].setFocusPainted(false);
    	shopButtons[bCounter].addActionListener(blf);
        gunButtonWasClicked[bCounter] = false;
        
        shopPanel.setLayout(new BoxLayout(shopPanel, BoxLayout.Y_AXIS));
        shopPanel.setBackground(col);
        shopMenuFrame.add(shopPanel);
    }
    
    /** public void setDifficulty(int difficulty)
     * Sets the difficulty based on the current difficulty.
     * Higher difficulties increase user damage, enemy damage, and movement speed for both the user and the enemies.
     * 
     * @param difficulty
     *      Difficulty that will modify the game.
     */
    public void setDifficulty(int difficulty) {
        switch(difficulty) {
            case 1: {
                //easy difficulty = more hp
                pl.setHp(DEFAULT_PLAYER_HP + 20);
            } break;
            case 2: {
                //medium difficulty = no buffs/debuffs
            } break;
            case 3: {
                //hard difficulty = higher movement speed, damage mp
                pl.setXSpeed(DEFAULT_PLAYER_X_SPEED * 1.25);
                pl.setYSpeed(DEFAULT_PLAYER_Y_SPEED * 1.25);
                for (Enemy e : enemies) {
                    if(e.getState("Soldier")) {
                        e.setXSpeed(DEFAULT_ENEMY_X_SPEED * 1.25);
                        e.setYSpeed(DEFAULT_ENEMY_Y_SPEED * 1.25);
                    }
                }
                Bullet.setDamageMp(1.75);
            } break;
            case 4: {
                //very hard difficulty = even higher movement speed, damage mp
                pl.setXSpeed(DEFAULT_PLAYER_X_SPEED * 1.75);
                pl.setYSpeed(DEFAULT_PLAYER_Y_SPEED * 1.75);
                //pl.setFireRateMp(4);
                for (Enemy e : enemies) {
                    if(e.getState("Soldier")) {
                        e.setXSpeed(DEFAULT_ENEMY_X_SPEED * 1.75);
                        e.setYSpeed(DEFAULT_ENEMY_Y_SPEED * 1.75);
                    }
                }
                Bullet.setDamageMp(2.5);
            } break;
        }
    }
    
    /** public static boolean isColliding(GameObject obj1, GameObject obj2)
     * Returns a boolean value that is true when 2 GameObjects are colliding and false when they are not.
     * 
     * @param obj1
     *      The first GameObject.
     * @param obj2
     *      The second GameObject.
     * @return
     *      A boolean value.
     */
    public static boolean isColliding(GameObject obj1, GameObject obj2) {
        boolean isColliding;
        
        //left side, right side, top side, bottom side
        isColliding = ( ((obj1.xPosition - obj1.img.getWidth(null)/2) - (obj2.xPosition + obj2.img.getWidth(null)/2) <= 0 && 
                         (obj1.xPosition + obj1.img.getWidth(null)/2) - (obj2.xPosition - obj2.img.getWidth(null)/2) >= 0 ) && 
                        ((obj1.yPosition - obj1.img.getHeight(null)/2) - (obj2.yPosition + obj2.img.getHeight(null)/2) <= 0 && 
                         (obj1.yPosition + obj1.img.getHeight(null)/2) - (obj2.yPosition - obj2.img.getHeight(null)/2) >= 0));
        
        return isColliding;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == pl.W) {
            pl.up = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == pl.S) {
            pl.down = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == pl.A) {
            pl.left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == pl.D) {
            pl.right = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            pl.space = true;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == pl.W) {
            pl.up = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == pl.S) {
            pl.down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == pl.A) {
            pl.left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == pl.D) {
            pl.right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            pl.space = false;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //********************MENU*****************
        if(e.getActionCommand().equals(startButton.getText())) {
            menu.setVisible(false);
            isInMenu = false;
            start();
            System.out.println("Start button pressed");
        }
        else if(e.getActionCommand().equals(difficultyButton.getText())) {
            menu.setVisible(false);
            difficultyMenu(this);
        }
        else if(e.getActionCommand().equals(creditsButton.getText())) {
            menu.setVisible(false);
            creditsMenu(this);
        }
        else if(e.getActionCommand().equals(instrButton.getText())) {
            menu.setVisible(false);
            instrMenu(this);
        }

        //********************DIFFICULTY MENU*************************
        if(e.getActionCommand().equals(easyDiffButton.getText())) {
            difficulty = 1;
            difficultyFrame.setVisible(false);
            menu.setVisible(true);
        }
        else if(e.getActionCommand().equals(mediumDiffButton.getText())) {
            difficulty = 2;
            difficultyFrame.setVisible(false);
            menu.setVisible(true);
        }
        else if(e.getActionCommand().equals(hardDiffButton.getText())) {
            difficulty = 3;
            difficultyFrame.setVisible(false);
            menu.setVisible(true);
        }
        else if(e.getActionCommand().equals(veryHardDiffButton.getText())) {
            difficulty = 4;
            difficultyFrame.setVisible(false);
            menu.setVisible(true);
        }

        //*********************SHOP**********************
        if(e.getActionCommand().equals(shopButtons[0].getText())) {
            if(!gunButtonWasClicked[0]) {
                pl.setWeapon(pistol);
                pl.addMoney(currentWeapon.getPrice());//if the user misclicks the pistol button, 
                                                      //"sell" the previous weapon so that the money is not lost
                if(currentWeapon != pl.getWeapon()) {
                    currentWeapon.destroy();
                }
                shopButtons[0].setEnabled(false);
                playerMoney.setText("Your money: " + pl.getMoney());
                gunButtonWasClicked[0] = true;
            }
        }
        else if(e.getActionCommand().equals(shopButtons[1].getText())) {
            if(!gunButtonWasClicked[1]) {
                if(pl.getMoney() < aRifle.getPrice()) {
                    /*shopMenuFrame.add(notEnoughMoneyLabel);
                    notEnoughMoneyLabel.setAlignmentX(LEFT_ALIGNMENT);
                    notEnoughMoneyLabel.setAlignmentY(LEFT_ALIGNMENT);*/
                }
                else {
                    pl.setWeapon(aRifle);
                    if(currentWeapon != pl.getWeapon()) {
                        currentWeapon.destroy();
	                    pl.removeMoney(aRifle.getPrice());
	                    shopButtons[1].setEnabled(false);
	                    playerMoney.setText("Your money: " + pl.getMoney());
                    }
                }
                gunButtonWasClicked[1] = true;
            }
        }
        else if(e.getActionCommand().equals(shopButtons[2].getText())) {
            if(!gunButtonWasClicked[2]) {
                if(pl.getMoney() < sRifle.getPrice()) {
                    /*shopMenuFrame.add(notEnoughMoneyLabel);
                    notEnoughMoneyLabel.setAlignmentX(LEFT_ALIGNMENT);
                    notEnoughMoneyLabel.setAlignmentY(LEFT_ALIGNMENT);*/
                }
                else {
                    pl.setWeapon(sRifle);
                    if(currentWeapon != pl.getWeapon()) {
                        currentWeapon.destroy();
                    }
                    pl.removeMoney(sRifle.getPrice());
                    shopButtons[2].setEnabled(false);
                    playerMoney.setText("Your money: " + pl.getMoney());
                }
                gunButtonWasClicked[2] = true;
            }
        }
        else if(e.getActionCommand().equals(shopButtons[3].getText())) {
            if(gunButtonWasClicked[3]) {
                if(pl.getMoney() < lMG.getPrice()) {
                    /*shopMenuFrame.add(notEnoughMoneyLabel);
                    notEnoughMoneyLabel.setAlignmentX(LEFT_ALIGNMENT);
                    notEnoughMoneyLabel.setAlignmentY(LEFT_ALIGNMENT);*/
                }
                else {
                    pl.setWeapon(lMG);
                    if(currentWeapon != pl.getWeapon()) {
                        currentWeapon.destroy();
                    }
                    pl.removeMoney(lMG.getPrice());
                    shopButtons[3].setEnabled(false);
                    playerMoney.setText("Your money: " + pl.getMoney());
                }
                gunButtonWasClicked[3] = true;
            }
        }
        
        //****************UTILITY****************
        if(e.getActionCommand().equals(shopButtons[4].getText())) {
            if(!gunButtonWasClicked[4]) {
            	gunButtonWasClicked[4] = true;
                shopMenuFrame.setEnabled(false);
                shopMenuFrame.setVisible(false);
                isInShop = false;
                reset();
            }
        }
        else if(e.getActionCommand().equals(exitButton.getText())) {
            System.exit(0);
        }
        else if(e.getActionCommand().equals(backButton.getText())) {
            difficultyFrame.setVisible(false);
            creditsFrame.setVisible(false);
            instrFrame.setVisible(false);
            menu.setVisible(true);
        }
    }
    
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        int ticks = 0;
        double ns = 1000000000/amountOfTicks;//9 zeros
        double deltaTime = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        
        
        System.out.println("About to start running");
        while (isRunning) {
            long now = System.nanoTime();
            deltaTime += (now - lastTime) / ns;
            lastTime = now;
            if(deltaTime >= 1) {
                deltaTime --;
                tick();
                ticks ++;
            }
            
            frames ++;
            render();
            
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                this.setTitle("Frames: " + frames + ". Ticks: " + ticks);
                frames = 0;
                ticks = 0;
            }
        }
        System.out.println("Stopped running");
        
        if(isDead) {
            this.setVisible(false);
            deadMenu(this);
        }
    }
    
    /** public static void main(String[] args)
     * Main method, starts the game.
     * 
     * @param args
     *      Unused.
     */
    public static void main(String[] args) {
        BloodShooter bl = new BloodShooter();
        
        menu(bl);
    }
}
