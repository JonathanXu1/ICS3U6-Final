abstract class Consumable extends Item{
  Consumable(){
    this.setRarity(-1);
  }
  abstract String getEffectDescription();
}