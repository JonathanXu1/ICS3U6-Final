/////////////////////
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
/////////////////////
class PowerDrive extends Drive{
  private Image powerDrive;
  PowerDrive(){
    this.setName("Power Drive");
  }
  /**
   *upgrade 
   *Increases damage and durability
   *@param: Item chosenEquip
   *@return: An Item
   */
  public Item upgrade (Item chosenEquip){
    ((Equipment)(chosenEquip)).setDurability(((Equipment)(chosenEquip)).getDurabilityCap());
    if (chosenEquip instanceof Armor){
      ((Armor)(chosenEquip)).setDefense(((Armor)(chosenEquip)).getDefense()+5);
    }else if (chosenEquip instanceof Weapon){
      ((Weapon)(chosenEquip)).setDamage(((Weapon)(chosenEquip)).getDamage()+5);
    }
    return (chosenEquip);
  }
  /**
   *drawItem
   *Draws the power drive
   *@param: The Graphics g, the int x, the int y, the int width, the int height, and the GamePanel gamePanel
   *@return: 
   */
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    powerDrive = Toolkit.getDefaultToolkit().getImage("../res/PowerDrive.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(powerDrive, x,y,width,height,gamePanel);
  }
  /**
   *getEffectDescription
   *Returns the effect description
   *@param: 
   *@return: A String
   */
  public String getEffectDescription(){
    return ("This drive will restore all lost durability and increase attack or defense by 5");
  }
}
