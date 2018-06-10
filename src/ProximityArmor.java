import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
class ProximityArmor extends Armor {
  Image proximityArmor;
  ProximityArmor(int dbty){
    super(dbty);
    this.setRarity(4);
    this.setDefense(20); 
    this.setName("Proximity Armor");
  }
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    proximityArmor = Toolkit.getDefaultToolkit().getImage("../res/ProximityArmor.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(proximityArmor, x,y,width,height,gamePanel);
  }
}