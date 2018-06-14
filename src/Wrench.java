/*
 * [Wrench.java];
 * 
 * The specific class for the Wrench, the starting weapon for the player. it is a very bad item. Implements graphics
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
class Wrench extends MeleeWeapon{
  Image wrench;
  Wrench(int dbty) {
    super(dbty);
    this.setRarity(1);
    this.setDamage(5);    
    this.setName("Wrench");
  }
  
  /**
   *drawItem
   *Draws the wrench
   *@param: The Graphics g, the int x, the int y, the int width, the int height, and the GamePanel gamePanel
   *@return: 
   */
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel) {
    wrench = Toolkit.getDefaultToolkit().getImage("../res/Wrench.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(wrench, x,y,width,height,gamePanel);
  }
}
