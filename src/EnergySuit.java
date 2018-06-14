import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
class EnergySuit extends Armor {
  Image energySuit;
  EnergySuit(int dbty){
    super(dbty); // Pass durability to Armor superclass
    this.setRarity(3); // Set rarity to 3
    this.setDefense(15); // set defense to 15
    this.setName("Energy Suit"); // set name 
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