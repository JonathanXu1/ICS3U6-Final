import javax.swing.*;
import java.awt.*;

class GamePanel extends JPanel{
  private int maxX;
  private int maxY;
  private boolean debugState;
  private int stringLength;
  private String fps;
  private int [] mouseXy;
  private double totalMem, memUsed, memPercent;
  private Font menuFont = new Font("Courier New", Font.PLAIN, 20);
  private Tile[][] map;
  private boolean newFloor = true;
  private int tileSize= 100;
  private int playerXInitial = 10;
  private int playerYInitial = 7;
  /////Set up the character coordinates on the map
  private boolean [] blocked = new boolean [4];
  GamePanel(int maxX, int maxY){
    setFocusable(true);
    this.maxX= maxX;
    this.maxY = maxY;
    Dimension panelSize= new Dimension (maxX, maxY);
    this.setPreferredSize(panelSize);
    Background.setTileSize(tileSize);
    Character.setArrayX (playerXInitial);
    Character.setArrayY (playerYInitial);
  }
  public void setDebugInfo(boolean debugState, int fps, double totalMem, double memUsed, int[] mouseXy){
    this.debugState = debugState;
    this.fps = Integer.toString(fps);
    this.totalMem = totalMem;
    this.memUsed = memUsed;
    this.mouseXy = mouseXy;
    memPercent = (memUsed/totalMem)*100;
  }
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Background.setTileSize(tileSize);
    //Draw the map
    drawMap(g);
    //Draw the game components
    drawGameComponents(g);
    //Draw the character
    drawCharacter (g);
    //Draw the debugPanel
    if(debugState){
      drawDebugPanel(g);
    }
    this.setVisible(true);
  }
  public void refresh(){
    this.repaint();
  }
  public boolean getNewFloor(){
    return (newFloor);
  }
  public void setNewFloor(boolean newFloor){
    this.newFloor = newFloor;
  }
  public void createMap(Tile [][]map){
    this.map = map;
  }
  public void drawDebugPanel (Graphics g){
    g.setColor(new Color(80, 80, 80, 127)); //Translucent grey
    stringLength = ("FPS: "+fps).length();
    g.fillRect(30, 15, 12*stringLength, 20);
    stringLength = ("Memory Usage: " + String.format("%.2f", memPercent) + "% (" + String.format("%.2f", memUsed) + "MB out of " + String.format("%.2f", totalMem) + "MB)").length();
    g.fillRect(maxX - 600, 15, 12*stringLength, 20);
    stringLength = ("Mouse Click: " + Integer.toString(mouseXy[0]) + "x " + Integer.toString(mouseXy[1])  + " y").length();
    g.fillRect(maxX-300, 45, 12*stringLength, 20);
    g.setFont (menuFont);
    g.setColor(Color.WHITE);
    g.drawString("FPS: " + fps, 30, 30);
    g.drawString("Memory Usage: " + String.format("%.2f", memPercent) + "% (" + String.format("%.2f", memUsed) + "MB out of " + String.format("%.2f", totalMem) + "MB)", maxX-600, 30);
    g.drawString("Mouse Click: " + Integer.toString(mouseXy[0]) + "x " + Integer.toString(mouseXy[1]) + " y", maxX-300, 60);
  }
  public void drawGameComponents(Graphics g){
    Image left = Toolkit.getDefaultToolkit().getImage("../res/MetalL.png");
    Image right = Toolkit.getDefaultToolkit().getImage("../res/MetalR.png");
    Image middle = Toolkit.getDefaultToolkit().getImage("../res/MetalM.png");
    g.drawImage(left,0,maxY-(int)(maxY*2.5/10.0),(int)((int)(maxY*2.5/10.0)*208.0/87.0),(int)(maxY*2.5/10.0),this);
    g.drawImage(middle,(int)((int)(maxY*2.5/10.0)*208.0/87.0),maxY-(int)(maxY*2.5/10.0),maxX -(int)((int)(maxY*2.5/10.0)*208.0/87.0*2.0)+5, (int)(maxY*2.5/10.0),this);
    g.drawImage(right,maxX-(int)((int)(maxY*2.5/10.0)*208.0/87.0),maxY-(int)(maxY*2.5/10.0), (int)((int)(maxY*2.5/10.0)*208.0/87.0), (int)(maxY*2.5/10.0),this);
    //Continue
    Image map = Toolkit.getDefaultToolkit().getImage("../res/Map.png");
    g.drawImage(map,maxX-(int)(maxX*1.0/5.0),0,(int)(maxX*1.0/5.0), (int)(maxX*1.0/5.0),this);
    Image hp = Toolkit.getDefaultToolkit().getImage("../res/HpBar.png");
    g.setColor (new Color (69,218,215));
    g.drawImage(hp,10,10, ((int)(maxX*1.0/5.0)),  ((int)(maxX*1.0/5.0/200.0*14.0)),this);
    g.fillRect (16,16, ((int)(maxX*1.0/5.0))-12, ((int)(maxX*1.0/5.0/200.0*14.0))-12);
    Image exp = Toolkit.getDefaultToolkit().getImage("../res/ExpBar.png");
    g.setColor (new Color (152,251,152));
    g.drawImage(exp,10,15+ ((int)(maxX*1.0/5.0/200.0*14.0)),((int)(maxX*1.0/5.0)), ((int)(maxX*1.0/5.0/200.0*10.0)),this);
    g.fillRect (16,21+((int)(maxX*1.0/5.0/200.0*14.0)), ((int)(maxX*1.0/5.0))-12,((int)(maxX*1.0/5.0/200.0*10.0))-12);
  }
  public void drawMap (Graphics g){
    Background.setOnTile();
    if (Background.getOnTile()){
      Character.setArrayY(playerYInitial+Background.getY()/tileSize);
      Character.setArrayX(playerXInitial+Background.getX()/tileSize);
      findBlocked ();
    }
    if ((!(blocked[0])&&(Background.getYDirection()<0))||(!(blocked[1])&&(Background.getYDirection()>0))||(!(blocked[2])&&(Background.getXDirection()<0))||(!(blocked[3])&&(Background.getXDirection()>0))){
      Background.move();
    }
    for (int i = 0;i<map.length;i++){
      for (int j=0;j<map[0].length;j++){
        ///The 10 and 7 are initial positions
        //Getting the x and y for the background allow the ability to have smooth movement when going from one tile to the next
        map[i][j].drawTile(g, maxX/2+j*tileSize-Background.getX()-(tileSize/2)-(tileSize*playerXInitial), maxY/2+i*tileSize-Background.getY()-(tileSize/2)-(tileSize*playerYInitial), tileSize, tileSize, this);
        //g.fillRect (maxX/2+j*tileSize-Background.getX()-(tileSize/2)-(tileSize*playerXInitial), maxY/2+i*tileSize-Background.getY()-(tileSize/2)-(tileSize*playerYInitial), tileSize,tileSize);
      }
    }
  }
  public void drawCharacter(Graphics g){
    g.setColor(Color.BLUE);
    g.fillRect (maxX/2-(tileSize/2),maxY/2-(tileSize/2),tileSize, tileSize);
  }
  public void findBlocked(){
    if (map[Character.getArrayY()-1][Character.getArrayX()]  instanceof WalkableTile){
      blocked[0] = false;
    }else{
      blocked[0] = true;
    }
    if (map[Character.getArrayY()+1][Character.getArrayX()]  instanceof WalkableTile){
      blocked[1] = false;
    }else{
      blocked[1] = true;
    }
    if (map[Character.getArrayY()][Character.getArrayX()-1]  instanceof WalkableTile){
      blocked[2] = false;
    }else{
      blocked[2] = true;
    }
    if (map[Character.getArrayY()][Character.getArrayX()+1]  instanceof WalkableTile){
      blocked[3] = false;
    }else{
      blocked[3] = true;
    }
  }
}