/////////////////////
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
/////////////////////
class PlasmaRapier extends MeleeWeapon {
  Image plasmaRapier;
  PlasmaRapier(int dbty) {
    super(dbty);
    this.setRarity(5);
    this.setDamage(25);   
    this.setName("Plasma Rapier");
  }
  
  /**
   *drawItem
   *Draws the items
   *@param: The Graphics g, the int x, the int y, the int width, the int height, and the GamePanel gamePanel
   *@return: 
   */
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel) {
    plasmaRapier = Toolkit.getDefaultToolkit().getImage("../res/PlasmaRapier.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(plasmaRapier, x,y,width,height,gamePanel);
  }
}
