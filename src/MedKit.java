/////////////////////
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;

/////////////////////
class MedKit extends Consumable{
  private Image medKit;
  MedKit(){
    this.setName("Medicine Kit");
  }
  /**
   *heal
   *Heals the character
   *@param: Character player
   *@return: A Character
   */
  public Character heal(Character player){
    player.setHealth(player.getHealthCap());
    return (player);
  }
  /**
   *drawItem
   *The med kit is drawn
   *@param: The Graphics g, the int x, the int y, the int width, the int height, and the GamePanel gamePanel
   *@return: 
   */
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    medKit = Toolkit.getDefaultToolkit().getImage("../res/MedKit.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(medKit, x,y,width,height,gamePanel);
  }
  /**
   *getEffectDescription
   *The effect description is returned
   *@param: 
   *@return: A String
   */
  public String getEffectDescription(){
    return ("This will heal the player to full");  
  }
}
