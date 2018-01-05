import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateCheck {


    public static Date getUpdateDate(){


        File file = new File("date");
        if (file.exists() && !file.isDirectory()){
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){

                String date = bufferedReader.readLine();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                return dateFormat.parse(date);

            } catch (IOException e) {
                System.out.println("cant access file");
            } catch (ParseException e) {
                System.err.print("Trouble when parsing date");
            }

        }
        else {
            System.out.println("can't find any information of previous update. Start now!");
            return null;
        }
        return null;
    }
//    public static void checkDate(){
//
//        File file = new File("date");
//        if (file.exists() && !file.isDirectory()){
//            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
//
//                String date = bufferedReader.readLine();
//                String time = bufferedReader.readLine();
//
//                System.out.println("last check for updates was " + date + " in " + time);
//
//            } catch (IOException e) {
//                System.out.println("cant access file");
//            }
//
//        }
//        else {
//            System.out.println("can't find any information of previous update. Start now!");
//            writeDate();
//        }
//    }

    public static String findDay(String date){
        Pattern day = Pattern.compile("^(\\d{1,2})");
        Matcher dayMatcher = day.matcher(date);
        dayMatcher.find();
        return dayMatcher.group(0);
    }

    public static String findMonth(String date){
        Pattern month = Pattern.compile("\\s(\\w{3,})\\s");//todo: month enum
        Matcher monthMatcher = month.matcher(date);
        monthMatcher.find();
        String month_String = monthMatcher.group(1);
        Month month1 = Month.valueOf(month_String.toUpperCase());
        return String.valueOf(month1.getValue());
    }

    public static String findYear(String date){
        Pattern year = Pattern.compile("(\\d{4})");
        Matcher yearMatcher = year.matcher(date);
        yearMatcher.find();
        return yearMatcher.group(0);
    }

    public static Date transformDate(String date) {
        String stringToDate = String.format("%s-%s-%s", findDay(date), findMonth(date), findYear(date));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat newDateFormat = new SimpleDateFormat();
        Date string_Date = null;
        try {
            string_Date = dateFormat.parse(stringToDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return string_Date;
    }
}
