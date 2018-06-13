import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
class Food extends Consumable{
  private Image food;
  Food(){
    this.setName("Food Pack");
  }
  public Character restoreHunger(Character player){
    if (player.getHealth()+10<player.getHealthCap()){
      player.setHealth(player.getHealth()+10);
    }else{
      player.setHealth(player.getHealthCap());  
    }
    player.setHunger(200);
    return (player);
  }
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    food = Toolkit.getDefaultToolkit().getImage("../res/FoodPack.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(food, x,y,width,height,gamePanel);
  }
  public String getEffectDescription(){
    return ("This will heal the player by 10 health and restore hunger to full");  
  }
}