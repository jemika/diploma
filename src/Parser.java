import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {

    public static void main(String args[]) {

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
        if (matcher.find( )) {
            System.out.println(matcher.group(0));
        }else {
            System.out.println("NO MATCH");
        }

        

    }
}

