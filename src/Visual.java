import java.io.IOException;

public class Visual {
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";
    public static final String END = "\033[0m";

    public static void showTitle(){
        System.out.println(YELLOW+"/////////////////// K-WatchManager v1.2 /////////////////// || github.com/su-Kaizen\n"+END);
    }

    public static void showMain(){
        System.out.println(
                "[1] Add watch\n" +
                "[2] Check watch accuracy\n" +
                "[3] Adjust watch\n" +
                "[4] Show watch logs\n"+
                "[5] Modify watch data\n" +
                "[6] Delete watch\n" +
                "[E] Exit\n" +
                "Concatenate the option and the ID with and '-', for example to adjust watch 4 type: '3-4'");
    }

    public static void showAddWatch(){
        System.out.println("Add the a watch with the following format:\n" +
                "Brand@Model@MovementType@Caliber@TheoreticAccuracy\n" +
                "If you can not specify one of the fields, just put a '*' instead.");
    }

    public static void ask4Time(String now){
        System.out.println("What time (hh:mm:ss) is it in your watch at: "+now+" ?\nYou can check the exact time at https://time.is");
    }

    public static void line(){
        System.out.println("+-------------------------------------------------------------------------------------------------------------------+");
    }

    public static void header(){
        System.out.println("| ID | BRAND | MODEL | MOVEMENT | CALIBER | THEORETIC ACCURACY | LAST ADJUSTED |");
    }

    public static void shortHeader(){
        System.out.println("| BRAND | MODEL | MOVEMENT | CALIBER | THEORETIC ACCURACY |");
    }


    public static void error(){
        System.out.println(Visual.RED+"Please type in the correct format..."+Visual.END);
    }

    // Custom error message
    public static void error(String message){
        System.out.println(Visual.RED+message+Visual.END);
    }

    // Clears the terminal
    public static void clear(){
        try {
            if(System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else{
                new ProcessBuilder("clear").start();
            }
        }
        catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void success(String message){
        System.out.println(Visual.GREEN+message+Visual.END);
    }
}
