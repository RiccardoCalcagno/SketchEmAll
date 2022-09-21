import ManagersAndServices.AppLayoutManager;
import ManagersAndServices.SessionManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SessionManager sessionManager = new SessionManager();

        sessionManager.startSession();
    }
}