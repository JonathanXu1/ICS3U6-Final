import javax.swing.*;
import java.awt.*;
import java.io.File;

class GamePanel extends JPanel{
  private boolean debugState;
  private int stringLength;
  private String fps;
  private int [] mouseXy;
  private int xMove;
  private int yMove;
  private int direction=0;
  private double totalMem, memUsed, memPercent;
  private String debugMessage = "NULL";
  
  private Font menuFont = new Font("Courier New", Font.PLAIN, 20);
  
  private Tile[][] map;
  private int maxX, maxY, minimapX, minimapY;
  private int minimapFactor = 10;
  private boolean minimapUp = false, minimapDown = false;
  private boolean newFloor = true;
  private int tileSize= 100;
  private Background bg = new Background(tileSize);
  private int playerXInitial = 10;
  private int playerYInitial = 7;
  private Character player = new Character (playerXInitial,playerYInitial, tileSize);
  /////Set up the character coordinates on the map
  private boolean [] blocked = new boolean [4];
  GamePanel(int maxX, int maxY){
    setFocusable(true);
    this.maxX= maxX;
    this.maxY = maxY;
    Dimension panelSize= new Dimension (maxX, maxY);
    this.setPreferredSize(panelSize);
    this.minimapX = (int)(maxX*1.0/5.0);
    this.minimapY = (int)(maxX*1.0/5.0);
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
    player.setTileSize(tileSize);
    bg.setTileSize(tileSize);
    //Draw the map
    drawMap(g);
    drawMinimap(g);
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
  public void setDirection (int direction){
    this.direction = direction;
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
    stringLength = ("Mouse Click: " + mouseXy[2] + " " + Integer.toString(mouseXy[0]) + "x " + Integer.toString(mouseXy[1])  + " y").length();
    g.fillRect(maxX-300, 45, 12*stringLength, 20);
    stringLength = ("Debug Message: " + debugMessage).length();
    g.fillRect(maxX-600, 75, 12*stringLength, 20);
    
    g.setColor(Color.WHITE);
    g.setFont (menuFont);
    g.drawString("FPS: " + fps, 30, 30);
    g.drawString("Memory Usage: " + String.format("%.2f", memPercent) + "% (" + String.format("%.2f", memUsed) + "MB out of " + String.format("%.2f", totalMem) + "MB)", maxX-600, 30);
    g.drawString("Mouse Click: " + mouseXy[2] + " " + Integer.toString(mouseXy[0]) + "x " + Integer.toString(mouseXy[1]) + " y", maxX-300, 60);
    g.drawString("Debug Message: " + debugMessage, maxX-600, 90);
  }
  public void drawGameComponents(Graphics g){
    //Bottom toolbar
    Image left = Toolkit.getDefaultToolkit().getImage("../res/MetalL.png");
    Image right = Toolkit.getDefaultToolkit().getImage("../res/MetalR.png");
    Image middle = Toolkit.getDefaultToolkit().getImage("../res/MetalM.png");
    g.drawImage(left,0,maxY-(int)(maxY*2.5/10.0),(int)((int)(maxY*2.5/10.0)*208.0/87.0),(int)(maxY*2.5/10.0),this);
    g.drawImage(middle,(int)((int)(maxY*2.5/10.0)*208.0/87.0),maxY-(int)(maxY*2.5/10.0),maxX -(int)((int)(maxY*2.5/10.0)*208.0/87.0*2.0)+5, (int)(maxY*2.5/10.0),this);
    g.drawImage(right,maxX-(int)((int)(maxY*2.5/10.0)*208.0/87.0),maxY-(int)(maxY*2.5/10.0), (int)((int)(maxY*2.5/10.0)*208.0/87.0), (int)(maxY*2.5/10.0),this);
    //Minimap
    Image map = Toolkit.getDefaultToolkit().getImage("../res/MapNoBorder.png");
    g.drawImage(map,maxX-(int)(maxX*1.0/5.0),0,minimapX, minimapY,this);
    //Hp and exp bars
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
    bg.setOnTile();
    if (bg.getOnTile()){
      player.setArrayY(playerYInitial+bg.getY()/tileSize);
      player.setArrayX(playerXInitial+bg.getX()/tileSize);
      findBlocked ();
    }
    if ((!(blocked[0])&&(bg.getYDirection()<0))||(!(blocked[1])&&(bg.getYDirection()>0))||(!(blocked[2])&&(bg.getXDirection()<0))||(!(blocked[3])&&(bg.getXDirection()>0))){
      bg.move();
    }
    for (int i = 0;i<map.length;i++){
      for (int j=0;j<map[0].length;j++){
        g.setColor (map[i][j].getMinimapColor());
        ///The 10 and 7 are initial positions
        //Getting the x and y for the background allow the ability to have smooth movement when going from one tile to the next
        g.fillRect (maxX/2+j*tileSize-bg.getX()-(tileSize/2)-(tileSize*playerXInitial), maxY/2+i*tileSize-bg.getY()-(tileSize/2)-(tileSize*playerYInitial), tileSize,tileSize);
      }
    }
  }
  public void drawMinimap(Graphics g){ //Trying to figure out how to only activate once when clicked
    g.setColor(Color.RED);
    g.fillRect(maxX-(int)(maxX*1.0/5.0),0,minimapX, minimapY);
    g.setColor(Color.BLUE);
    g.fillRect(maxX-40, 50, 20, 20);
    g.fillRect(maxX-40, 75, 20, 20);
    if(mouseXy[0] > maxX-40 && mouseXy[0] < maxX-20 && mouseXy[1] > 50 && mouseXy[1] < 70 && mouseXy[2] == 1 && !minimapUp){ //Clicked on top button
      minimapFactor += 10;
      minimapUp = true;
      minimapDown = false;
    } else if(mouseXy[0] > maxX-40 && mouseXy[0] < maxX-20 && mouseXy[1] > 75 && mouseXy[1] < 95 && mouseXy[2] == 1 && !minimapDown){ //Clicked on bottom button
      minimapFactor -= 10;
      minimapDown = true;
      minimapUp = false;
    }
    if(mouseXy[2] == 0){
      minimapDown = false;
      minimapUp = false;
    }
    debugMessage = "Minimap factor: " + Integer.toString(minimapFactor);
    
  }
  public void drawCharacter(Graphics g){
    g.setColor(Color.BLUE);
    g.fillRect (maxX/2-(tileSize/2),maxY/2-(tileSize/2),tileSize, tileSize);
  }
  public void findBlocked(){
    if (map[player.getArrayY()-1][player.getArrayX()]  instanceof WalkableTile){
      blocked[0] = false;
    }else{
      blocked[0] = true;
    }
    if (map[player.getArrayY()+1][player.getArrayX()]  instanceof WalkableTile){
      blocked[1] = false;
    }else{
      blocked[1] = true;
    }
    if (map[player.getArrayY()][player.getArrayX()-1]  instanceof WalkableTile){
      blocked[2] = false;
    }else{
      blocked[2] = true;
    }
    if (map[player.getArrayY()][player.getArrayX()+1]  instanceof WalkableTile){
      blocked[3] = false;
    }else{
      blocked[3] = true;
    }
  }
}