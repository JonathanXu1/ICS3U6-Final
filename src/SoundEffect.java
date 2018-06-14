/*
import javax.sound.sampled.AudioInputStream;

public enum SoundEffect(){
  SHOOT("../res/Shoot.wav");
  WALK("../res/Walk.wav");
  
  private Clip clip;
  
  //Loads sound effect
  SoundEffect(String dir){
    File audioFile = new File(dir);
    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
    DataLine.Info info = new DataLine.Info(Clip.class, audioStream.getFormat());
    clip = (Clip) AudioSystem.getLine(info);
    try {
      clip.open(audioStream);
    }catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void play(){
    if (clip.isRunning()){
      clip.stop(); // Stop the player if it is still running
      clip.setFramePosition(0); // rewind to the beginning
      clip.start();// Start playing
    }
  }
  
  static void init(){
    
  }
}
*/