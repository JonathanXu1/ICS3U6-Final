/*
 * [SolarScorcher.java];
 * 
 * The specific class for the Solar Scorcher, the rarest and best ranged weapon in the game. It has very high damage.
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

class SolarScorcher extends RangedWeapon {
  Image solarScorcher;
  SolarScorcher(int dbty){ 
    super(dbty);
    this.setRarity(5);
    this.setDamage(20);
    this.setName("Solar Scorcher");
  }
  /**
   *drawItem
   *Draws the solar scorcher
   *@param: The Graphics g, the int x, the int y, the int width, the int height, and the GamePanel gamePanel
   *@return: 
   */
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    solarScorcher = Toolkit.getDefaultToolkit().getImage("../res/SolarScorcher.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(solarScorcher, x,y,width,height,gamePanel);
  }
}
