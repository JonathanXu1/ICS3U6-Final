/////////////////////
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/////////////////////
class DoorTile extends WalkableTile{
  Image door;
  boolean entityOnDoor = true;
  DoorTile(Color minimapColor, String info){
    super(minimapColor, info);
  }
  /**
   *drawTile
   *Draws a door
   *@param: The Graphics g, the int x, the int y, the int width, the int height, the GamePanel gamePanel, and the boolean focus
   *@return: 
   */
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
  //Getters and setters
  /**
   *setEntityOnDoor 
   *Sets true if the entity is on the door
   *@param: boolean entityOnDoor
   *@return: 
   */
  public void setEntityOnDoor (boolean entityOnDoor){
    this.entityOnDoor= entityOnDoor;
  }
  /**
   *getEntityOnDoor
   *Returns whether or not an entity is on the door
   *@param: 
   *@return: A boolean
   */
  public boolean getEntityOnDoor(){
    return (entityOnDoor);
  }
}
