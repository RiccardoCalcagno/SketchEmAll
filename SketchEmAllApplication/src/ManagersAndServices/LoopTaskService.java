package ManagersAndServices;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoopTaskService {

    public interface TaskOfSession {
        abstract void call();
    }

    // how to create a game loop: https://java-design-patterns.com/patterns/game-loop/
    public LoopTaskService(){

        isLoopRunning = false;
    }

    private HashSet<TaskOfSession> tasksToPerform = new HashSet<>();

    public void addTaskInLoop(TaskOfSession taskToAddInTheLoop){
        try {
            lockInAccessToTasksContainer.lock();
            tasksToPerform.add(taskToAddInTheLoop);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            lockInAccessToTasksContainer.unlock();
        }
    }
    public void removeTaskFromLoop(TaskOfSession taskToRemoveFromTheLoop){
        try {
            lockInAccessToTasksContainer.lock();
            tasksToPerform.remove(taskToRemoveFromTheLoop);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            lockInAccessToTasksContainer.unlock();
        }
    }

    private Lock lockInAccessToTasksContainer = new ReentrantLock();
    private Thread mainThread;
    private boolean isLoopRunning;
    public void startLoop(){
        mainThread = new Thread(this::processGameLoop);
        mainThread.start();

        isLoopRunning = true;
    }
    public void stopLoop(){

        isLoopRunning = false;
    }

    protected void processGameLoop(){
        while (isLoopRunning) {

            try {
                var lag = new Random().nextInt(200) + 50;
                Thread.sleep(lag);

                lockInAccessToTasksContainer.lock();

                for (TaskOfSession taskToPerform: tasksToPerform) {

                        taskToPerform.call();
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            finally {
                lockInAccessToTasksContainer.unlock();
            }
        }
    }
}
