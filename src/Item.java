/*
 * [Item.java];
 * 
 * One of the largest and most important superclasses, that encomapsses all items in the game. it is abstract and used to
 * allow polymorphism in various item arrays such as the map and inventory
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */


/////////////////////
import java.awt.Graphics;
/////////////////////
public abstract class Item{
  private int rarity;
  private String name;
  private boolean selected = false;
  Item(){      
  }
  /**
   *drawItem
   *Draws the item , is an abstract method
   *@param: The Graphics g, the int x, the int y, the int width, the int height, the GamePanel gamePanel
   *@return: 
   */
  abstract public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel);
  //Getters and setters
  
  /**
   *getRarity
   *Returns the rarity of the item
   *@param: 
   *@return: The int rarity
   */
  public int getRarity() {
    return this.rarity;
  }
  /**
   *setRarity
   *Sets the rarity of the item
   *@param: The int rarity
   *@return: 
   */
  public void setRarity(int r) {
    this.rarity = r;
  }
  /**
   *getName
   *Returns the name of the item
   *@param: 
   *@return: The String name
   */
  public String getName(){
    return (name);
  }
  /**
   *setName
   *Sets the name of the item
   *@param: The string name
   *@return: 
   */
  public void setName(String name){
    this.name = name;
  }
  /**
   *getItemSelected
   *Returns if the item is selected
   *@param: 
   *@return: The boolean selected
   */
  public boolean getItemSelected(){
    return (selected);
  }
  /**
   *setItemSelected
   *Sets the item to be selected
   *@param: The boolean selected
   *@return: 
   */
  public void setItemSelected (boolean selected){
    this.selected = selected;
  }
}