import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

class LightningRoomba extends Roomba{
  private BufferedImage[] sprites;
  private int lastSprite = 4;
  private int movementMod=-1;
  private int movementCount=0;
  LightningRoomba(int h,int hC,int a,int sP,boolean freezeStatus,boolean lightningStatus,boolean flameStatus, Color minimapColor, boolean enraged){
    super (h,hC,a,sP,freezeStatus,lightningStatus,flameStatus, minimapColor,enraged);
    try {
      BufferedImage sheet = ImageIO.read(new File("../res/LightningRoomba.png"));
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
  public void drawEntity(Graphics g, int x, int y, int width, int height, int xDirection, int yDirection, GamePanel gamePanel){
    if ((xDirection==0)&&(yDirection==0)){
      g.drawImage(sprites[lastSprite], x,y,width,height,gamePanel);
    }else{
      movementCount++;
      if (xDirection==0){
        g.drawImage(sprites[((yDirection+1)/2)*3+1+movementMod], x,y,width,height,gamePanel);
        lastSprite= ((yDirection+1)/2)*3+1;
      }else{
        g.drawImage(sprites[((xDirection+1)/2+2)*3+1+movementMod], x,y,width,height,gamePanel);
        lastSprite= ((xDirection+1)/2+2)*3+1;
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
}