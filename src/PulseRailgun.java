/*
 * [PulseRailgun.java];
 * 
 * The specific class for the Pulse Railgun, a rare ranged weapon. It has very high damage.
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
class PulseRailgun extends RangedWeapon {
  Image pulseRailgun;
  PulseRailgun(int dbty){ 
    super(dbty);
    this.setRarity(4);
    this.setDamage(15);
    this.setName("Pulse Railgun");
  }
  /**
   *drawItem
   *Draws the pulse railgun
   *@param: The Graphics g, the int x, the int y, the int width, the int height, and the GamePanel gamePanel
   *@return: 
   */
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    pulseRailgun = Toolkit.getDefaultToolkit().getImage("../res/PulseRailgun.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(pulseRailgun, x,y,width,height,gamePanel);
  }
}
