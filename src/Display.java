import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

class Display extends JFrame{
  //Main game
  private ItemPanel itemPanel;
  private EmptyPanel rightPanel;
  private GamePanel gamePanel;
  private MapPanel mapPanel;
  private HpPanel hpPanel;
  private ExpPanel expPanel;
  private int maxX;
  private int maxY;
  private CustomButton inventoryButton = new CustomButton("Inventory");
  private StartListener inventoryStartListener;
  //Menu
  private CustomButton continueButton = new CustomButton("Continue");
  private CustomButton newGameButton = new CustomButton("New");
  private CustomButton loadGameButton = new CustomButton("Load");
  private CustomButton settingsButton = new CustomButton("Settings");
  private CustomButton scoreboardButton = new CustomButton("Scoreboard");
  
  private JLabel title = new JLabel("CONCORDIA");
  private MenuPanel menuPanel;
  private MenuBGPanel menuBgPanel;
  private StartListener menuStartListener;  
  
  Display(){
    super ("Concordia");
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    this.maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    this.setSize(maxX, maxY);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  public void menu(){
    title.setFont(new Font("sansserif", Font.BOLD, 72));
    
    this.setLayout(new BorderLayout());
    menuBgPanel = new MenuBGPanel(maxX, maxY);
    this.add(menuBgPanel, BorderLayout.CENTER);
    menuBgPanel.setLayout(new BorderLayout());
    menuPanel = new MenuPanel(300,100);
    menuBgPanel.add(title, BorderLayout.NORTH);
    menuBgPanel.add(menuPanel, BorderLayout.WEST);
    menuPanel.setLayout(new GridLayout(5, 1));
    menuPanel.add(continueButton);
    menuPanel.add(newGameButton);
    menuPanel.add(loadGameButton);
    menuPanel.add(settingsButton);
    menuPanel.add(scoreboardButton);
    this.setVisible(true);
    menuStartListener = new StartListener();
    continueButton.addActionListener(menuStartListener);
  }
  public void refreshAll(){
    itemPanel.refresh();
    gamePanel.refresh();
    mapPanel.refresh();
    hpPanel.refresh();
    expPanel.refresh();
  }
  public void start(){
    //Hide everything
    menuPanel.setVisible(false);
    menuBgPanel.setVisible(false);
    title.setVisible(false);
    //Creation of all the panels
    itemPanel = new ItemPanel(maxX, (int)(maxY*2.5/10.0));
    itemPanel.setLayout(new BorderLayout());
    itemPanel.add(inventoryButton, BorderLayout.CENTER);
    inventoryStartListener = new StartListener();
    inventoryButton.addActionListener(inventoryStartListener);
    gamePanel = new GamePanel(maxX, maxY);
    this.setLayout(new BorderLayout());
    this.add (itemPanel, BorderLayout.SOUTH);
    this.add (gamePanel, BorderLayout.NORTH);
    gamePanel.setLayout(new BorderLayout());
    mapPanel = new MapPanel((int)(maxX*1.0/5.0),(int)(maxX*1.0/5.0));
    rightPanel = new EmptyPanel((int)(maxX*1.7/5.0),(int)(maxY*1.0/20.0));
    gamePanel.add (mapPanel, BorderLayout.EAST);
    gamePanel.add (rightPanel, BorderLayout.WEST);
    rightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
    hpPanel = new HpPanel((int)(maxX*1.5/5.0),(int)(maxX*1.5/5.0/200.0*14.0));
    expPanel = new ExpPanel((int)(maxX*1.5/5.0),(int)(maxX*1.5/5.0/200.0*10.0));
    rightPanel.add (hpPanel);
    rightPanel.add (expPanel);
    this.setVisible(true);
  }
  public boolean getListen (){
    return (menuStartListener.getStart());
  } 
}