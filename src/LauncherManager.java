public class LauncherManager {
    public static void main(String[] args){
        String input = "" ;
        Manager m = new Manager();
        do{
            input = m.mainMenu();
        }
        while(!input.equals("e"));
    }
}
