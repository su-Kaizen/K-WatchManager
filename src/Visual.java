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
                "[4] Show watches\n" +
                "Once you know the ID of a watch you have to concatenate de option and the ID, for example, to adjust the watch 2: '3-2'");
    }

    public static void showAddWatch(){
        System.out.print("Add the a watch with the following format:\n" +
                "Brand@Model@Accuracy@Caliber@MovementType\n" +
                "If you can not specify one of the fields, just put a '*' instead.");
    }

    public static void ask4Time(String now){
        System.out.println("What time (hh:mm:ss) is it in your watch at: "+now+" ?");
    }
}
