import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCheck {

    public static void writeDate(){

        try (PrintWriter printWriter = new PrintWriter("date", "UTF-8")){
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");
            SimpleDateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss");
            printWriter.write(dateFormat.format(date) + "\n");
            printWriter.write(dateFormatTime.format(date));
        } catch (FileNotFoundException e) {
            System.out.println("can't write to file");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void checkDate(){

        File file = new File("date");
        if (file.exists() && !file.isDirectory()){
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){

                String date = bufferedReader.readLine();
                String time = bufferedReader.readLine();

                System.out.println("last check for updates was " + date + " in " + time);

            } catch (IOException e) {
                System.out.println("cant access file");
            }

        }
        else {
            System.out.println("can't find any information of previous update. Start now!");
            writeDate();
        }
    }
}
