import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class Parser {

    public static void getSiteDate() {

        Document doc = null;
        try {
            doc = Jsoup.connect("https://usn.ubuntu.com/usn/").get();
        } catch (IOException e) {
            System.err.println("THERE IS NOT INTERNET ACCESS!");
            System.exit(-1);
        }

        Element content = doc.select("h3").first();

        String line = String.valueOf(content);
        String pattern = "\\d{1,2}\\w{2}\\s+\\w+\\s+\\d{4}";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(line);
        String date = "";
        if (matcher.find( )) {
            date = matcher.group(0);
        }else {
            System.out.println("NO MATCH");
        }

        Pattern day = Pattern.compile("^(\\d{1,2})");
        Matcher dayMatcher = day.matcher(date);
        dayMatcher.find();
        String day_String = dayMatcher.group(0);

        Pattern month = Pattern.compile("\\w{4,}");//todo: month enum
        Matcher monthMatcher = month.matcher(date);
        monthMatcher.find();
        String month_String = monthMatcher.group(0);


        Month month1 = Month.valueOf(month_String.toUpperCase());
        month_String = String.valueOf(month1.getValue());

        Pattern year = Pattern.compile("(\\d{4})");
        Matcher yearMatcher = year.matcher(date);
        yearMatcher.find();
        String year_String = yearMatcher.group(0);

        String stringToDate = String.format("%s-%s-%s",day_String,month_String,year_String);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");

        Date string_date = null;
        try {
            string_date = dateFormat.parse(stringToDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(String.format("update on site 'https://usn.ubuntu.com/usn/' was at %s",
                dateFormat.format(string_date)));

    }
}

