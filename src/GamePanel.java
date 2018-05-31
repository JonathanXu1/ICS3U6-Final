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
  private Image left, right, middle, exp, hp;
  private Image hotbar;
  private Image mapBorder;
  private Tile[][] map;
  private int maxX= 0;
  private int maxY= 0;
  private int minimapX, minimapY, minimapArrayX, minimapArrayY; //minimapX = minimapY, may remove one later
  private int minimapFactor = 20;
  private boolean minimapUp = false, minimapDown = false;
  private boolean newFloor = true;
  private int tileSize= 100;
  private int playerStartingX;
  private int playerStartingY;
  private Character player = new Character();
  /////Set up the character coordinates on the map
  private boolean [] blocked = new boolean [4];
  GamePanel(){
    setFocusable(true);
    Background.setTileSize(tileSize);
    player.setArrayX (playerStartingX);
    player.setArrayY (playerStartingY);
    this.left = Toolkit.getDefaultToolkit().getImage("../res/MetalL.png");
    this.right = Toolkit.getDefaultToolkit().getImage("../res/MetalR.png");
    this.middle = Toolkit.getDefaultToolkit().getImage("../res/MetalM.png");
    this.exp = Toolkit.getDefaultToolkit().getImage("../res/ExpBar.png");
    this.hp = Toolkit.getDefaultToolkit().getImage("../res/HpBar.png");
    this.hotbar = Toolkit.getDefaultToolkit().getImage("../res/Hotbar.png");
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
      this.minimapX = 250;
      this.minimapY = 250;
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
  public void createMap(Tile [][]map, int playerStartingX, int playerStartingY){
    this.map = map;
    this.playerStartingX =playerStartingX;
    this.playerStartingY =playerStartingY;
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
    //Always set to 250 pixels in height
    g.drawImage(left,0,maxY-250,(int)(250.0*(110.0/75.0)),250,this);
    g.drawImage(middle,(int)((250.0)*110.0/75.0),maxY-250,maxX -(int)(250.0*110.0/75.0*2.0)+5, 250,this);
    g.drawImage(right,maxX-(int)(250.0*110.0/75.0),maxY-250, (int)(250.0*110.0/75.0), 250,this);
    g.drawImage(hotbar,(int)(maxX/2.0-(250.0*208.0/(75.0*2.0))),maxY-250,(int)(250.0*208.0/75.0), 250,this);
    //Hp and exp bars
    g.drawImage(hp,10,10, ((int)(maxX*1.0/5.0)),  ((int)(maxX*1.0/5.0/200.0*14.0)),this);
    g.drawImage(exp,10,15+ ((int)(maxX*1.0/5.0/200.0*14.0)),((int)(maxX*1.0/5.0)), ((int)(maxX*1.0/5.0/200.0*10.0)),this);
  }
  public void drawMap (Graphics g){
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, maxX, maxY);
    Background.setOnTile();
    if (Background.getOnTile()){
      player.setArrayY(playerStartingY+Background.getY()/tileSize);
      player.setArrayX(playerStartingX+Background.getX()/tileSize);
      findBlocked ();
      for(int i = -4; i < 5; i++){
        for(int j = -4; j < 5; j++){
          if (!((Math.abs(j-i)==8)||(Math.abs(j-i)==7)||(Math.abs(j-i)==6)||(Math.abs(i+j)==8)||(Math.abs(i+j)==7)||(Math.abs(i+j)==6))){
            if((player.getArrayY()+i>=0)&&(player.getArrayY()+j<=map.length)&&(player.getArrayX()+i>=0)&&(player.getArrayX()+j<=map[0].length)){
              map[player.getArrayY() + i][player.getArrayX() + j].setViewed();
            }
          }
          }
      }
    }
    if ((!(blocked[0])&&(Background.getYDirection()<0))||(!(blocked[1])&&(Background.getYDirection()>0))||(!(blocked[2])&&(Background.getXDirection()<0))||(!(blocked[3])&&(Background.getXDirection()>0))){
      Background.move();
    }
    for (int i = 0;i<map.length;i++){
      for (int j = 0;j<map[0].length;j++){
        ///The 10 and 7 are initial positions
        //Getting the x and y for the background allow the ability to have smooth movement when going from one tile to the next
        if(map[i][j].getViewed()){
          if (((maxX/2+j*tileSize-Background.getX()-(tileSize/2)-(tileSize*playerStartingX))>-tileSize*2)&&((maxX/2+j*tileSize-Background.getX()-(tileSize/2)-(tileSize*playerStartingX))<maxX+tileSize*2)&&((maxY/2+i*tileSize-Background.getY()-(tileSize/2)-(tileSize*playerStartingY))>-tileSize*2)&&((maxY/2+i*tileSize-Background.getY()-(tileSize/2)-(tileSize*playerStartingY))<maxY+tileSize*2)){
            if (!(map[i][j] instanceof VoidTile)){
              map[i][j].drawTile(g, maxX/2+j*tileSize-Background.getX()-(tileSize/2)-(tileSize*playerStartingX), maxY/2+i*tileSize-Background.getY()-(tileSize/2)-(tileSize*playerStartingY), tileSize, tileSize, this);
            }            
          }
        }
      }
    }
  }
  public void drawMinimap(Graphics g){ //Trying to figure out how to only activate once when clicked
    g.setColor(Color.BLACK);
    g.fillRect(0,maxY-250,minimapX, minimapY);    
    //User clicks zoom in and out buttons
    if((mouseXy[0] > 253)&&(mouseXy[0] < 253+34)&&(mouseXy[1] > maxY-240)&&(mouseXy[1] < maxY-240+110)&&(mousePressed)&&(!minimapUp)&&(minimapFactor > 20)){ //Clicked on top button
      minimapFactor -= 10;
      minimapUp = true;
      minimapDown = false;
    } else if((mouseXy[0] > 253)&&(mouseXy[0] < 253+34)&&(mouseXy[1] > maxY-120)&&(mouseXy[1] < maxY-120+110)&&(mousePressed)&&(!minimapDown)&&(minimapFactor < 100)){ //Clicked on bottom button
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
    double miniTileSize = ((double)minimapX)/minimapFactor;
    for(int i = 0; i < minimapFactor; i++){
      for(int j = 0; j < minimapFactor; j++){
        minimapArrayY = player.getArrayY() + i - minimapFactor/2;
        minimapArrayX = player.getArrayX() + j - minimapFactor/2;
        if ((minimapArrayY>0)&&(minimapArrayY<map.length)&&(minimapArrayX>0)&&(minimapArrayX<map[0].length)){ //If tiles are in view window
          if (!(map[minimapArrayY][minimapArrayX] instanceof VoidTile)){ //If not void tile
            if(debugState){ //If debug state
              g.setColor(map[minimapArrayY][minimapArrayX].getMinimapColor());
              g.fillRect((int)Math.round(j*miniTileSize), (maxY-240)+ (int)Math.round(i*miniTileSize), (int)Math.ceil(miniTileSize), (int)Math.ceil(miniTileSize));
            } else if(map[minimapArrayY][minimapArrayX].getViewed()){ //If not debug state
              g.setColor(map[minimapArrayY][minimapArrayX].getMinimapColor());
              g.fillRect((int)Math.round(j*miniTileSize), (maxY-240)+ (int)Math.round(i*miniTileSize), (int)Math.ceil(miniTileSize), (int)Math.ceil(miniTileSize));
            }
          }
        }
        if ((minimapArrayY==player.getArrayY())&&(minimapArrayX==player.getArrayX())){
          g.setColor(Color.BLUE);
          g.fillRect((int)Math.round(j*miniTileSize), (maxY-240)+ (int)Math.round(i*miniTileSize), (int)Math.ceil(miniTileSize), (int)Math.ceil(miniTileSize)); //Character square
        }
      }
    }
    //Draws buttons for zoom in and out
    //Draws the frame
    g.drawImage(mapBorder,0,maxY-250,minimapX, minimapY,this);
  }
  public void drawCharacter(Graphics g){
    g.setColor(Color.BLUE);
    g.fillRect (maxX/2-(tileSize/2),maxY/2-(tileSize/2),tileSize, tileSize);
  }
  public void findBlocked(){
    if (player.getArrayY()-1>=0){
      if (map[player.getArrayY()-1][player.getArrayX()]  instanceof WalkableTile){
        blocked[0] = false;
      }else{
        blocked[0] = true;
      }
    }else{
      blocked[0] = true;
    }
    if (player.getArrayY()+1<=map.length){
      if (map[player.getArrayY()+1][player.getArrayX()]  instanceof WalkableTile){
        blocked[1] = false;
      }else{
        blocked[1] = true;
      }
    }else{
      blocked[1] = true;
    }
    if (player.getArrayX()-1>=0){
      if (map[player.getArrayY()][player.getArrayX()-1]  instanceof WalkableTile){
        blocked[2] = false;
      }else{
        blocked[2] = true;
      }
    }else{
      blocked[2] = true;
    }
    if (player.getArrayX()+1<=map[0].length){
      if (map[player.getArrayY()][player.getArrayX()+1]  instanceof WalkableTile){
        blocked[3] = false;
      }else{
        blocked[3] = true;
      }
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