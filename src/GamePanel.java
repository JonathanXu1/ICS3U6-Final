import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

class GamePanel extends JPanel{
  //Debug
  private int stringLength;
  private String fps;
  private Font menuFont = new Font("Courier New", Font.PLAIN, 20);
  private int [] mouseXy;
  private double totalMem, memUsed, memPercent;
  private String debugMessage = "NULL";
  
  //Listeners
  private boolean mouseClicked;
  private CustomKeyListener keyListener = new CustomKeyListener();
  private CustomMouseListener mouseListener = new CustomMouseListener();
  
  //Images
  private Image left, leftClickedPlus, leftClickedMinus, right, middle, exp, hp, hotbar,mapBorder;
  private final double Y_TO_X = 110.0/75.0;
  private final double Y_TO_X_HOT = 208.0/75.0;
  private final double BOT_HEIGHT = 250.0;
  
  //Sizes
  private int maxX= 0, maxY= 0;
  private int minimapFactor = 20;
  private final int TILE_SIZE= 100;
  
  //Tiling and maps
  private Tile[][] map;
  private int minimapX, minimapY, minimapArrayX, minimapArrayY; //minimapX = minimapY, may remove one later
  private boolean newFloor = true;
  private Background bg= new Background (TILE_SIZE);
  
  //Coordinate control
  private int playerStartingX, playerStartingY;
  private Character player;
  private boolean [] blocked = new boolean [4];
  private int [] xyDirection = new int [2];
  
  //Constructor
  GamePanel(){
    //Adds the listeners
    this.addKeyListener(keyListener);
    this.addMouseListener(mouseListener);
    //Initializes the player
    player = new Character(playerStartingX,playerStartingY);
    //Initializes images
    this.left = Toolkit.getDefaultToolkit().getImage("../res/MetalL.png");
    this.leftClickedPlus = Toolkit.getDefaultToolkit().getImage("../res/MetalC+.png");
    this.leftClickedMinus = Toolkit.getDefaultToolkit().getImage("../res/MetalC-.png");
    this.right = Toolkit.getDefaultToolkit().getImage("../res/MetalR.png");
    this.middle = Toolkit.getDefaultToolkit().getImage("../res/MetalM.png");
    this.exp = Toolkit.getDefaultToolkit().getImage("../res/ExpBar.png");
    this.hp = Toolkit.getDefaultToolkit().getImage("../res/HpBar.png");
    this.hotbar = Toolkit.getDefaultToolkit().getImage("../res/Hotbar.png");
    this.mapBorder = Toolkit.getDefaultToolkit().getImage("../res/MapNoBorder.png"); //duplicate name
    //Initializes minimap size
    this.minimapX = (int)(BOT_HEIGHT);
    this.minimapY = (int)(BOT_HEIGHT);
  }
  
