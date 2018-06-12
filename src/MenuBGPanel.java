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
  private int pixelSize;
  private int starCount;
  Random r = new Random();
  MenuBGPanel(int xVal, int yVal){
    this.setFocusable(true);
    this.xVal = xVal;
    this.yVal = yVal;
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
    this.setLayout(null);
  }
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Image menuBg = Toolkit.getDefaultToolkit().getImage("../res/bg.png");
    g.drawImage(menuBg,0,0,xVal,yVal,this);
    pixelSize = xVal/200;
    
    starCount = r.nextInt(100) + 50; //50 - 100 stars
    g.setColor(Color.GRAY);
    for (int i = 0; i <= starCount; i ++){
      g.fillRect(r.nextInt(200)*pixelSize, r.nextInt(100)*pixelSize, pixelSize, pixelSize);
    }
    //System.out.println(Integer.toString(xVal/200) + " " + Integer.toString(yVal/100));
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
