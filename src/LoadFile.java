class LoadFile {
  private char[][] charMap;
  private Entity[][] entityMap;
  private Item[][] itemMap;
  private Item[][] inventory;
  
  LoadFile(char[][] cM, Entity[][] eM, Item[][] iM, Item[][] iN) {
    this.charMap = cM;
    this.entityMap = eM;
    this.itemMap = iM;
    this.inventory = iN;
  }    
  
  public char[][] returnMap() {
    return this.charMap;
  }
  
  public Entity[][] returnEntites() {
    return this.entityMap;
  }
  
  public Item[][] returnItems() {
    return this.itemMap;
  }
  
  public Item[][] returnInventory() {
    return this.inventory;
  }

}