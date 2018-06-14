/////////////////////
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/////////////////////
class FloorTile extends WalkableTile{
  Image floor;
  String dir;
  double orientation = 0;
  FloorTile(Color minimapColor, String dir, String type){
    super(minimapColor, type);
    this.dir = dir;
  }
  FloorTile(Color minimapColor, String dir, double orientation, String type){
    super(minimapColor, type);
    this.dir = dir;
    this.orientation = orientation;
  }
  /**
   *drawTile
   *Draws the floor tile
   *@param: The Graphics g, the int x, the int y, the int width, the int height, the GamePanel gamePanel, and the boolean focus
   *@return: 
   */
  public void drawTile(Graphics g, int x, int y, int width, int height, GamePanel gamePanel, boolean focus){
    if(focus){
      floor = Toolkit.getDefaultToolkit().getImage(dir + ".png");
    } else{
      floor = Toolkit.getDefaultToolkit().getImage(dir + "Dark" + ".png");
    }
    if(orientation != 0){
      g.drawImage(rotate(orientation, floor), x,y,width,height,gamePanel);
    } else{
      g.drawImage(floor, x,y,width,height,gamePanel);
    }
  }
}
