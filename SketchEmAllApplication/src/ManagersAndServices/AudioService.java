package ManagersAndServices;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Random;

/**
 * Static class used handle sounds in the game. Can only open .wav sounds
 */
public class AudioService {

    /**
     * Audio player
     */
    private static Clip audioClip;

    /**
     * Initializes the audio player by getting authorization from the system.
     */
    public static void initialize(){
        try {
            audioClip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            System.out.println("Could not get line from Mixer.");
        }
    }

    /**
     * Gets the sound from the resources folder
     * @param soundName
     *  Name of the file containing the sound.
     * @return
     *  Stream containing the sound
     */
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

    /**
     * Plays a sound by reading the provided audio stream
     * @param stream
     *  Audio stream to read
     */
    private static void playSound(AudioInputStream stream){
        if (audioClip == null) return;
        if (audioClip.isRunning()) audioClip.stop();
        if (audioClip.isOpen()) audioClip.close();
        try {
            audioClip.open(stream);
            audioClip.start();
        } catch (LineUnavailableException e) {
            System.out.println("Could not open line from Mixer.");
        } catch (IOException e) {
            System.out.println("IO Exception while playing the audio.");
        }
    }

    /**
     * Plays a sound for success
     */
    public static void playVictorySound(){
        AudioInputStream ais;
        int soundNumber = new Random().nextInt(2)+1;
        ais = getSound("success_"+soundNumber+".wav");
        playSound(ais);
    }

    /**
     * Plays a sound for failure
     */
    public static void playFailureSound(){
        AudioInputStream ais;
        int soundNumber = new Random().nextInt(2)+1;
        ais = getSound("wrong_buzz_"+soundNumber+".wav");
        playSound(ais);
    }

    /**
     * Plays a sound for completely losing
     */
    public static void playLossSound(){
        AudioInputStream ais;
        ais = getSound("final_loss.wav");
        playSound(ais);
    }

    /**
     * Plays a sound for losing when the player is out of time
     */
    public static void playOutOfTimeSound(){
        AudioInputStream ais;
        ais = getSound("final_loss.wav");
        playSound(ais);
    }

}