  //Methods that are inherited from JPanel
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    //Required to have focus so that the listeners work
    this.requestFocusInWindow();
    setDoubleBuffered(true);
    if (maxX==0){
      this.maxX= (int)this.getSize().getWidth();
      this.maxY =(int)this.getSize().getHeight();
      this.setPreferredSize(this.getSize());
    }
    //Update the listeners
    mouseXy = mouseListener.getMouseXy();
    mouseClicked = mouseListener.getClicked();
    //Draw map (background)
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
    if (keyListener.getDebugState()){
      drawDebugPanel(g);
    }
    this.setVisible(true);
  }
  public void refresh(){
    this.repaint();
  }
  //End of methods that are inherited from JPanel

  //Drawing methods, which were split up for legibility
  public void drawDebugPanel (Graphics g){
    //Debug panel, for personal use and testing
    g.setColor(new Color(80, 80, 80, 127)); //Translucent grey
    stringLength = ("FPS: "+fps).length();
    g.fillRect(30, 15, 12*stringLength, 20);
    stringLength = ("Memory Usage: " + String.format("%.2f", memPercent) + "% (" + String.format("%.2f", memUsed) + "MB out of " + String.format("%.2f", totalMem) + "MB)").length();
    g.fillRect(maxX - 600, 15, 12*stringLength, 20);
    stringLength = ("Mouse Click: " + " " + Integer.toString(mouseXy[0]) + "x " + Integer.toString(mouseXy[1])  + " y").length();
    g.fillRect(maxX-300, 45, 12*stringLength, 20);
    stringLength = ("Debug Message: " + debugMessage).length();
    g.fillRect(maxX-600, 75, 12*stringLength, 20);
    g.setColor(Color.WHITE);
    g.setFont (menuFont);
    g.drawString("FPS: " + fps, 30, 30);
    g.drawString("Memory Usage: " + String.format("%.2f", memPercent) + "% (" + String.format("%.2f", memUsed) + "MB out of " + String.format("%.2f", totalMem) + "MB)", maxX-600, 30);
    g.drawString("Mouse Click: " + " " + Integer.toString(mouseXy[0]) + "x " + Integer.toString(mouseXy[1]) + " y", maxX-300, 60);
    g.drawString("Debug Message: " + debugMessage, maxX-600, 90);
  }
  public void drawGameComponents(Graphics g){
    //Bottom toolbar
    g.drawImage(left,0,maxY-(int)(BOT_HEIGHT),(int)(BOT_HEIGHT*Y_TO_X),(int)(BOT_HEIGHT),this);
    if (mouseListener.getAlternateButton()){
      if ((mouseListener.getMouseXy()[0] > 253)&&(mouseListener.getMouseXy()[0] < 287)&&(mouseListener.getMouseXy()[1] > maxY-240)&&(mouseListener.getMouseXy()[1] < maxY-130)){
        g.drawImage(leftClickedPlus,0,maxY-(int)(BOT_HEIGHT),(int)(BOT_HEIGHT*Y_TO_X),(int)(BOT_HEIGHT),this);
      }else if ((mouseListener.getMouseXy()[0] > 253)&&(mouseListener.getMouseXy()[0] < 287)&&(mouseListener.getMouseXy()[1] > maxY-120)&&(mouseListener.getMouseXy()[1] < maxY-10)){
        g.drawImage(leftClickedMinus,0,maxY-(int)(BOT_HEIGHT),(int)(BOT_HEIGHT*Y_TO_X),(int)(BOT_HEIGHT),this);
      }
    }
    g.drawImage(middle,(int)(BOT_HEIGHT*Y_TO_X),maxY-(int)(BOT_HEIGHT),maxX -(int)(2.0*BOT_HEIGHT*Y_TO_X)+5, (int)(BOT_HEIGHT),this);
    g.drawImage(right,maxX-(int)(BOT_HEIGHT*Y_TO_X),maxY-(int)(BOT_HEIGHT), (int)(BOT_HEIGHT*Y_TO_X), (int)(BOT_HEIGHT),this);
    g.drawImage(hotbar,(int)(maxX/2.0-(BOT_HEIGHT*(Y_TO_X_HOT/2.0))),maxY-(int)(BOT_HEIGHT),(int)(BOT_HEIGHT*Y_TO_X_HOT), (int)(BOT_HEIGHT),this);
    //Hp and exp bars
    g.drawImage(hp,10,10, ((int)(maxX*0.2)),  ((int)(maxX*0.2/200.0*14.0)),this);
    g.drawImage(exp,10,15+ ((int)(maxX*0.2/200.0*14.0)),((int)(maxX*0.2)), ((int)(maxX*0.2/200.0*10.0)),this);
  }
  public void drawMap (Graphics g){
    //Sets the void image
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, maxX, maxY);
    bg.setOnTile();
    if (bg.getOnTile()){
      keyListener.setAllDirection();
      player.setArrayY(playerStartingY+bg.getY()/TILE_SIZE);
      player.setArrayX(playerStartingX+bg.getX()/TILE_SIZE);
      findBlocked ();
      drawFog(player.getArrayX(), player.getArrayY(), 0);
    }
    xyDirection =keyListener.getAllDirection();
    bg.setXDirection (xyDirection[0]);
    bg.setYDirection (xyDirection[1]);
    //If no paths are blocked and the array does not go out of bounds
    if ((!(blocked[0])&&(bg.getYDirection()<0))||(!(blocked[1])&&(bg.getYDirection()>0))||(!(blocked[2])&&(bg.getXDirection()<0))||(!(blocked[3])&&(bg.getXDirection()>0))){
      bg.move();
    }
    for (int i = 0;i<map.length;i++){
      for (int j = 0;j<map[0].length;j++){
        ///The 10 and 7 are initial positions
        //Getting the x and y for the background allow the ability to have smooth movement when going from one tile to the next
        if(map[i][j].getViewed()){
          //Restricts the map so that the array will not go out of bounds
          if (((maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX))>-TILE_SIZE*2)&&((maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX))<maxX+TILE_SIZE*2)&&((maxY/2+i*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY))>-TILE_SIZE*2)&&((maxY/2+i*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY))<maxY+TILE_SIZE*2)){
            if (!(map[i][j] instanceof VoidTile)){
              map[i][j].drawTile(g, maxX/2+j*TILE_SIZE-bg.getX()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingX), maxY/2+i*TILE_SIZE-bg.getY()-(TILE_SIZE/2)-(TILE_SIZE*playerStartingY), TILE_SIZE, TILE_SIZE, this);
            }
          }
        }
      }
    }
  }
  public void drawFog(int x, int y, int count){
    map[y][x].setViewed();
    if(count <= 2){ //If within range
      for(int i = -1; i <= 1; i ++){
        for(int j = -1; j <= 1; j++){
          if(map[y][x].getMinimapColor() == Color.GREEN && map[y+i][x+j].getMinimapColor() != Color.WHITE){ //Avoids corner sight
            drawFog(x+j, y+i, count+1);
          } else if(map[y][x].getMinimapColor() == Color.WHITE && map[y+i][x+j].getMinimapColor() != Color.GREEN){
            drawFog(x+j, y+i, count+1);
          } else if(map[y][x].getMinimapColor() == Color.RED && map[player.getArrayY()][player.getArrayX()].getMinimapColor() == Color.RED && map[y+i][x+j].getMinimapColor() != Color.RED){
            drawFog(x+j, y+i, count+1);
          }
        }
      }
    }
    /*
    for(int i = -4; i < 5; i++){
      for(int j = -4; j < 5; j++){
        //Sets the view range in a circular shape
        if (!((Math.abs(j-i)==8)||(Math.abs(j-i)==7)||(Math.abs(j-i)==6)||(Math.abs(i+j)==8)||(Math.abs(i+j)==7)||(Math.abs(i+j)==6))){
          if((y+i>=0)&&(y+i<map.length)&&(x+j>=0)&&(x+j<map[0].length)){
            if(map[player.getArrayY()][player.getArrayX()].getMinimapColor() == Color.GREEN){
              if(map[y+i][x+j].getMinimapColor() == Color.GREEN || map[y+i][x+j].getMinimapColor() == Color.LIGHT_GRAY || map[y+i][x+j].getMinimapColor() == Color.RED){
                map[y + i][x + j].setViewed();
              }
            } else if(map[player.getArrayY()][player.getArrayX()].getMinimapColor() == Color.WHITE){
              if(map[y+i][x+j].getMinimapColor() == Color.WHITE || map[y+i][x+j].getMinimapColor() == Color.DARK_GRAY || map[y+i][x+j].getMinimapColor() == Color.RED){
                map[y + i][x + j].setViewed();
              }
            } else{
              map[y + i][x + j].setViewed();
            }
          }
        }
      }
    } */
  }
  public void drawMinimap(Graphics g){
    g.setColor(Color.BLACK);
    g.fillRect(0,maxY-(int)(BOT_HEIGHT),minimapX, minimapY);    
    //User clicks zoom in and out buttons
    if((mouseListener.getMouseXy()[0] > 253)&&(mouseListener.getMouseXy()[0] < 287)&&(mouseListener.getMouseXy()[1] > maxY-240)&&(mouseListener.getMouseXy()[1] < maxY-130)&&(mouseListener.getClicked())&&(minimapFactor > 20)){ //Clicked on top button
      mouseListener.setClicked (false);
      minimapFactor -= 10;
    } else if ((mouseListener.getMouseXy()[0] > 253)&&(mouseListener.getMouseXy()[0] < 287)&&(mouseListener.getMouseXy()[1] > maxY-120)&&(mouseListener.getMouseXy()[1] < maxY-10)&&(mouseListener.getClicked())&&(minimapFactor < 100)){ //Clicked on bottom button
      mouseListener.setClicked (false);
      minimapFactor += 10;
    }
    debugMessage = "Minimap factor: " + Integer.toString(minimapFactor);
    //Draws minimap contents
    //Must be a double to avoid rounding errors
    double miniTileSize = ((double)minimapX)/minimapFactor;
    for(int i = 0; i < minimapFactor; i++){
      for(int j = 0; j < minimapFactor; j++){
        //Sets the minimap based on the normal map relative to the player
        minimapArrayY = player.getArrayY() + i - minimapFactor/2;
        minimapArrayX = player.getArrayX() + j - minimapFactor/2;
        if ((minimapArrayY>0)&&(minimapArrayY<map.length)&&(minimapArrayX>0)&&(minimapArrayX<map[0].length)){ //If tiles are in view window
          if (!(map[minimapArrayY][minimapArrayX] instanceof VoidTile)){ //If not void tile, to remove unecessary drawing
            if(keyListener.getDebugState()){ //If debug state
              g.setColor(map[minimapArrayY][minimapArrayX].getMinimapColor());
              g.fillRect((int)Math.round(j*miniTileSize), (maxY-240)+ (int)Math.round(i*miniTileSize), (int)Math.ceil(miniTileSize), (int)Math.ceil(miniTileSize));
            }else if(map[minimapArrayY][minimapArrayX].getViewed()){ //If not debug state, to be able to see the map while testing
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
    //Draws the frame, placed last as it covers the minimap
    g.drawImage(mapBorder,0,maxY-(int)(BOT_HEIGHT),minimapX, minimapY,this);
  }
  public void drawCharacter(Graphics g){
    g.setColor(Color.BLUE);
    g.fillRect (maxX/2-(TILE_SIZE/2),maxY/2-(TILE_SIZE/2),TILE_SIZE, TILE_SIZE);
  }
  public void drawBars(Graphics g){
    //Fill Hp, can be modified through the width
    g.setColor (new Color (69,218,215));
    g.fillRect (16,16, ((int)(maxX*1.0/5.0))-12, ((int)(maxX*1.0/5.0/200.0*14.0))-12);
    //Fill Exp, can be modified through the width
    g.setColor (new Color (152,251,152));
    g.fillRect (16,21+((int)(maxX*1.0/5.0/200.0*14.0)), ((int)(maxX*1.0/5.0))-12,((int)(maxX*1.0/5.0/200.0*10.0))-12);
  }
  
  //Map manipulation
  //The first method blocks off all impossible paths so that it the player does not clip into walls
    public void findBlocked(){
    //Restrictions are placed before the start of the each if so that array out of bounds is avoided
    if (player.getArrayY()-1>=0){
      //If the tile is void, then it will not be drawn to save memory
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
    //Sets up the map so that setting the floor is easier as well
    public void createMap(Tile [][]map, int playerStartingX, int playerStartingY){
    //Initializes player locations
    this.map = map;
    this.playerStartingX =playerStartingX;
    this.playerStartingY =playerStartingY;
  }
    
  //Getters and setters
  //There is no getter for the following, as it only needs to be accessed from this class
  //Sets all the information for the debug panel
  public void setDebugInfo(int fps, double totalMem, double memUsed){
    this.fps = Integer.toString(fps);
    this.totalMem = totalMem;
    this.memUsed = memUsed;
    memPercent = (memUsed/totalMem)*100;
  }
  //Retrieves whether or not it is a new floor
  public boolean getNewFloor(){
    return (newFloor);
  }
  //Sends back if the floor is new or not; this is useful for understanding what code to run
  public void setNewFloor(boolean newFloor){
    this.newFloor = newFloor;
  }
}