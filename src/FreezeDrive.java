import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
class FreezeDrive extends Drive{
  private Image freezeDrive;
  FreezeDrive(){
    this.setName("Freeze Drive");
  }
  public Item upgrade (Item chosenEquip){
    if (chosenEquip instanceof Armor){
      ((Armor)(chosenEquip)).setFreezeDefense(((Armor)(chosenEquip)).getFreezeDefense()+10);
    }else if (chosenEquip instanceof Weapon){
      ((Weapon)(chosenEquip)).setFreezeChance(((Weapon)(chosenEquip)).getFreezeChance()+10);
    }
    return (chosenEquip);
  }
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    freezeDrive = Toolkit.getDefaultToolkit().getImage("../res/FreezeDrive.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(freezeDrive, x,y,width,height,gamePanel);
  }
  public String getEffectDescription(){
    return ("This drive increases freeze chance by 10%, breaking defense for weapons and grants freeze immunity with armors");  
  }
}