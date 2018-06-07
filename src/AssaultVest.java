import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
public class AssaultVest extends Armor {
  Image assaultVest;
  AssaultVest(int dbty){
    super(dbty);
    this.setRarity(2);
    this.setDefense(10); 
  }
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    assaultVest = Toolkit.getDefaultToolkit().getImage("../res/AssaultVest.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(assaultVest, x,y,width,height,gamePanel);
  }
}