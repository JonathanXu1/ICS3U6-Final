import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

class WallTile extends Tile{
  Image wall;
  WallTile(Color minimapColor){
    super(minimapColor);
  }
  public void drawTile(Graphics g, int x, int y, int width, int height, GamePanel gamePanel, boolean focus){
    if(focus){
      wall = Toolkit.getDefaultToolkit().getImage("../res/WallTile.png");
    } else{
      wall = Toolkit.getDefaultToolkit().getImage("../res/WallTileDark.png");
    }
    g.drawImage(wall, x,y,width,height,gamePanel);
  }
}