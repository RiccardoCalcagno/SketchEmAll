package ManagersAndServices;

import javax.sound.sampled.*;
import java.io.IOException;

public class AudioService {

    static Clip audioClip;

    public static void initialize(){
        try {
            audioClip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            System.out.println("Could not get line from Mixer.");
            return;
        }
    }

    public static void playVictorySound(){
        AudioInputStream ais;
        try {
            ais = AudioSystem.getAudioInputStream(RepositoryService.loadSoundFromResources("success_2.wav"));
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Could not open file format :(");
            return;
        } catch (IOException e) {
            System.out.println("Could not read sound file");
            return;
        }

        try {
            audioClip.loop(0);
            audioClip.open(ais);
        } catch (LineUnavailableException e) {
            System.out.println("Could not open line from Mixer.");
        } catch (IOException e) {
            System.out.println("IO Exception while playing the audio.");
        }
    }

}
