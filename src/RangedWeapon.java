public abstract class RangedWeapon extends Weapon{
  private int shotsPerTurn;
  private int range;
  
  RangedWeapon(int dbty) {
    super(dbty);
  }
  
  public void setSPT(int spt){
    this.shotsPerTurn = spt;
  }
  
  public int returnSPT() {
    return this.shotsPerTurn;
  }
  
  public void setRange(int rng){
    this.range = rng;
  }

  public int returnRange() {
    return this.range;
  }
  
}