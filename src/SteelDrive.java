/////////////////////
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
/////////////////////
class SteelDrive extends Drive{
  private Image steelDrive;
  SteelDrive(){
    this.setName("Steel Drive");
  }
  /**
   *upgrade 
   *Creates a steel enchantment
   *@param: Item chosenEquip
   *@return: An Item
   */
  public Item upgrade (Item chosenEquip){
    ((Equipment)(chosenEquip)).setDurabilityCap(((Equipment)(chosenEquip)).getDurabilityCap()+25);
    ((Equipment)(chosenEquip)).setDurability(((Equipment)(chosenEquip)).getDurabilityCap());
    return (chosenEquip);
  }
  /**
   *drawItem
   *Draws the steel drive
   *@param: The Graphics g, the int x, the int y, the int width, the int height, and the GamePanel gamePanel
   *@return: 
   */
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    steelDrive = Toolkit.getDefaultToolkit().getImage("../res/SteelDrive.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(steelDrive, x,y,width,height,gamePanel);
  }
  /**
   *getEffectDescription
   *Returns the effect description
   *@param: 
   *@return: A String
   */
  public String getEffectDescription(){
    return ("This drive will restore all lost durability and increase the durability cap by 25");
  }
}
