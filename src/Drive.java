/*
 * [Drive.java];
 * 
 * The superclass for the upgrade drives in the, which are one-time use upgrades to weapons and armor. 
 * it sets up the asbtract method for all the specific drive items
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */ 

abstract class Drive extends Consumable{
  abstract Item upgrade (Item chosenEquip);
}