import java.awt.Graphics;

public abstract class RangedWeapon extends Weapon{
  private int shotsPerTurn;
  
  RangedWeapon(int dbty) {
    super(dbty);
  }
}