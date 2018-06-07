import java.awt.Graphics;

class EnergyLance extends MeleeWeapon {
  EnergyLance(int dbty){
    super(dbty);
    
    this.setRarity(50);
    this.setDamage(45);    
  }
  
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel) {
 
  }
} 