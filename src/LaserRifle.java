public class LaserRifle extends RangedWeapon {
  // item stats;
   
  
  LaserRifle(int dbty){ 
    super(dbty);
    this.setRarity(100);
    int[] comp = {0,1};
    this.setComponents(comp);
    this.setDamage(25);
    this.setKnockback(0);
    this.setSPT(1);
    this.setRange(9999);    
  }
}