/*
 * [Consumable.java];
 * 
 * The superclass for all the consumable items in the game that can only be used once. Since consumable effects are varied,
 * the superclass does not contain much.
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */ 

abstract class Consumable extends Item{
  Consumable(){
    this.setRarity(-1);
  }
  abstract String getEffectDescription();
}
