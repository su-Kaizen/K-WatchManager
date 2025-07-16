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


    public static void showMain(){
        System.out.println("[1] Add watch\n" +
                "[2] Check watch accuracy\n" +
                "[3] Adjust watch\n" +
                "[4] Modify watch data\n" +
                "[e] Exit\n" +
                "Once you know the ID of a watch you have to concatenate the option and the ID, for example, to adjust the watch 2, type: '3-2'");
    }

    public static void showAddWatch(){
        System.out.println("Add the a watch with the following format:\n" +
                "Brand@Model@Accuracy@Caliber@MovementType\n" +
                "If you can not specify one of the fields, just put a '*' instead.");
    }

    public static void ask4Time(String now){
        System.out.println("What time (hh:mm:ss) is it in your watch at: "+now+" ?");
    }

    public static void line(){
        System.out.println("=====================================================================================================================");
    }

    public static void header(){
        System.out.println("| ID | WATCH NAME | CALIBER | THEORETIC DEVIATION | CALIBER TYPE | LAST ADJUSTED |");
        System.out.println("+--------------------------------------------------------------------------------+");
    }
    public static void shortHeader(){
        System.out.println("| WATCH NAME | CALIBER | THEORETIC DEVIATION | CALIBER TYPE |");
        System.out.println("+-----------------------------------------------------------+");
    }

    public static void error(){
        System.out.println(Visual.RED+"Please type in the correct format...\n"+Visual.END);
    }
    public static void clear(){ // No se como lo hace pero funciona (borra la terminal)
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
        catch (IOException | InterruptedException ex) {
            System.err.println("Ha ocurrido un problema...\n");
        }
    }

    public static void showTitle(){
        System.out.println(YELLOW+"/////////////////// K-WatchManager /////////////////// v1.0\n"+END);
    }
}
