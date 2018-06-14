/*
 * [FreezeDrive.java];
 * 
 * The specific class for the consumable drive that can grant weapons a chance of inflicting freeze (armor reduction) 
 * or gives armor resistance to it
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */


/////////////////////
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;

/////////////////////
class FreezeDrive extends Drive{
  private Image freezeDrive;
  FreezeDrive(){
    this.setName("Freeze Drive");
  }
  /**
   *upgrade 
   *Upgrades with freeze enchantments
   *@param: Item chosenEquip
   *@return: An Item
   */
  public Item upgrade (Item chosenEquip){
    if (chosenEquip instanceof Armor){
      ((Armor)(chosenEquip)).setFreezeDefense(((Armor)(chosenEquip)).getFreezeDefense()+10);
    }else if (chosenEquip instanceof Weapon){
      ((Weapon)(chosenEquip)).setFreezeChance(((Weapon)(chosenEquip)).getFreezeChance()+10);
    }
    return (chosenEquip);
  }
  /**
   *drawItem
   *Draws the item
   *@param: The Graphics g, the int x, the int y, the int width, the int height, and the GamePanel gamePanel
   *@return: 
   */
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    freezeDrive = Toolkit.getDefaultToolkit().getImage("../res/FreezeDrive.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(freezeDrive, x,y,width,height,gamePanel);
  }
  /**
   *getEffectDescription
   *Returns the freeze efffect description
   *@param: 
   *@return: A String
   */
  public String getEffectDescription(){
    return ("This drive increases freeze chance by 10%, breaking defense for weapons and grants freeze immunity with armors");  
  }
}
