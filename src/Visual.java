import java.io.IOException;

public class Visual {
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;34m";
    public static final String WHITE = "\033[0;37m";
    public static final String END = "\033[0m";
    public static final String PIPE = CYAN+" | "+END;
    public static final String AT = CYAN+"@"+YELLOW;
    // cyan = "\033[0;36m"
    public static void showTitle(){
        System.out.println(YELLOW+"/////////////////// K-WatchManager v2.1 /////////////////// || github.com/su-Kaizen\n"+END);
    }

    public static void showMain(){
        System.out.println(YELLOW+
                "[1] Add watch\n" +
                "[2] Check watch accuracy\n" +
                "[3] Adjust watch\n" +
                "[4] Show watch logs\n"+
                "[5] Modify watch data\n" +
                "[6] Delete watch\n" +
                "[E] Exit\n" +
                "Concatenate the option and the ID with and '-', for example to adjust watch 4 type: '3-4'."+END);
    }

    public static void showAddWatch(){
        System.out.println(YELLOW+"Add the a watch with the following format:\n" +
                "Brand"+AT+"Model"+AT+"MovementType"+AT+"Caliber"+AT+"TheoreticAccuracy"+AT+"MoreInformation\n" +
                "If you can not specify one of the fields, just put a '*' instead."+END);
    }

    public static void ask4Time(String now){
        System.out.println(YELLOW+"What time ("+CYAN+"hh:mm:ss"+YELLOW+") is it in your watch at: "+CYAN+now+YELLOW+" ?\nYou can check the exact time at "+CYAN+"https://time.is"+END);
    }

    public static void line(){
        System.out.println(CYAN+"+-------------------------------------------------------------------------------------------------------------------+"+END);
    }

    public static void header(){
        System.out.println(CYAN+"|  ID  |  BRAND  |  MODEL  |  MOVEMENT  |  CALIBER  |  THEORETIC ACCURACY  |  LAST ADJUSTED  |  MORE INFO"+END);
    }

    public static void shortHeader(){
        System.out.println(CYAN+"BRAND | MODEL | MOVEMENT | CALIBER | THEORETIC ACCURACY | MORE INFO"+END);
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
