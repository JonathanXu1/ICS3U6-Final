abstract class Drive extends Consumable{
  Drive(){
    this.setRarity(-1);
  }
  abstract Item upgrade (Item chosenEquip);
  abstract String getEffectDescription();
}