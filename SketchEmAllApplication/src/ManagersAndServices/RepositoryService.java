package ManagersAndServices;//package ManagersAndServices;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Resources folder handler
 */
public class RepositoryService {

    /**
     * Reference to the main {@link SessionManager}
     */
    private static SessionManager sessionManager;

    /**
     * List of words to draw
     */
    private static final ArrayList<String> wordsList = new ArrayList<>();

    /**
     * Initializes the static fields
     * @param sessionManager
     *  Reference to the main {@link SessionManager} of the app
     */
    public static void initializeAsSingleton(SessionManager sessionManager){
        RepositoryService.sessionManager = sessionManager;
        initializeWordsList();
    }

    /**
     * Initializes the list of words to take random words from.
     */
    private static void initializeWordsList(){
        java.net.URL wordsLocation =  sessionManager.getClass().getResource("/resources/words.txt");

        if (wordsLocation==null){
            System.out.println("Could not load words list, falling back on default.");
            wordsList.add("cup");
            return;
        }

        File wordsFile = new File(wordsLocation.getPath());
        Scanner reader;
        try  {
            reader = new Scanner(wordsFile);
        } catch (FileNotFoundException e) {
            System.out.println("Could not load words list, falling back on default.");
            wordsList.add("cup");
            return;
        }

        while (reader.hasNextLine()){
            wordsList.add(reader.nextLine());
        }
        reader.close();
    }

    /**
     * Randomly chooses a word for the player to draw
     * @return
     *  Word to draw
     */
    public static String chooseNextWord(){
        int nextWordIndex = new Random().nextInt(wordsList.size());
        return wordsList.get(nextWordIndex);
    }

    /**
     * Retrieves an image file from the resources
     * @param iconName
     *  Name of the image file
     * @return
     *  ImageIcon containing the loaded image
     */
    public static ImageIcon loadImageFromResources(String iconName){

        String imgLocation = "/resources/"+iconName;

        // how to select an image in the resources: https://www.jetbrains.com/help/idea/add-items-to-project.html#import-items
        java.net.URL image = sessionManager.getClass().getResource(imgLocation);

        assert image != null;

        return new ImageIcon(image);
    }

    /**
     * Retrieves an audio file from the resources
     * @param soundname
     *  Name of the sound file
     * @return
     *  Sound file
     */
    public static File loadSoundFromResources(String soundname){

        String soundLocation = "/resources/"+soundname;

        // how to select an image in the resources: https://www.jetbrains.com/help/idea/add-items-to-project.html#import-items
        java.net.URL sound = sessionManager.getClass().getResource(soundLocation);

        assert sound != null;

        System.out.println(sound.getPath());

        return new File(sound.getPath());
    }
}
