package ManagersAndServices;//package ManagersAndServices;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RepositoryService {

    private static SessionManager sessionManager;
    private static final ArrayList<String> wordsList = new ArrayList<>();

    public static void initializeAsSingleton(SessionManager sessionManager){
        RepositoryService.sessionManager = sessionManager;
        initializeWordsList();
    }

    private static void initializeWordsList(){
        java.net.URL wordsLocation =  sessionManager.getClass().getResource("/resources/words.txt");
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

    public static String chooseNextWord(){
        int nextWordIndex = new Random().nextInt(wordsList.size());
        return wordsList.get(nextWordIndex);
    }

    public static ImageIcon loadImageFromResources(String iconName){

        String imgLocation = "/resources/"+iconName;

        // how to select an image in the resources: https://www.jetbrains.com/help/idea/add-items-to-project.html#import-items
        java.net.URL image = sessionManager.getClass().getResource(imgLocation);

        assert image != null;

        return new ImageIcon(image);
    }

    public static File loadSoundFromResources(String soundname){

        String soundLocation = "/resources/"+soundname;

        // how to select an image in the resources: https://www.jetbrains.com/help/idea/add-items-to-project.html#import-items
        java.net.URL sound = sessionManager.getClass().getResource(soundLocation);

        assert sound != null;

        return new File(sound.getPath());
    }
}
