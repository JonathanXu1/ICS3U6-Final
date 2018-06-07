public abstract class Weapon extends Equipment {
  private int damage;
  
  Weapon(int dbty) {
    super(dbty);
  }
  
  public int getDamage() {
    return this.damage;
  }
  
  public void setDamage(int dm) {
    this.damage = dm;
  }      
}