import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.*; // WILDCARD REEEEEEEEEEEE
import java.io.File;

public class SoundPlayer extends Display{
  Clip clip;
  SoundPlayer(String dir) throws Exception{
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
  
  public void playSound(){
    if (clip.isRunning()){
      clip.stop();   // Stop the player if it is still running
      clip.setFramePosition(0); // rewind to the beginning
      clip.start();     // Start playing
    }
  }
}