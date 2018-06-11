import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
class FlameDrive extends Drive{
  private Image flameDrive;
  FlameDrive(){
    this.setName("Flame Drive");
  }
  public Item upgrade (Item chosenEquip){
    if (chosenEquip instanceof Armor){
      ((Armor)(chosenEquip)).setFlameDefense(true);
    }else if (chosenEquip instanceof Weapon){
      ((Weapon)(chosenEquip)).setFlameChance(((Weapon)(chosenEquip)).getFlameChance()+5);
    }
    return (chosenEquip);
  }
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    flameDrive = Toolkit.getDefaultToolkit().getImage("../res/FlameDrive.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(flameDrive, x,y,width,height,gamePanel);
  }
  public String getEffectDescription(){
   return ("This drive can increase burn chance by 5% for weapons and grants burn immunity with armors");  
  }
}