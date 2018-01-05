import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public class FileWriter {

    public static void writeData(String fileName, Collection<String> collection){
        try (PrintWriter printWriter = new PrintWriter(fileName, "UTF-8")){
            for (Object obj: collection) {
                printWriter.write(String.valueOf(obj) + "\n");
            }
        }
        catch (IOException e) {
            System.out.println("cant access file");
        }
    }


}
