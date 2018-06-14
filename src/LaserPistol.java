/*
 * [LaserPistol.java];
 * 
 * The specific class for the Laser Pistol, a common ranged weapon. It low damage.
 * It sets all the stats in the constructor and has code for graphics.
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
class LaserPistol extends RangedWeapon {
  Image laserPistol;
  LaserPistol(int dbty){ 
    super(dbty);
    this.setRarity(2);
    this.setDamage(5);
    this.setName("Laser Pistol");
  }
  
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    laserPistol = Toolkit.getDefaultToolkit().getImage("../res/LaserPistol.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(laserPistol, x,y,width,height,gamePanel);
  }
}