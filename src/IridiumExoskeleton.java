/////////////////////
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;

/////////////////////
class IridiumExoskeleton extends Armor {
  Image iridiumExoskeleton;
  IridiumExoskeleton(int dbty){
    super(dbty);
    this.setRarity(5);
    this.setDefense(25); 
    this.setName("Iridium Exoskeleton");
  }
  /**
   *drawItem
   *Draws the item
   *@param: The Graphics g, the int x, the int y, the int width, the int height, and the GamePanel gamePanel
   *@return: 
   */
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    iridiumExoskeleton = Toolkit.getDefaultToolkit().getImage("../res/IridiumExoskeleton.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(iridiumExoskeleton, x,y,width,height,gamePanel);
  }
}
