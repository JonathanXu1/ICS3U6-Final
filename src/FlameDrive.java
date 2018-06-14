/*
 * [FlameDrive.java];
 * 
 * The specific class for the consumable drive that can grant weapons a chance of inflicting flame (damage over time) 
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
class FlameDrive extends Drive{
  private Image flameDrive;
  FlameDrive(){
    this.setName("Flame Drive");
  }
  /**
   *upgrade 
   * Upgrades the set item with flame enchantments
   *@param: Item chosenEquip
   *@return: An Item
   */
  public Item upgrade (Item chosenEquip){
    if (chosenEquip instanceof Armor){
      ((Armor)(chosenEquip)).setFlameDefense(((Armor)(chosenEquip)).getFlameDefense()+10);
    }else if (chosenEquip instanceof Weapon){
      ((Weapon)(chosenEquip)).setFlameChance(((Weapon)(chosenEquip)).getFlameChance()+10);
    }
    return (chosenEquip);
  }
  /**
   *drawItem
   * Draws the flame drive
   *@param: The Graphics g, the int x, the int y, the int width, the int height, and the GamePanel gamePanel
   *@return: 
   */
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    flameDrive = Toolkit.getDefaultToolkit().getImage("../res/FlameDrive.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(flameDrive, x,y,width,height,gamePanel);
  }
  /**
   *getEffectDescription
   *Returns the description
   *@param: 
   *@return: A String
   */
  public String getEffectDescription(){
    return ("This drive increases burn chance by 10% for weapons and grants burn immunity with armors");  
  }
}
