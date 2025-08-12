import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class TestingFile {
    public static void main(String[] args) {
//        changeLastAdjust();
//        clearLogs();
        passInfo();
    }


//    public static void convertMaps(){
//        Manager m = new Manager();
//        m.loadWatches();
//
//        for(int i = 0; i<m.watches.size(); i++){
//            Watch w = m.watches.get(i);
//            w.newLog = new TreeMap<>();
//            Iterator<Map.Entry<LocalDate, String>> iterator = w.log.entrySet().iterator();
//            while(iterator.hasNext()){
//                Map.Entry<LocalDate, String> entry = iterator.next();
//                LocalDate date = entry.getKey();
//                LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(12,0,0));
//                w.newLog.put(dateTime, entry.getValue());
//            }
//            for(LocalDateTime key: w.newLog.keySet()){
//                System.out.println(key.format(Manager.formatter)+" -> "+w.newLog.get(key));
//            }
//            System.out.println("--------------------------------");
//        }
//        m.saveWatches();
//        System.out.println("Converted!");
//    }

//    public static void changeLastAdjust(){
//        Manager m = new Manager();
//        ArrayList<Watch> watches = m.watches;
//        for(int i = 0; i<watches.size(); i++){
//            Watch w = watches.get(i);
//            LocalDate ld = w.getLastAdjust();
//            if(ld != null){
//                LocalDateTime ldt = LocalDateTime.of(ld, LocalTime.of(12,0,0));
//                w.newLastAdjust = ldt;
//                w.setLastAdjust(null);
//            }
//        }
//        m.saveWatches();
//        System.out.println("Done!");
//    }
//
//
//    public static void clearLogs(){
//        Manager m = new Manager();
//        ArrayList<Watch> watches = m.watches;
//        for(int i = 0; i<watches.size(); i++){
//            Watch w = watches.get(i);
//            w.log = null;
//        }
//        m.saveWatches();
//        System.out.println("Done this too!");
//    }

    // im afraid
    public static void passInfo(){
        Manager m = new Manager();
        ArrayList<Watch> watches = m.watches;

        for(int i = 0; i<watches.size(); i++){
            Watch w = watches.get(i);
            w.setLastAdjust(w.newLastAdjust);
            w.log = w.newLog;
            w.newLog = null;
            w.newLastAdjust = null;
        }
        m.saveWatches();
    }
}
