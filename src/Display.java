import javax.swing.*;
import java.awt.*;

class Display extends JFrame{
  private DisplayPanel mediumPanel;
  private DisplayPanel botPanel;
  private DisplayPanel rightPanel;
  private DisplayPanel gamePanel;
  private DisplayPanel mapPanel;
  private DisplayPanel hpPanel;
  private DisplayPanel expPanel;
  private DisplayPanel accessoryPanel;
  private int polygon =0;
  private int maxX;
  private int maxY;
  Display(){
    super ("Concordia");
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.maxX= (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
    this.maxY = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
    this.setSize(maxX, maxY);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
  public void menu(){
    
  }
  public void start(){
    this.setLayout(new BorderLayout());
    polygon =3;
    botPanel = new DisplayPanel(maxX, (int)(maxY*2.0/10.0), Color.GRAY, polygon);
    polygon =0;
    gamePanel = new DisplayPanel(maxX, (int)(maxY*9.0/10.0), Color.WHITE, polygon);
    this.add (botPanel, BorderLayout.SOUTH);
    this.add (gamePanel, BorderLayout.NORTH);
    gamePanel.setLayout(new BorderLayout());
    polygon =0;
    mapPanel = new DisplayPanel((int)(maxX*1.0/5.0),(int)(maxX*1.0/5.0), Color.GRAY, polygon);
    rightPanel = new DisplayPanel((int)(maxX*1.5/5.0),(int)(maxY*1.0/20.0), Color.WHITE, polygon);
    gamePanel.add (mapPanel, BorderLayout.EAST);
    gamePanel.add (rightPanel, BorderLayout.WEST);
    rightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    polygon =1;
    hpPanel = new DisplayPanel((int)(maxX*1.5/5.0),(int)(maxY*1.0/20.0), Color.RED, polygon);
    expPanel = new DisplayPanel((int)(maxX*1.0/4.0),(int)(maxY*1.0/30.0), Color.BLUE, polygon);
    rightPanel.add (hpPanel);
    rightPanel.add (expPanel);
    gamePanel.animate();
    botPanel.animate();
    mapPanel.animate();
  }
}