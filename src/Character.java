//Imports
/////////////////////
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

/////////////////////
class Character extends Entity{
//Constructor
  private BufferedImage[] sprites;
  private int lastSprite = 4;
  private int movementMod=-1;
  private int movementCount=0;
  private int xp = 0;
  private int xpCap = 50;
  private int hunger = 200;
  
  Character(int h,int hC,int a,boolean freezeStatus,boolean lightningStatus,boolean flameStatus, Color minimapColor){
    super (h,hC,a,freezeStatus,lightningStatus,flameStatus, minimapColor);
    try {
      BufferedImage sheet = ImageIO.read(new File("../res/Character.png"));
      sprites = new BufferedImage[12];
      for (int i = 0; i < 4;i++){
        for (int j = 0; j < 3; j++){
          sprites[(i*3) + j] = sheet.getSubimage(j* 15,i* 15,15,15);
        }
      }
    }catch(Exception e) {
      System.out.println("Error");
    }
  }
  /**
   *drawEntity
   *Draws the character
   *@param: The Graphics g, the int x, the int y, the int width, the int height, the int xDirection, the int yDirection, and the GamePanel gamePanel
   *@return: 
   */
  public void drawEntity(Graphics g, int x, int y, int width, int height, int xDirection, int yDirection, GamePanel gamePanel){
    if ((xDirection==0)&&(yDirection==0)){
      g.drawImage(sprites[lastSprite], x,y,width,height,gamePanel);
    }else{
      movementCount++;
      if (xDirection==0){
        g.drawImage(sprites[((yDirection/10+1)/2)*3+1+movementMod], x,y,width,height,gamePanel);
        lastSprite= ((yDirection/10+1)/2)*3+1;
      }else{
        g.drawImage(sprites[((xDirection/10+1)/2+2)*3+1+movementMod], x,y,width,height,gamePanel);
        lastSprite= ((xDirection/10+1)/2+2)*3+1;
      }
      if (movementCount==10){
        if (movementMod==1){
          movementMod =-1;
        }else if (movementMod==-1){
          movementMod =1;
        }
        movementCount =0;
      }
    }
  }
//Getters and setters
  /**
   *getXp
   *Returns the xp
   *@param: 
   *@return: An int
   */
  public int getXp(){
    return xp;
  }
  /**
   *changeXp
   *Modifies the xp
   *@param: int xp
   *@return: 
   */
  public void changeXp(int xp){
    this.xp += xp;
  }
  /**
   *setXp
   *Sets the xp
   *@param: int xp
   *@return: 
   */
  public void setXp(int xp) {
    this.xp = xp;
  }
  /**
   *getXpCap
   *Gets the xp cap
   *@param: 
   *@return: An int
   */
  public int getXpCap(){
    return xpCap;
  }
  /**
   *setXpCap
   *Sets the xp cap
   *@param: int cap
   *@return: 
   */
  public void setXpCap(int cap){
    this.xpCap = cap;
  }
  /**
   *getHunger
   *Gets the hunger
   *@param: 
   *@return: An int
   */
  public int getHunger(){
    return hunger;
  }
  /**
   *setHunger
   *Sets the hunger
   *@param: int hunger
   *@return: 
   */
  public void setHunger(int hunger){
    this.hunger = hunger;
  }
}
