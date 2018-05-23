import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

class GamePanel extends JLayeredPane implements MouseWheelListener{
  private int length =500;
  private int xVal;
  private int yVal;
  JTextArea textArea = new JTextArea();
  JScrollPane scrollPane = new JScrollPane(textArea);
  GamePanel(int xVal, int yVal){
    setFocusable(true);
    this.xVal = xVal;
    this.yVal = yVal;
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
    textArea.setEditable(false);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setPreferredSize(new Dimension(1000, 1000));
    this.add(scrollPane);
    addMouseWheelListener(this);
  }
  public void paintComponent(Graphics g){
    g.setColor (Color.BLACK);
    g.fillRect (0,0, xVal,yVal);
    g.setColor (Color.BLUE);
    g.fillRect (100,100, length,length);
  }
  public void refresh(){
    this.repaint();
  }
  public void mouseWheelMoved(MouseWheelEvent e) {
    int notches = e.getWheelRotation();
    if (notches<0){
      length = length+5;
    }else if (notches>0){
      length = length-5;
    }
  }
}