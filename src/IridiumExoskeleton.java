/*
 * [IridiumExoskeleton.java];
 * 
 * The specific class for the iridium exoskeleton, the rarest and best armor in the game. It is very
 * effective at negating damage. It sets all the stats in the constructor and has code for graphics
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
class IridiumExoskeleton extends Armor {
  Image iridiumExoskeleton;
  IridiumExoskeleton(int dbty){
    super(dbty);
    this.setRarity(5);
    this.setDefense(25); 
    this.setName("Iridium Exoskeleton");
  }
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    iridiumExoskeleton = Toolkit.getDefaultToolkit().getImage("../res/IridiumExoskeleton.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(iridiumExoskeleton, x,y,width,height,gamePanel);
  }
}