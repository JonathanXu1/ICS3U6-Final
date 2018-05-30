import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

class GamePanel extends JPanel{
  private boolean debugState;
  private int stringLength;
  private String fps;
  private int [] mouseXy;
  private boolean mousePressed, mouseHover;
  private double totalMem, memUsed, memPercent;
  private String debugMessage = "NULL";
  private Font menuFont = new Font("Courier New", Font.PLAIN, 20);
  private Image left;
  private Image right;
  private Image middle;
  private Image exp;
  private Image hp;
  private Image mapBorder;
  private Tile[][] map;
  private int maxX= 0;
  private int maxY= 0;
  private int minimapX, minimapY, minimapArrayX, miniTileSize, minimapArrayY; //minimapX = minimapY, may remove one later
  private int minimapFactor = 20;
  private boolean minimapUp = false, minimapDown = false;
  private boolean newFloor = true;
  private int tileSize= 100;
  private int playerXInitial = Character.getArrayX();
  private int playerYInitial = Character.getArrayY();
  /////Set up the character coordinates on the map
  private boolean [] blocked = new boolean [4];
  GamePanel(){
    setFocusable(true);
    Background.setTileSize(tileSize);
    Character.setArrayX (playerXInitial);
    Character.setArrayY (playerYInitial);
    this.left = Toolkit.getDefaultToolkit().getImage("../res/MetalL.png");
    this.right = Toolkit.getDefaultToolkit().getImage("../res/MetalR.png");
    this.middle = Toolkit.getDefaultToolkit().getImage("../res/MetalM.png");
    this.exp = Toolkit.getDefaultToolkit().getImage("../res/ExpBar.png");
    this.hp = Toolkit.getDefaultToolkit().getImage("../res/HpBar.png");
    this.mapBorder = Toolkit.getDefaultToolkit().getImage("../res/MapNoBorder.png"); //duplicate name
  }
  public void setDebugInfo(boolean debugState, int fps, double totalMem, double memUsed, CustomMouseListener mouse){
    this.debugState = debugState;
    this.fps = Integer.toString(fps);
    this.totalMem = totalMem;
    this.memUsed = memUsed;
    this.mouseXy = mouse.getMouseXy();
    this.mousePressed = mouse.getPressed();
    this.mouseHover = mouse.getHover();
    memPercent = (memUsed/totalMem)*100;
  }
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    if (maxX==0){
      this.setPreferredSize(this.getSize());
      this.maxX= (int)this.getSize().getWidth();
      this.maxY =(int)this.getSize().getHeight();
      this.minimapX = 350;
      this.minimapY = 350;
    }
    Background.setTileSize(tileSize);
    //Draw the map
    drawMap(g);
    //Draw the game components
    drawGameComponents(g);
    //Draws the minimap
    drawMinimap(g);
    //Draw the health and exp
    drawBars(g);
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
    stringLength = ("Mouse Click: " + String.valueOf(mousePressed) + " " + Integer.toString(mouseXy[0]) + "x " + Integer.toString(mouseXy[1])  + " y").length();
    g.fillRect(maxX-300, 45, 12*stringLength, 20);
    stringLength = ("Debug Message: " + debugMessage).length();
    g.fillRect(maxX-600, 75, 12*stringLength, 20);
    g.setColor(Color.WHITE);
    g.setFont (menuFont);
    g.drawString("FPS: " + fps, 30, 30);
    g.drawString("Memory Usage: " + String.format("%.2f", memPercent) + "% (" + String.format("%.2f", memUsed) + "MB out of " + String.format("%.2f", totalMem) + "MB)", maxX-600, 30);
    g.drawString("Mouse Click: " + String.valueOf(mousePressed) + " " + Integer.toString(mouseXy[0]) + "x " + Integer.toString(mouseXy[1]) + " y", maxX-300, 60);
    g.drawString("Debug Message: " + debugMessage, maxX-600, 90);
  }
  public void drawGameComponents(Graphics g){
    //Bottom toolbar
    //Always set to 270 pixels in height
    g.drawImage(left,0,maxY-350,(int)(350.0*(110.0/75.0)),350,this);
    g.drawImage(middle,(int)((350.0)*110.0/75.0),maxY-350,maxX -(int)(350.0*110.0/75.0*2.0)+5, 350,this);
    g.drawImage(right,maxX-(int)(350.0*110.0/75.0),maxY-350, (int)(350.0*110.0/75.0), 350,this);
    //Hp and exp bars
    g.drawImage(hp,10,10, ((int)(maxX*1.0/5.0)),  ((int)(maxX*1.0/5.0/200.0*14.0)),this);
    g.drawImage(exp,10,15+ ((int)(maxX*1.0/5.0/200.0*14.0)),((int)(maxX*1.0/5.0)), ((int)(maxX*1.0/5.0/200.0*10.0)),this);

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
      for (int j = 0;j<map[0].length;j++){
        ///The 10 and 7 are initial positions
        //Getting the x and y for the background allow the ability to have smooth movement when going from one tile to the next
        map[i][j].drawTile(g, maxX/2+j*tileSize-Background.getX()-(tileSize/2)-(tileSize*playerXInitial), maxY/2+i*tileSize-Background.getY()-(tileSize/2)-(tileSize*playerYInitial), tileSize, tileSize, this);
        //g.fillRect (maxX/2+j*tileSize-Background.getX()-(tileSize/2)-(tileSize*playerXInitial), maxY/2+i*tileSize-Background.getY()-(tileSize/2)-(tileSize*playerYInitial), tileSize,tileSize);
      }
    }
  }
  public void drawMinimap(Graphics g){ //Trying to figure out how to only activate once when clicked
    //Minimap bg
    g.setColor(Color.BLACK);
    g.fillRect(maxX-(int)(maxX*1.0/5.0),0,minimapX, minimapY);
    //User clicks zoom in and out buttons
<<<<<<< HEAD
    if((mouseXy[0] > 360)&&(mouseXy[0] < 360+43)&&(mouseXy[1] > maxY-333)&&(mouseXy[1] < maxY-333+150)&&(mouseXy[2] == 1)&&(!minimapUp)&&(minimapFactor > 20)){ //Clicked on top button
      minimapFactor -= 10;
      minimapUp = true;
      minimapDown = false;
    } else if((mouseXy[0] > 360)&&(mouseXy[0] < 360+43)&&(mouseXy[1] > maxY-170)&&(mouseXy[1] < maxY-170+150)&&(mouseXy[2] == 1)&&(!minimapDown)&&(minimapFactor < 100)){ //Clicked on bottom button
      minimapFactor += 10;
      minimapDown = true;
      minimapUp = false;
    }
    if(!mousePressed){
      minimapDown = false;
      minimapUp = false;
    }

    debugMessage = "Minimap factor: " + Integer.toString(minimapFactor) + String.valueOf(minimapUp);

    //Draws minimap contents
    miniTileSize = minimapX/minimapFactor;
    for(int i = 0; i < minimapFactor; i++){
      for(int j = 0; j < minimapFactor; j++){
        minimapArrayY = Character.getArrayY() + i - minimapFactor/2;
        minimapArrayX = Character.getArrayX() + j - minimapFactor/2;
        if((minimapArrayY < map.length) && (minimapArrayY >= 0) && (minimapArrayX < map[0].length) && (minimapArrayX >= 0)){
          g.setColor(map[minimapArrayY][minimapArrayX].getMinimapColor());
          g.fillRect(j*miniTileSize, (maxY-340)+i*miniTileSize, miniTileSize, miniTileSize);
        }
      }
    }
    //Draws buttons for zoom in and out
    g.setColor(Color.BLUE);
    g.fillRect(360, maxY-333, 43, 150);
    g.fillRect(360, maxY-170, 43, 150);
    g.setColor(Color.RED);
    g.fillRect((minimapFactor/2)*miniTileSize, maxY-(minimapFactor/2)* miniTileSize, miniTileSize, miniTileSize); //Character square
    //Draws the frame    
    g.drawImage(mapBorder,0,maxY-350,minimapX, minimapY,this);
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
  public void drawBars(Graphics g){
    //Fill Hp
    g.setColor (new Color (69,218,215));
    g.fillRect (16,16, ((int)(maxX*1.0/5.0))-12, ((int)(maxX*1.0/5.0/200.0*14.0))-12);
    //Fill Exp
    g.setColor (new Color (152,251,152));
    g.fillRect (16,21+((int)(maxX*1.0/5.0/200.0*14.0)), ((int)(maxX*1.0/5.0))-12,((int)(maxX*1.0/5.0/200.0*10.0))-12);
  }
}