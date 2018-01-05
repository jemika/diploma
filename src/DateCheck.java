import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateCheck {

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
