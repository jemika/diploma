import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Util {

    public static String getCurrentDir(){
        String current = null;
        try {

            current = new java.io.File( "." ).getCanonicalPath();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return current;
    }

    public static String getDate(){

        String dateToday = null;

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateToday = dateFormat.format(date);

        return dateToday;
    }

    public static String getEmail(){
        Properties properties = new Properties();
        InputStream input = null;
        String email = null;

        try {
            input = new FileInputStream("config.properties");
            properties.load(input);
            email = properties.getProperty("email");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return email;
    }
}
