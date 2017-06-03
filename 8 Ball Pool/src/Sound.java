import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	Clip clip;
	AudioInputStream inputStream;
	FloatControl volume;
	int vol;

	public void loadSound(String filePath) throws Throwable, IOException {
		inputStream= AudioSystem.getAudioInputStream(new File(filePath));
		clip=AudioSystem.getClip();
		clip.open(inputStream);
		volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		volume.setValue(vol);
	}
	
	
	public void playSound() throws Throwable{
		clip.start(); //  so i dont hear the same song 200000 times
	}
	
	public void stopSound(){
		clip.stop();
	}
	
	//sound must be loaded again after changing volume each time
	public void setVolume(int x){
		vol=x;
	}

	
}
