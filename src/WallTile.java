import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

class WallTile extends Tile{
  Image wall;
  String dir;
  WallTile(Color minimapColor, String dir){
    super(minimapColor);
    this.dir = dir;
  }
  public void drawTile(Graphics g, int x, int y, int width, int height, GamePanel gamePanel, boolean focus){
    if(focus){
      wall = Toolkit.getDefaultToolkit().getImage(dir + ".png");
    } else{
      wall = Toolkit.getDefaultToolkit().getImage(dir + "Dark" + ".png");
    }
    g.drawImage(wall, x,y,width,height,gamePanel);
  }
}