/*
 * [PlasmaRapier.java];
 * 
 * The specific class for the Plasma Rapier, the best melee weapon in the game. It has the highest damage.
 * It sets all the stats in the constructor and has code for graphics.
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
class PlasmaRapier extends MeleeWeapon {
  Image plasmaRapier;
  PlasmaRapier(int dbty) {
    super(dbty);
    this.setRarity(5);
    this.setDamage(25);   
    this.setName("Plasma Rapier");
  }
  
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel) {
    plasmaRapier = Toolkit.getDefaultToolkit().getImage("../res/PlasmaRapier.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(plasmaRapier, x,y,width,height,gamePanel);
  }
}