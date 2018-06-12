import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

class DoorTile extends WalkableTile{
  Image door;
  boolean entityOnDoor = true;
  DoorTile(Color minimapColor, String info){
    super(minimapColor, info);
  }
  public void drawTile(Graphics g, int x, int y, int width, int height, GamePanel gamePanel, boolean focus){
    if(focus){
      if (entityOnDoor){
        door = Toolkit.getDefaultToolkit().getImage("../res/OpenDoor.png");
      }else{
        door = Toolkit.getDefaultToolkit().getImage("../res/DoorTile.png");
      }
    } else{
      if (entityOnDoor){
        door = Toolkit.getDefaultToolkit().getImage("../res/OpenDoorDark.png");
      }else{
        door = Toolkit.getDefaultToolkit().getImage("../res/DoorTileDark.png");
      }
    }
    g.drawImage(door, x,y,width,height,gamePanel);
  }
  public void setEntityOnDoor (boolean entityOnDoor){
    this.entityOnDoor= entityOnDoor;
  }
}