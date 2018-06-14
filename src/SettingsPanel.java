import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ButtonGroup;
import java.awt.Font;
import java.io.File;
import java.awt.FontFormatException;
import java.io.IOException;
import java.awt.Color;
import java.awt.GridLayout;

class SettingsPanel extends JPanel{
  private int xVal, yVal;
  /*
  private CustomCheckButton musicButton = new CustomCheckButton();
  private CustomCheckButton soundFxButton = new CustomCheckButton();
  */
  private CustomRadioButton noneButton = new CustomRadioButton("None");
  private CustomRadioButton basicButton = new CustomRadioButton("Basic");
  private CustomRadioButton interstellarButton = new CustomRadioButton("Interstellar");
  private CustomRadioButton africaButton = new CustomRadioButton("Africa");
  private ButtonGroup musicGroup = new ButtonGroup();

  private CustomRadioButton easyButton = new CustomRadioButton("Easy");
  private CustomRadioButton mediumButton = new CustomRadioButton("Medium");
  private CustomRadioButton hardButton = new CustomRadioButton("Hard");
  private ButtonGroup difficultyGroup = new ButtonGroup();
  
  private JLabel musicLabel = new JLabel("Music");
  private JLabel difficultyLabel = new JLabel("Difficulty");
  private JLabel settingsTitle = new JLabel("SETTINGS");
  private Image bg = Toolkit.getDefaultToolkit().getImage("../res/Bg.png");
  
  private Font customTitle, customHeader;
  
  private int difficulty;
  private int music;
  
  //Constructor
  SettingsPanel(int xVal, int yVal){
    this.setFocusable(true);
    this.xVal = xVal;
    this.yVal = yVal;
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
    this.setLayout(null);
    
    //Adds fonts
    try {
      customTitle = Font.createFont(Font.TRUETYPE_FONT, new File("../res/fonts/spaceage.ttf")).deriveFont(80f);
      customHeader = Font.createFont(Font.TRUETYPE_FONT, new File("../res/fonts/spaceage.ttf")).deriveFont(32f);
    } catch (IOException e) {
      e.printStackTrace();
    } catch(FontFormatException e) {
      e.printStackTrace();
    }
    
    musicGroup.add(noneButton);
    musicGroup.add(basicButton);
    musicGroup.add(interstellarButton);
    musicGroup.add(africaButton);
    difficultyGroup.add(easyButton);
    difficultyGroup.add(mediumButton);
    difficultyGroup.add(hardButton);
    
    //Sets initial music and difficulty settings
    basicButton.setSelected(true);
    mediumButton.setSelected(true);
    
    difficultyLabel.setFont(customHeader);
    difficultyLabel.setForeground(Color.WHITE);
    musicLabel.setFont(customHeader);
    musicLabel.setForeground(Color.WHITE);
    
    JPanel musicPanel = new JPanel();
    musicPanel.setLayout(new GridLayout(5, 1));
    musicPanel.setBounds(xVal/2-600, 300, 600, 300);
    musicPanel.setOpaque(false);
    musicPanel.add(musicLabel);
    musicPanel.add(noneButton);
    musicPanel.add(basicButton);
    musicPanel.add(interstellarButton);
    musicPanel.add(africaButton);
    
    JPanel difficultyPanel = new JPanel();
    difficultyPanel.setLayout(new GridLayout(4, 1));
    difficultyPanel.setBounds(xVal/2, 300, 600, 300);
    difficultyPanel.setOpaque(false);
    difficultyPanel.add(difficultyLabel);
    difficultyPanel.add(easyButton);
    difficultyPanel.add(mediumButton);
    difficultyPanel.add(hardButton);
    
    settingsTitle.setFont(customTitle);
    settingsTitle.setForeground(Color.WHITE);
    settingsTitle.setBounds(xVal/2-400, -100, 800, 300);
      
    this.add(settingsTitle);
    settingsTitle.setVisible(true);
    this.add(musicPanel);
    musicPanel.setVisible(true);
    this.add(difficultyPanel);
    difficultyPanel.setVisible(true);
  }
  
  @Override
  public void paintComponent(Graphics g){
    g.drawImage(bg,0,0,xVal,yVal,this);
  }
  
  public int[] getSettings(){
    if(noneButton.isSelected()){
      music = 1;
    } else if(basicButton.isSelected()){
      music = 2;
    } else if(interstellarButton.isSelected()){
      music = 3;
    } else if(africaButton.isSelected()){
      music = 4;
    }
    if(easyButton.isSelected()){
      difficulty = 1;
    } else if(mediumButton.isSelected()){
      difficulty = 2;
    }else if(hardButton.isSelected()){
      difficulty = 3;
    }
    
    int[] result = new int[2];
    result[0] = music;
    result[1] = difficulty;
    return result;
  }
}