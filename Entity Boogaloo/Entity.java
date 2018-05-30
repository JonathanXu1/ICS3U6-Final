
abstract class Entity {
  private int health;
  private int healthCap;
  private int armor;
  private int speed; 
  private int status;  
  
  Entity(int h,int hC,int a,int sP,int sT) {
    this.health = h;
    this.healthCap = hC;
    this.armor = a;
    this.speed = sP;
    this.status = sT;
  }
  
  public boolean isDead() {
    if (this.health < 1) {
      return true;
    }
    return false;
  }
  
  public int getHealth() {
    return this.health;
  }
  
  public void setHealth(int updt) {
    this.health = updt;
  }
  
  public int getArmor() {
    return this.armor;
  }
  
  public void setArmor(int updt) {
    this.armor = updt;
  }
  
  public int getCap() {
    return this.healthCap;
  }
  
  public int getSpeed() {
    return this.speed;
  }
  
  public void setSpeed(int updt) {
    this.speed = updt;
  }
  
  public int getStatus() {
    return this.status;
  }
  
  public void setStatus(int updt) {
    this.status = updt;
  }
  
  
  
}