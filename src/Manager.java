import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.time.*;
import java.util.concurrent.TimeUnit;

public class Manager {
    public static Scanner sc = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    ArrayList<Watch> watches;

    public void Manager(){
        int status = loadWatches();
        if(status == -1){
            watches = new ArrayList<>();
        }
    }

    public int loadWatches(){
        try(ObjectInputStream o = new ObjectInputStream(new FileInputStream("watches.bin"))){

            watches = (ArrayList<Watch>) o.readObject();
        }
        catch(ClassNotFoundException | IOException ex){
            ex.printStackTrace();
            return -1;
        }
        return 0;
    }

    public void saveWatches(){
        try(ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("watches.bin"))){
            o.writeObject(watches);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void addWatch(){
       Visual.showAddWatch();

        String watchInput = Manager.getInput();
        Watch w = Watch.makeWatch(watchInput);

        watches.add(w);
        this.saveWatches();
        System.out.println("Successfully added!");
    }

    public static String getInput(){
        System.out.print("\n> ");
        return sc.nextLine();
    }

    public void mainMenu(){
        Visual.showMain();
        String input = getInput();
        String[] split = input.split("-");
        switch (split[0]){
            case "1" -> addWatch();
            case "2" -> checkAccuracy(split[1]);
            case "3" -> adjustWatch(split[1]);
            default -> System.out.println("Invalid option...");
        }
    }

    public void checkAccuracy(String id){
        Watch watch = watches.get(Integer.parseInt(id)); // get the specified watch
        LocalDate last = watch.getLastAdjust(); // The last adjustment
        LocalDate nowDate = LocalDate.now();
        LocalTime now = LocalTime.now();
        now = now.plusMinutes(1); // The actual time with one minute more

        Visual.ask4Time(now.format(formatter)+":00");

        String input = getInput();

        LocalTime watchHour = LocalTime.parse(input); // parse the watch time

        // Getting the difference between the real time and the watch one
        String diff = now.until(watchHour, ChronoUnit.SECONDS)+"";
        diff = !diff.contains("-") ? "+"+diff : diff;

        System.out.println("The watch has a "+diff+" seconds deviation");

        if(last != null){
            int days = (int) last.until(nowDate,ChronoUnit.DAYS);
            days = Math.abs(days);
            double deviationPerDay = Integer.parseInt(diff)/days;
            System.out.println("The last adjustment was in "+last+"\n" +
                    "Thats a round "+deviationPerDay+" seconds per day");
        }
    }



    public void adjustWatch(String id){
        Watch watch = watches.get(Integer.parseInt(id));
        System.out.println("Write 'today' if it was adjusted today or write a date(yyyy-mm-dd)");
        String input = getInput();
    }

}
