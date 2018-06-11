import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

class FloorTile extends WalkableTile{
  Image floor;
  String dir;
  FloorTile(Color minimapColor, String dir){
    super(minimapColor);
    this.dir = dir;
  }
  public void drawTile(Graphics g, int x, int y, int width, int height, GamePanel gamePanel, boolean focus){
    if(focus){
      floor = Toolkit.getDefaultToolkit().getImage(dir + ".png");
    } else{
      floor = Toolkit.getDefaultToolkit().getImage(dir + "Dark" + ".png");
    }
    g.drawImage(floor, x,y,width,height,gamePanel);
  }
}