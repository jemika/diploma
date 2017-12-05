import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Counter {


    static int quantityOfUpdates = countUpdates();
    private static int countUpdates(){
        BashExec.showOnlyNames();
        int result = 0;
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader("packages_need_updates(only names)")))
        {
            while (bufferedReader.readLine() != null){

                result++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("No file packages_need_updates(only names)");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
