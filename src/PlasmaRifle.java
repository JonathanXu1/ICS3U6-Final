import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
class PlasmaRifle extends RangedWeapon {
  Image plasmaRifle;
  PlasmaRifle(int dbty){ 
    super(dbty);
    this.setRarity(3);
    this.setDamage(10);
    this.setSPT(1);
    this.setName("Plasma Rifle");
  }
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    plasmaRifle = Toolkit.getDefaultToolkit().getImage("../res/PlasmaRifle.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(plasmaRifle, x,y,width,height,gamePanel);
  }
}