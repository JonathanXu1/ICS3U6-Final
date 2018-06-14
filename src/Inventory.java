/////////////////////
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
/////////////////////
class Inventory{
  Item[][] inventory = new Item [4][6];
  boolean selected = false;
  Item tempOriginal;
  Item tempNew;
  int lineSkip=0;
  Font stats = new Font("Consolas", Font.PLAIN, 12);
  Inventory (){
  }
  /**
   *drawSelectedItem 
   *Draws a certain item
   *@param: The Graphics g, the int positionX, the int positionY, the int x, the int y, the int width, the int height, and the GamePanel gamePanel
   *@return: 
   */
  public void drawSelectedItem ( Graphics g, int positionX, int positionY,int x, int y, int width, int height, GamePanel gamePanel){
    inventory[positionY][positionX].drawItem(g, x, y, width, height, gamePanel);
  }
  /**
   *setSelected 
   *Selects a certain item
   *@param: The int positionX, the int positionY, and the boolean selected
   *@return: 
   */
  public void setSelected (int positionX, int positionY, boolean selected){
    inventory[positionY][positionX].setItemSelected(selected);
  }
  /**
   *swap 
   * Swaps items
   *@param: The int x, the int y, the int newX, and the int newY
   *@return: 
   */
  public void swap (int x,int y,int newX,int newY){
    tempOriginal=inventory[y][x];
    tempNew= inventory[newY][newX];
    inventory[y][x] = tempNew;
    inventory[newY][newX] = tempOriginal;
  }
  /**
   *writeStats
   *Writes the stats of items
   *@param: The Graphics g, the int positionX, the int positionY, the int xPixelPos, and the int yPixelPos
   *@return: 
   */
  public void writeStats(Graphics g, int positionX,int positionY,int xPixelPos,int yPixelPos){
    g.setColor(Color.WHITE);
    g.setFont(stats);
    lineSkip = 0;
    g.drawString (((inventory[positionY][positionX])).getName(), xPixelPos, yPixelPos);
    lineSkip = lineSkip+12;
    if (!((inventory[positionY][positionX]).getRarity()==-1)){
      g.drawString("Rarity: "+((inventory[positionY][positionX])).getRarity()+"/5",xPixelPos,yPixelPos+lineSkip);
      lineSkip = lineSkip+12;
    }
    if (inventory[positionY][positionX] instanceof Equipment){
      g.drawString("Durability: "+((Equipment)(inventory[positionY][positionX])).getDurability()+"/"+((Equipment)(inventory[positionY][positionX])).getDurabilityCap(),xPixelPos,yPixelPos+lineSkip);
      lineSkip = lineSkip+12;
      if (inventory[positionY][positionX] instanceof Armor){
        g.drawString("Defense: "+((Armor)(inventory[positionY][positionX])).getDefense()+"",xPixelPos,yPixelPos+lineSkip);
        lineSkip = lineSkip+12;
      }else if (inventory[positionY][positionX] instanceof Weapon){
        g.drawString("Attack: "+((Weapon)(inventory[positionY][positionX])).getDamage()+"",xPixelPos,yPixelPos+lineSkip);
        lineSkip = lineSkip+12;
      }
    }
    if ((inventory[positionY][positionX]) instanceof Consumable){
      g.drawString(((Consumable)(inventory[positionY][positionX])).getEffectDescription(),xPixelPos,yPixelPos+lineSkip);
      lineSkip = lineSkip+12;
      g.drawString("Click the item again to use",xPixelPos,yPixelPos+lineSkip);
      lineSkip = lineSkip+12;
    }
    lineSkip=0;
    if ((inventory[positionY][positionX]) instanceof Weapon){
      if (((Weapon)(inventory[positionY][positionX])).getFlameChance()!=0){
        g.drawString("Burn chance: "+((Weapon)(inventory[positionY][positionX])).getFlameChance()+"%",xPixelPos+377,yPixelPos+lineSkip);
        lineSkip = lineSkip+12;
      }
      if (((Weapon)(inventory[positionY][positionX])).getFreezeChance()!=0){
        g.drawString("Freeze chance: "+((Weapon)(inventory[positionY][positionX])).getFreezeChance()+"%",xPixelPos+377,yPixelPos+lineSkip);
        lineSkip = lineSkip+12;
      }
      if (((Weapon)(inventory[positionY][positionX])).getLightningChance()!=0){
        g.drawString("Paralyze chance: "+((Weapon)(inventory[positionY][positionX])).getLightningChance()+"%",xPixelPos+377,yPixelPos+lineSkip);
        lineSkip = lineSkip+12;
      }
    }
    if ((inventory[positionY][positionX]) instanceof Armor){
      if (((Armor)(inventory[positionY][positionX])).getFlameDefense()!=0){
        g.drawString("Burn defense: "+((Armor)(inventory[positionY][positionX])).getFlameDefense()+"%",xPixelPos+377,yPixelPos+lineSkip);
        lineSkip = lineSkip+12;
      }
      if (((Armor)(inventory[positionY][positionX])).getFreezeDefense()!=0){
        g.drawString("Freeze defense: "+((Armor)(inventory[positionY][positionX])).getFreezeDefense()+"%",xPixelPos+377,yPixelPos+lineSkip);
        lineSkip = lineSkip+12;
      }
      if (((Armor)(inventory[positionY][positionX])).getLightningDefense()!=0){
        g.drawString("Paralyze defense: "+((Armor)(inventory[positionY][positionX])).getLightningDefense()+"%",xPixelPos+377,yPixelPos+lineSkip);
        lineSkip = lineSkip+12;
      }
    }
  }
  /**
   *writePending
   *Writes the pending message for items
   *@param: The Graphics g, the int positionX, the int positionY, the int xPixelPos, and the int yPixelPos
   *@return: 
   */
  public void writePending(Graphics g, int positionX,int positionY,int xPixelPos,int yPixelPos){
    g.setColor(Color.WHITE);
    g.setFont(stats);
    lineSkip = 0;
    g.drawString(((Consumable)(inventory[positionY][positionX])).getEffectDescription(), xPixelPos,yPixelPos);
    lineSkip = lineSkip+12;
    if (((Consumable)(inventory[positionY][positionX])) instanceof Drive){
      g.drawString("Click outside the inventory or the drive again to cancel the upgrade",xPixelPos,yPixelPos+lineSkip);
    }
  }
  //Getters and setters
  /**
   * getArrayInventory
   *Returns the inventory as an array
   *@param: 
   *@return: An Item[][]
   */
  public Item[][] getArrayInventory(){
    return (inventory);
  }
  /**
   *setItem
   *Sets a certain item to an inventory
   *@param: The int positionX, the int positionY, and the Item item
   *@return: 
   */
  public void setItem(int positionX, int positionY, Item item){
    inventory[positionY][positionX] = item;
  }
  /**
   *getItem 
   *Gets a certain item
   *@param: The int positionX, and the int positionY
   *@return: An Item
   */
  public Item getItem (int positionX, int positionY){
    return (inventory[positionY][positionX]);
  }
}
