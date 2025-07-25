import java.io.*;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.*;

public class Manager {
    public static Scanner sc = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    ArrayList<Watch> watches;
    private DecimalFormat decimalFormat;
    public Manager(){

        decimalFormat = new DecimalFormat("#.##");
        int status = loadWatches();
        if(status == -1){
            watches = new ArrayList<>();
        }
    }

    // loads the list of watches and returns -1 if it fails.
    public int loadWatches(){
        try(ObjectInputStream o = new ObjectInputStream(new FileInputStream("watches.bin"))){
            watches = (ArrayList<Watch>) o.readObject();
        }
        catch(ClassNotFoundException | IOException ex){
            return -1;
        }
        return 0;
    }

    // Save the list of watches
    public void saveWatches(){
        try(ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("watches.bin"))){
            o.writeObject(watches);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    // show main menu
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

        if(!split[0].equals("e") && !split[0].equals("4")){
            getInput(true);
        }
        Visual.clear();
        return input;
    }

    // adding a watch to the list
    public void addWatch(){
        Visual.clear();
        Visual.showAddWatch();
        String watchInput = Manager.getInput(false);
        Watch w = Watch.makeWatch(watchInput);
        if(w != null){
            watches.add(w);
            this.saveWatches();
            System.out.println(Visual.GREEN+"Successfully added!"+Visual.END);
        }
    }

    //
    public void checkAccuracy(String id){
        Watch w = getWatch(id); // get the specified watch
        if(w != null){
            Visual.clear();
            LocalDate last = w.getLastAdjust(); // The last adjustment
            LocalDate nowDate = LocalDate.now();
            LocalTime now = LocalTime.now();
            now = now.plusMinutes(1); // The actual time with one minute more
            now = now.minusSeconds(now.getSecond()); // Remove the seconds, to do properly the dif later
            Visual.ask4Time(now.format(formatter)+":00");
            String input = getInput(false);
            LocalTime watchHour = null;

            try{
               watchHour = LocalTime.parse(input); // parse the watch time
            }
            catch(DateTimeParseException ex){
                Visual.error("Invalid time format, please write it correctly.");
                return;
            }


            // Getting the difference between the real time and the watch one
            String diff = now.until(watchHour, ChronoUnit.SECONDS)+"";
            diff = !diff.contains("-") ? "+"+diff : diff;

            System.out.println("Your watch has a "+diff+" seconds deviation.");

            // if there is a last adjustment, make an approximate deviation per day
            if(last != null){
                // The days difference between the last adjustment and today date.
                int days = (int) last.until(nowDate,ChronoUnit.DAYS);

                // avoiding negative value
                days = Math.abs(days);

                // Round the double seconds to 2 decimals
                String deviationPerDay = decimalFormat.format(Double.parseDouble(diff)/days).replace(",",".");
                System.out.println("The last adjustment was in "+last+" "+getDaysAgo(last) +
                        ". That's a round "+deviationPerDay+" seconds per day.");

                // Record a log on the watch with the seconds deviation per day.
                w.addLog(LocalDate.now(),diff+" seconds deviation. A round "+deviationPerDay+"s per day.");
            }
            else{
                // Without the deviation per day
                w.addLog(LocalDate.now(),diff+" seconds deviation.");
            }
            saveWatches(); // important to save the watches to keep the log updated
        }
    }

    public void adjustWatch(String id){
        Watch w = getWatch(id);
        if(w != null){
            Visual.clear();
            System.out.println("Write 'today' if it was adjusted today or write a date(yyyy-mm-dd)");

            String input = getInput(false).toLowerCase();
            // Simply put the lastAdjustment to today
            if(input.equals("today")){
                LocalDate now = LocalDate.now();
                w.setLastAdjust(now);
                w.addLog(now, "Adjusted.");
                Visual.success("Successfully adjusted the watch!");

            }
            else{
                try { // trying to split the input date and parse it to LocalDate
                    String[] date = input.split("-");
                    LocalDate d = LocalDate.of(
                            Integer.parseInt(date[0]),
                            Integer.parseInt(date[1]),
                            Integer.parseInt(date[2]));

                    w.setLastAdjust(d);
                    w.addLog(d,"Adjusted.");
                    Visual.success("Successfully adjusted the watch!");
                }
                catch(Exception ex){
                    Visual.error();
                }
            }
            saveWatches();
        }
    }

    // simple method to show the watch list
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

    // just returns the user input
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

    public void modifyWatch(String id){
        Watch w = getWatch(id);
        if(w != null){
            Visual.clear();
            Visual.shortHeader();
            System.out.println(w);
            System.out.println("Write all the changes in order separated with a @, if you want to maintain a field unchanged, write an '*'");
            String result[] = getInput(false).split("@");
            w.modifyData(result);
            saveWatches();
            Visual.success("Watch data successfully changed!");
        }
    }

    public void showWatchHistory(String id){
        Watch w = getWatch(id);
        if(w != null){

            // if this returns 0 means that the log is not empty
            int r = w.showHistory();
            if(r == 0){
                System.out.println("Clear log? [Y/n]");
                String input = getInput(false).toLowerCase();
                if(input.equals("y")){
                    System.out.println("You sure? [Y/n]");
                    input = getInput(false).toLowerCase();
                    if(input.equals("y")){
                        w.clearLog();
                        saveWatches();
                        Visual.success("Watch logs removed.");
                        getInput(true);
                    }
                }
            }
            else{
                getInput(true);
            }
        }
    }

    // A method to access securely to the arraylist avoiding possible exceptions like numberformatexception or indexoutofbounds
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
