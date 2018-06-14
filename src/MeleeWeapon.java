/*
 * [MeleeWeapon.java];
 * 
 * The superclass for Melee weapons, which essetially acts as a delineation between weapons.
 * it ferries durability up to the Weapon superclass.
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */

public abstract class MeleeWeapon extends Weapon{  
  MeleeWeapon(int dbty) {
    super(dbty);
  }  
}