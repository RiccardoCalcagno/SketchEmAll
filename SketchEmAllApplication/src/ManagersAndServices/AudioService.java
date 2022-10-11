package ManagersAndServices;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * Static class used handle sounds in the game
 */
public class AudioService {

    private static Clip audioClip;

    public static void initialize(){
        try {
            audioClip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            System.out.println("Could not get line from Mixer.");
        }
    }

    private static AudioInputStream getSound(String soundName){

        try {
            return AudioSystem.getAudioInputStream(RepositoryService.loadSoundFromResources(soundName));
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Could not open file format :(");
            return null;
        } catch (IOException e) {
            System.out.println("Could not read sound file");
            return null ;
        }
    }

    private static void playSound(AudioInputStream stream){
        try {
            audioClip.loop(0);
            audioClip.open(stream);
        } catch (LineUnavailableException e) {
            System.out.println("Could not open line from Mixer.");
        } catch (IOException e) {
            System.out.println("IO Exception while playing the audio.");
        }
    }

    public static void playVictorySound(){
        AudioInputStream ais;
        ais = getSound("success_2.wav");
        playSound(ais);
    }

    public static void playFailureSound(){
        AudioInputStream ais;
        ais = getSound("wrong_buzz_1.wav");
        playSound(ais);
    }

    public static void playLossSound(){
        AudioInputStream ais;
        ais = getSound("final_loss.wav");
        playSound(ais);
    }

    public static void playOutOfTimeSound(){
        //TODO
    }

}
