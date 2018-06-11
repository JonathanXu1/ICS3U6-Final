import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
class Inventory{
  Item[][] inventory = new Item [4][6];
  boolean selected = false;
  Item tempOriginal;
  Item tempNew;
  int lineSkip=0;
  Font stats = new Font("Montserrat", Font.PLAIN, 12);
  Inventory (){
  }
  public void setItem(int positionX, int positionY, Item item){
    inventory[positionY][positionX] = item;
  }
  public Item getItem (int positionX, int positionY){
    return (inventory[positionY][positionX]);
  }
  public void drawSelectedItem ( Graphics g, int positionX, int positionY,int x, int y, int width, int height, GamePanel gamePanel){
    inventory[positionY][positionX].drawItem(g, x, y, width, height, gamePanel);
  }
  public void setSelected (int positionX, int positionY, boolean selected){
    inventory[positionY][positionX].setItemSelected(selected);
  }
  public void swap (int x,int y,int newX,int newY){
    tempOriginal=inventory[y][x];
    tempNew= inventory[newY][newX];
    inventory[y][x] = tempNew;
    inventory[newY][newX] = tempOriginal;
  }
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
  }
  public void writePending(Graphics g, int positionX,int positionY,int xPixelPos,int yPixelPos){
    g.setColor(Color.WHITE);
    g.setFont(stats);
    lineSkip = 0;
    g.drawString(((Consumable)(inventory[positionY][positionX])).getEffectDescription(), xPixelPos,yPixelPos);
    lineSkip = lineSkip+12;
    g.drawString("Click outside the inventory to cancel the upgrade",xPixelPos,yPixelPos+lineSkip);
  }
}