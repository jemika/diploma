import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class Util {

    public static String getCurrentDir(){
        String current = null;
        try {

            current = new java.io.File( "." ).getCanonicalPath();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return current + "/";
    }

    public static Date getDateWithTime(){
        Date date = new Date();
        return date;
    }
    
    public static Date getDate(){
        Properties properties = new Properties();
        InputStream input = null;
        Date date = null;
        String date_String = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        try {
            input = new FileInputStream(Main.config);
            properties.load(input);
            date_String = properties.getProperty("updateDay");
            date = dateFormat.parse(date_String);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return date;
    }

    public static String writeDate(){

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
            input = new FileInputStream(Main.config);
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
