/* [DisplayGrid.java]
 * A Small program for Display a 2D String Array graphically
 * @author Mangat
 */

// Graphics Imports
import javax.swing.*;
import java.awt.*;


class DisplayGridC { 

  private JFrame frame;
  private int maxX,maxY, GridToScreenRatio;
  private int[][] world;
  private int[] fireLine = {40,0,70,70};
  private int cycles;
  
  DisplayGridC(int[][] w) { 
    this.world = w;
    this.cycles = -1;

    
    maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    GridToScreenRatio = maxY / (world.length+1);  //ratio to fit in screen as square map
    
    System.out.println("Map size: "+world.length+" by "+world[0].length + "\nScreen size: "+ maxX +"x"+maxY+ " Ratio: " + GridToScreenRatio);
    
    this.frame = new JFrame("Test Chamber");
    
    GridAreaPanel worldPanel = new GridAreaPanel();
    
    frame.getContentPane().add(BorderLayout.CENTER, worldPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    frame.setVisible(true);
  }
  
  
  public void refresh() { 
    frame.repaint();
  }
  
  public void setLine(int r1,int d1,int r2,int d2) {
    fireLine[0] = r1;
    fireLine[1] = d1;
    fireLine[2] = r2;
    fireLine[3] = d2;
  }
  
  public int returnLim() {
    return Toolkit.getDefaultToolkit().getScreenSize().height;
  }
    
  public void seeRatio() {
    System.out.println(GridToScreenRatio);
  }
  
  class GridAreaPanel extends JPanel {
    public void paintComponent(Graphics g) {        
      //super.repaint();
      //Color D_GREEN = new Color(57,118,40);
      
      setDoubleBuffered(true); 
      g.setColor(Color.BLACK);
      g.drawString(Integer.toString(GridToScreenRatio),GridToScreenRatio*30,GridToScreenRatio*24);
      
      for(int i = 0; i<world[0].length;i=i+1)
      { 
        for(int j = 0; j<world.length;j=j+1) 
        {           
          g.setColor(Color.BLACK);
          g.drawRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
        }
      }
      
      g.setColor(Color.RED);
      g.drawLine(fireLine[0],fireLine[1],fireLine[2],fireLine[3]);
      
      
    }
  }//end of GridAreaPanel
  
} //end of DisplayGrid

