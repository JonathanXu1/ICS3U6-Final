import javax.swing.JPanel;
import java.awt.Image;//Might need to change to buffered image
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import java.awt.Color;

class MenuBGPanel extends JPanel{
  private int xVal;
  private int yVal;
  private int pixelX, pixelY;
  private int starCount;
  private Image menuBg = Toolkit.getDefaultToolkit().getImage("../res/bg.png");
  private Star[][] stars = new Star[100][200];
  Random rand = new Random();
  MenuBGPanel(int xVal, int yVal){
    this.setFocusable(true);
    this.xVal = xVal;
    this.yVal = yVal;
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
    this.setLayout(null);
    
    pixelX = xVal/200;
    pixelY = yVal/100;
  }
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    g.drawImage(menuBg,0,0,xVal,yVal,this);
    
    starCount = 1; //# stars
    for (int i = 0; i <= starCount; i ++){
      int randX = rand.nextInt(200);
      int randY = rand.nextInt(100);
      if(stars[randY][randX] == null){
        stars[randY][randX] = new Star();
      }
    }
    for (int i = 0; i < stars.length; i ++){
      for (int j = 0; j < stars[0].length; j++){
        if(stars[i][j] != null){
          if(!stars[i][j].getDied()){
            stars[i][j].updateColor();
            g.setColor(stars[i][j].getColor());
            g.fillRect(j*pixelX, i*pixelY, pixelX, pixelY);
          } else{
            stars[i][j] = null;
          }
        }
      }
    }
  }
  public void refresh(){
    this.repaint();
    /*
     import javax.imageio.ImageIO;
     import java.awt.image.BufferedImage
     
     ...
     
     BufferedImage image = ImageIO.read(urlImage);
     int c = image.getRGB(x,y);
     int  red = (c & 0x00ff0000) >> 16;
     int  green = (c & 0x0000ff00) >> 8;
     int  blue = c & 0x000000ff;
     // and the Java Color is ...
     Color color = new Color(red,green,blue);
     */
  }
}
