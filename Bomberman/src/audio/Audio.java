package audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Audio {
	
	public static final Audio MUSIC = new Audio("music");
	
	private AudioInputStream audioInputStream;
	private Clip clip;
	
	public Audio (String filename) {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("rsc/sounds/" + filename + ".wav"));
			clip = AudioSystem.getClip();
	    } catch(Exception ex) {
	        System.out.println("Error with prepping sound.");
	        ex.printStackTrace();
	    }
	}
	public void playSound(boolean loop) {
	    try {
	    	clip.open(audioInputStream);
			clip.start();
			if(loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void stopSound() {
		clip.stop();
	}
	
}
