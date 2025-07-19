import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Watch implements Serializable {
    private String caliber;
    private String brand;
    private String model;
    private String theoreticAccuracy;
    private String type;
    private LocalDate lastAdjust;
    private TreeMap<LocalDate,String> log;
    public Watch(String b, String m, String t, String c, String ty){
        brand = b.toUpperCase();
        model = m;
        theoreticAccuracy = t;
        caliber = c;
        type = ty.toUpperCase();
        lastAdjust = null;
        log = new TreeMap<>();
    }

    public void setBrand(String b){
        brand = b.toUpperCase();
    }

    public LocalDate getLastAdjust(){
        return lastAdjust;
    }

    public void setLastAdjust(LocalDate date){
        lastAdjust = date;
    }

    public void setModel(String m){
        model = m.toUpperCase();
    }

    @Override
    public String toString(){
        String s = brand+" "+model+" | "+caliber+" | "+theoreticAccuracy+" | "+type;

        return lastAdjust == null ? s : s+" | "+lastAdjust+" "+Manager.getDaysAgo(lastAdjust);
    }

    public static Watch makeWatch(String input){
        if(input.contains("*")){
            input = input.replace("*","Not specified");
        }
        String[] att = input.split("@");
        if(att.length != 5){
            Visual.error();
            return null;
        }
        return new Watch(att[0], att[1], att[2], att[3], att[4]);
    }

    public void modifyData(String data[]){
        /* WATCH NAME | CALIBER | THEORETIC DEVIATION | CALIBER TYPE */
        if (data.length == 4){
            this.model = data[0].equals("*") ? this.model : data[0].toUpperCase();
            this.caliber = data[1].equals("*") ? this.caliber : data[1];
            this.theoreticAccuracy = data[2].equals("*") ? this.theoreticAccuracy : data[2];
            this.type = data[3].equals("*") ? this.type : data[3].toUpperCase();
        }
        else{
            Visual.error();
        }
    }

    public void showHistory(){
        System.out.println("+----------------+");

        System.out.println("+----------------+");
    }

    public void addLog(LocalDate date, String action){
        log.put(date,action);
    }
}
