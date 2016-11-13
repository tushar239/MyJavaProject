package java5.timertask.example;

import java.awt.Toolkit;
import java.util.TimerTask;

public class Sound extends TimerTask {
    
    Toolkit toolkit;
    String sound;
   
    public Sound(String sound) {
      this.sound = sound;
      //toolkit = Toolkit.getDefaultToolkit();
    }
   
    public void run() {
      System.out.println(sound);
      //toolkit.beep();
    }
}