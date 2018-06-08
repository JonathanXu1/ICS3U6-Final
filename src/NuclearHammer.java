import java.awt.Graphics;

class NuclearHammer extends MeleeWeapon{
  NuclearHammer(int dbty) {
    super(dbty);
    
    this.setRarity(8);
    this.setDamage(100);
  }
  
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
  
  }
}