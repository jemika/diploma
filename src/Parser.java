import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {

    public static void main(String args[]) throws ParseException {

        Document doc = null;
        try {
            doc = Jsoup.connect("https://usn.ubuntu.com/usn/").get();
        } catch (IOException e) {
            e.printStackTrace();
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

        System.out.println(date);
        Pattern day = Pattern.compile("^(\\d{1,2})");
        Matcher dayMatcher = day.matcher(date);
        dayMatcher.find();
        String day_String = dayMatcher.group(0);

//        Pattern month = Pattern.compile("^(\\d{1,2})");//todo: month enum
//        Matcher monthMatcher = month.matcher(date);
//        monthMatcher.find();
//        String month_String = monthMatcher.group(0);
//
        Pattern year = Pattern.compile("(\\d{4})");
        Matcher yearMatcher = year.matcher(date);
        yearMatcher.find();
        String year_String = yearMatcher.group(0);



    }




}

