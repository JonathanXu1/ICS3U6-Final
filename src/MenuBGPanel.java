//Add custom font
//Have better button highlight effects


import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

class MenuBGPanel extends JPanel{
  private int xVal;
  private int yVal;
  private double pixelX, pixelY;
  private int starCount;
  private Image bg = Toolkit.getDefaultToolkit().getImage("../res/Bg2.png");
  private Image station1 = Toolkit.getDefaultToolkit().getImage("../res/Station1.png");
  private Image station2 = Toolkit.getDefaultToolkit().getImage("../res/Station2.png");
  private Star[][] stars = new Star[50][100];
  private int count = 0;
  private int stationState = 1;
  private int stationCount = 0;
  Random rand = new Random();
  
  MenuBGPanel(int xVal, int yVal){
    this.setFocusable(true);
    this.xVal = xVal;
    this.yVal = yVal;
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
    this.setLayout(null);
    
    pixelX = xVal/100;
    pixelY = yVal/50; 
  }
  @Override
  public void paintComponent(Graphics g){
    count ++;
    stationCount ++;
    super.paintComponent(g);
    //Draws space gradient
    g.drawImage(bg,0,0,xVal,yVal,this);
    //Draws stars
    if(count >= 10){ //Display count for buffering animations
      count = 0; 
      starCount = 2; //# stars
      for (int i = 0; i <= starCount; i ++){
        int randX = rand.nextInt(100);
        int randY = rand.nextInt(50);
        if(stars[randY][randX] == null){
          stars[randY][randX] = new Star();
        }
      }
      for (int i = 0; i < stars.length; i ++){
        for (int j = 0; j < stars[0].length; j++){
          if(stars[i][j] != null){
            if(!stars[i][j].getDied()){
              stars[i][j].updateColor();
            }
          }
        }
      }
    }
    for (int i = 0; i < stars.length; i ++){
      for (int j = 0; j < stars[0].length; j++){
        if(stars[i][j] != null){
          if(!stars[i][j].getDied()){
            g.setColor(stars[i][j].getColor());
            g.fillRect((int)(j*pixelX), (int)(i*pixelY), (int)(pixelX), (int)(pixelY));
          } else {
            stars[i][j] = null;
          }
        }
      }
    }
    //Draws Station
    if(stationCount >= 200){
      stationCount = 0;
      if(stationState == 1){
        stationState = 2;
      }else if(stationState == 2){
        stationState = 1;
      } 
    } 
    if(stationState == 1){
      g.drawImage(station1,0,0,xVal,yVal,this);
    } else if(stationState == 2){
      g.drawImage(station2,0,0,xVal,yVal,this);
    }
    
  }
  public void refresh(){
    this.repaint();
  }
}
