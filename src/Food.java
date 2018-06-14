/////////////////////
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;

/////////////////////
class Food extends Consumable{ // Food objects restore health to the player and prevent them from taking starvation damage
  private Image food; // Image for graphics
  
  Food(){
    this.setName("Food Pack");
  }
  
  /**
   *restoreHunger
   *Restores the hunger
   *@param: Character player
   *@return: A Character
   */
  public Character restoreHunger(Character player){
    if (player.getHealth()+10<player.getHealthCap()){
      player.setHealth(player.getHealth()+10);
    }else{
      player.setHealth(player.getHealthCap());  
    }
    player.setHunger(200);
    return (player);
  }
  
  /**
   *drawItem
   *Draws the item
   *@param: The Graphics g, the int x, the int y, the int width, the int height, and the GamePanel gamePanel
   *@return: 
   */
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    food = Toolkit.getDefaultToolkit().getImage("../res/FoodPack.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(food, x,y,width,height,gamePanel);
  }
  /**
   *getEffectDescription
   *Returns the effect description
   *@param: 
   *@return: A String
   */
  public String getEffectDescription(){
    return ("This will heal the player by 10 health and restore hunger to full");  
  }
}
