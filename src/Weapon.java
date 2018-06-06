public abstract class Weapon extends Equipment {
  private int damage;
  private int knockback;
  
  Weapon(int dbty) {
    super(dbty);
  }
  
  public int getDamage() {
    return this.damage;
  }
  
  public void setDamage(int dm) {
    this.damage = dm;
  }
  
  public int getKnockback() {
    return this.knockback;
  }
  
  public void setKnockback(int kb) {
    this.knockback = kb;
  }      
}