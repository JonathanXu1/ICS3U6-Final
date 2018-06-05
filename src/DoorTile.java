import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

class DoorTile extends WalkableTile{
  Image door;
  DoorTile(Color minimapColor){
    super(minimapColor);
  }
  public void drawTile(Graphics g, int x, int y, int width, int height, GamePanel gamePanel, boolean focus){
    if(focus){
      door = Toolkit.getDefaultToolkit().getImage("../res/DoorTile.png");
    } else{
      door = Toolkit.getDefaultToolkit().getImage("../res/DoorTileDark.png");
    }
    g.drawImage(door, x,y,width,height,gamePanel);
  }
}