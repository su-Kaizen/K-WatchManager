import java.time.LocalDate;

public class Watch {
    private String caliber;
    private String brand;
    private String model;
    private String theoreticAccuracy;
    private String type;
    private LocalDate lastAdjust;
    public Watch(String b, String m, String t, String c, String ty){
        brand = b.toUpperCase();
        model = m;
        theoreticAccuracy = t;
        caliber = c;
        type = ty.toUpperCase();
        lastAdjust = null;
    }

    public void setBrand(String b){
        brand = b.toUpperCase();
    }

    public void setModel(String m){
        model = m.toUpperCase();
    }

    @Override
    public String toString(){
        return brand+" "+model+" | "+caliber+" | "+theoreticAccuracy+" | "+type;
    }

    public static Watch makeWatch(String input){
        if(input.contains("*")){
            input = input.replace("*","Not specified");
        }
        String[] att = input.split("@");
        if(att.length != 5){
            throw new IllegalArgumentException("Please.\n");
        }
        return new Watch(att[0], att[1], att[2], att[3], att[4]);
    }

}
