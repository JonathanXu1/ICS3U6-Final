import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

class WallTile extends Tile{
  Image wall;
  String dir;
  double orientation = 0;
  WallTile(Color minimapColor, String dir, String type){
    super(minimapColor, type);
    this.dir = dir;
  }
  WallTile(Color minimapColor, String dir, double orientation, String type){
    super(minimapColor, type);
    this.dir = dir;
    this.orientation = orientation;
  }
  public void drawTile(Graphics g, int x, int y, int width, int height, GamePanel gamePanel, boolean focus){
    if(focus){
      wall = Toolkit.getDefaultToolkit().getImage(dir + ".png");
    } else{
      wall = Toolkit.getDefaultToolkit().getImage(dir + "Dark" + ".png");
    }
    if(orientation != 0){
      g.drawImage(rotate(orientation, wall), x,y,width,height,gamePanel);
    } else{
      g.drawImage(wall, x,y,width,height,gamePanel);
    }
  }
}