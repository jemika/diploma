import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Counter {

    public static ArrayList<String> allUpdatedApps = new ArrayList<>();
    static int quantityOfUpdates = countUpdates();

    private static int countUpdates(){
        int result = 0;
        String packageName = null;
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader("list_to_update")))
        {
            while ((packageName =bufferedReader.readLine()) != null){
                allUpdatedApps.add(packageName);
                result++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("list_to_update");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
