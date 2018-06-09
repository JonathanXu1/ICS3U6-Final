import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
class EnergySuit extends Armor {
  Image energySuit;
  EnergySuit(int dbty){
    super(dbty);
    this.setRarity(3);
    this.setDefense(15); 
  }
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    energySuit = Toolkit.getDefaultToolkit().getImage("../res/EnergySuit.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(energySuit, x,y,width,height,gamePanel);
  }
}