import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.*;

public class Manager {
    public static Scanner sc = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    ArrayList<Watch> watches;

    public Manager(){
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

    public String mainMenu(){
        Visual.showTitle();
        showWatches();
        Visual.showMain();
        String input = getInput(false).toLowerCase();
        String[] split = input.split("-");
        switch (split[0]){
            case "1" -> addWatch();
            case "2" -> {if(split.length == 2) {checkAccuracy(split[1]);} else{Visual.error();}}
            case "3" -> {if(split.length == 2) {adjustWatch(split[1]);} else{Visual.error();}}
            case "4" -> {if(split.length == 2) {showWatchHistory(split[1]);} else{Visual.error();}}
            case "5" -> {if(split.length == 2) {modifyWatch(split[1]);} else{Visual.error();}}
            case "e" -> System.out.println("Exiting...\n");
            default -> System.out.println(Visual.RED+"Invalid option...\n"+Visual.END);
        }

        if(!split[0].equals("e")){
            getInput(true);
        }
        Visual.clear();
        return input;
    }
    public void addWatch(){
        Visual.showAddWatch();
        String watchInput = Manager.getInput(false);
        Watch w = Watch.makeWatch(watchInput);
        if(w != null){
            watches.add(w);
            this.saveWatches();
            System.out.println(Visual.GREEN+"Successfully added!"+Visual.END);
        }
    }

    public void checkAccuracy(String id){
        Watch w = getWatch(id); // get the specified watch
        if(w != null){
            LocalDate last = w.getLastAdjust(); // The last adjustment
            LocalDate nowDate = LocalDate.now();
            LocalTime now = LocalTime.now();
            now = now.plusMinutes(1); // The actual time with one minute more
            now = now.minusSeconds(now.getSecond()); // Remove the seconds, to do properly the dif later
            Visual.ask4Time(now.format(formatter)+":00");

            String input = getInput(false);

            LocalTime watchHour = LocalTime.parse(input); // parse the watch time

            // Getting the difference between the real time and the watch one
            String diff = now.until(watchHour, ChronoUnit.SECONDS)+"";
            diff = !diff.contains("-") ? "+"+diff : diff;

            System.out.println("Your watch has a "+diff+" seconds deviation.");


            if(last != null){
                int days = (int) last.until(nowDate,ChronoUnit.DAYS);
                days = Math.abs(days);
                double deviationPerDay = Integer.parseInt(diff)/days;
                System.out.println("The last adjustment was in "+last+" "+getDaysAgo(last) +
                        ". That's a round "+deviationPerDay+" seconds per day.");

                w.addLog(LocalDate.now(),diff+" seconds deviation. A round "+deviationPerDay+"s per day.");
            }
            else{
                w.addLog(LocalDate.now(),diff+" seconds deviation.");
            }
        }
    }

    public void adjustWatch(String id){
        Watch w = getWatch(id);
        if(w != null){
            System.out.println("Write 'today' if it was adjusted today or write a date(yyyy-mm-dd)");
            String input = getInput(false).toLowerCase();
            if(input.equals("today")){
                LocalDate now = LocalDate.now();
                w.setLastAdjust(now);
                w.addLog(now, "Adjusted.");
            }
            else{
                try {
                    String[] date = input.split("-");
                    LocalDate d = LocalDate.of(
                            Integer.parseInt(date[0]),
                            Integer.parseInt(date[1]),
                            Integer.parseInt(date[2]));

                    w.setLastAdjust(d);
                    w.addLog(d,"Adjusted.");
                    saveWatches();
                    System.out.println(Visual.GREEN+"Successfully adjusted the watch!"+Visual.END);
                }
                catch(Exception ex){
                    Visual.error();
                }
            }
        }
    }

    public void showWatches(){
        Visual.header();
        Visual.line();
        if(watches.isEmpty()){
            System.out.println("No saved watches");
        }
        for(int i = 0; i<watches.size(); i++){
            System.out.println("ID: "+i+" -> "+watches.get(i));
        }
        Visual.line();
    }

    public static String getInput(boolean cont){
        System.out.print(cont ? "\nPress enter..." : "> ");
        return sc.nextLine().trim();
    }

    public static String getDaysAgo(LocalDate date){
        long diff = date.until(LocalDate.now(), ChronoUnit.DAYS);

        if(diff == 0){
            return "(today)";
        }

        return diff == 1 ? "("+diff+" day ago)" : "("+diff+" days ago)";
    }

    public int modifyWatch(String id){
        Watch w = getWatch(id);
        if(w != null){
            Visual.clear();

            Visual.shortHeader();

            System.out.println(w);
            System.out.println("Write all the changes in order separated with a @, if you want to maintain a field unchanged, write an '*'");
            String result[] = getInput(false).split("\\@");
            w.modifyData(result);
            saveWatches();
            System.out.println(Visual.GREEN+"Watch data successfully changed!"+Visual.END);
            return 0;
        }

        return 1;
    }

    public void showWatchHistory(String id){
        Watch w = getWatch(id);
        if(w != null){
            w.showHistory();
        }
    }

    public Watch getWatch(String i){
        Watch w = null;
        int id = -1;
        try{
            id = Integer.valueOf(i);
            w = watches.get(id);
        }
        catch (NumberFormatException | IndexOutOfBoundsException ex){
            Visual.error("Invalid watch ID, please check the watchlist.");
        }

        return w;
    }
}
