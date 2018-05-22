import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;

import java.awt.event.MouseWheelListener;

class GamePanel extends JLayeredPane implements MouseWheelListener{
  private int xVal;
  private int length =500;
  JTextArea textArea = new JTextArea();
  JScrollPane scrollPane = new JScrollPane(textArea);
  private int yVal;
  private Color currentCol;
  GamePanel(int xVal, int yVal, Color currentCol){
    setFocusable(true);
    this.xVal = xVal;
    this.yVal = yVal;
    this.currentCol = currentCol;
    Dimension gamePanelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(gamePanelSize);
    textArea.setEditable(false);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setPreferredSize(new Dimension(1000, 1000));
    this.add(scrollPane);
    addMouseWheelListener(this);
  }public void mouseWheelMoved(MouseWheelEvent e) {
    int notches = e.getWheelRotation();
    if (notches<0){
      length = length+5;
    }else if (notches>0){
      length = length-5;
    }
  }  
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    g.setColor (currentCol);
    g.fillRect (0,0, xVal,yVal);
    g.setColor (Color.BLUE);
    g.fillRect (200,200,length,length);
  }
  public void animate(){
    this.repaint();
  }
}