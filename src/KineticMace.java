import java.awt.Graphics;

class KineticMace extends MeleeWeapon{
  KineticMace(int dbty) {
    super(dbty);
    
    this.setRarity(15);
    this.setDamage(60);    
  }
  
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel) {
  
  }
}