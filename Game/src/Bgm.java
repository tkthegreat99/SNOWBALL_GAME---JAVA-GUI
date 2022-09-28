import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.*;

public class Bgm {
	 Clip bgmclip;
     public void playBgm(File file, float vol, boolean repeat){
             try{
                     //BGM은 임의의 시점에서 정지시킬 수 있어야 하므로 전역으로 전용 Clip을 사용한다
                     bgmclip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
                     bgmclip.open(AudioSystem.getAudioInputStream(file));
                     bgmclip.addLineListener(new LineListener() {
                     
                             public void update(LineEvent event) {
                                     // TODO Auto-generated method stub
                                
                                     if(event.getType()==LineEvent.Type.STOP){
                                             bgmclip.close();
                                     }
                             }
                     });
                     FloatControl volume = (FloatControl)bgmclip.getControl(FloatControl.Type.MASTER_GAIN);
                     volume.setValue(vol);
                     bgmclip.start();
                     if(repeat)
                             bgmclip.loop(bgmclip.LOOP_CONTINUOUSLY);
             }catch(Exception e){
                     e.printStackTrace();
             }
     }
     public void stopBgm(){
            
             if(bgmclip!=null && bgmclip.isRunning()){
                     bgmclip.stop();
                     bgmclip.close();
             }
     }
     public void playSound(File file, float vol, boolean repeat){
             try{
                     final Clip clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
                     clip.open(AudioSystem.getAudioInputStream(file));
                     clip.addLineListener(new LineListener() {
                             @Override
                             public void update(LineEvent event) {
                                     // TODO Auto-generated method stub
                                     if(event.getType()==LineEvent.Type.STOP){
                                             //이 부분이 없으면 효과음이 메모리에 점점 쌓여서 언젠가 크래시된다
                                             clip.close();
                                     }
                             }
                     });
                     FloatControl volume = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
                     volume.setValue(vol);
                     clip.start();
                     if(repeat)
                             clip.loop(Clip.LOOP_CONTINUOUSLY);
             }catch(Exception e){
                     e.printStackTrace();
             }
     }
}