import java.io.*;
import java.util.*;
import java.time.*;
public class Manager {
    public static Scanner sc = new Scanner(System.in);
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
        System.out.print("Add the a watch with the following format:\n" +
                "Brand@Model@Accuracy@Caliber@MovementType\n" +
                "If you can not specify one of the fields, just put a '*' instead.");

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

}
