import java.awt.Graphics;

public class PlasmaRapier extends MeleeWeapon {
  PlasmaRapier(int dbty) {
    super(dbty);
    this.setRarity(150);
    this.setDamage(20);   
  }
  
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel) {
  
  }
}