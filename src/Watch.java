public class Watch {
    private String caliber;
    private String brand;
    private String model;
    private String theoreticAccuracy;
    private String type;
    public Watch(String b, String m, String t, String c, String ty){
        brand = b.toUpperCase();
        model = m;
        theoreticAccuracy = t;
        caliber = c;
        type = ty.toUpperCase();
    }

    public Watch(String b, String m, String t, String ty){
        brand = b.toUpperCase();
        model = m;
        theoreticAccuracy = t;
        caliber = "Not specified";
        type = ty.toUpperCase();
    }

    public void setBrand(String b){
        brand = b.toUpperCase();
    }

    public void setModel(String m){
        model = m.toUpperCase();
    }

    @Override
    public String toString(){
        return brand+" "+model+" | "+caliber+" | "+theoreticAccuracy;
    }

}
