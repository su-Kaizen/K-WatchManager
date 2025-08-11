import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class TestingFile {
    public static void main(String[] args) {
       convertMaps();
    }


    public static void convertMaps(){
        Manager m = new Manager();
        m.loadWatches();

        for(int i = 0; i<m.watches.size(); i++){
            Watch w = m.watches.get(i);
            w.newLog = new TreeMap<>();
            Iterator<Map.Entry<LocalDate, String>> iterator = w.log.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<LocalDate, String> entry = iterator.next();
                LocalDate date = entry.getKey();
                LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(12,0,0));
                w.newLog.put(dateTime, entry.getValue());
            }
            for(LocalDateTime key: w.newLog.keySet()){
                System.out.println(key.format(Manager.formatter)+" -> "+w.newLog.get(key));
            }
            System.out.println("--------------------------------");
        }
        m.saveWatches();
        System.out.println("Converted!");
    }



}
