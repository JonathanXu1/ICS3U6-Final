
/*
 * [EnergySword.java];
 * 
 * The specific class for the energy sword, a common melee weapon. It has sub-par damage.
 * It sets all the stats in the constructor and has code for graphics.
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */

/////////////////////
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;

/////////////////////
class EnergySword extends MeleeWeapon {
  Image energySword;
  EnergySword(int dbty){ // Passes durability to MeleeWeapon superClass
    super(dbty); 
    this.setRarity(2); // Sets rarity to 2
    this.setDamage(10); // Sets damage
    this.setName("Energy Sword"); // set name
  }
  /**
   *drawItem
   *Draws the energy sword
   *@param: The Graphics g, the int x, the int y, the int width, the int height, and the GamePanel gamePanel
   *@return: 
   */
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel) {
    energySword = Toolkit.getDefaultToolkit().getImage("../res/EnergySword.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(energySword, x,y,width,height,gamePanel);
  }
} 
