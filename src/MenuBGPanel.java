import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

class MenuBGPanel extends JPanel{
  private int xVal;
  private int yVal;
  private int pixelX, pixelY;
  private int starCount;
  private Image menuBg = Toolkit.getDefaultToolkit().getImage("../res/bg.png");
  private Star[][] stars = new Star[100][200];
  private int count = 0;
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
    count ++;
    super.paintComponent(g);
    g.drawImage(menuBg,0,0,xVal,yVal,this);
    if(count >= 10){ //Display count for buffering animations
      count = 0; 
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
  }
  public void refresh(){
    this.repaint();
  }
}
