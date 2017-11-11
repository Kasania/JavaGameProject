package kasania.resources.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound{
	
	private AudioInputStream AIS;
	private AudioFormat AF;
	private DataLine.Info info;
	private Clip clip;
	private FloatControl FC;
	
	public Sound(String path,String SoundType){
		File file = new File(".\\res\\sound\\"+path);
		try {
			AIS = AudioSystem.getAudioInputStream(file);
			AF = AIS.getFormat();
			info = new Info(Clip.class, AF);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(AIS);
			FC = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			FC.setValue(6);
			if(SoundType.equals("BGM")){
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}else{
			}
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void init(){
		clip.flush();
		clip.setFramePosition(0);
	}
	
	public void play(){
		clip.start();
	}

	public void stop(){
		clip.stop();
		clip.flush();
	}
	
	public void setVolume(float Volume){
		FC.setValue(Volume);
	}

}
