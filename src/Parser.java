import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Parser {

    public static void getNeededDate(){
        ArrayList<String> hrefList = new ArrayList<>();
        Map<String, Integer> priorityMap = null;
        Document doc = null;
        try {
            doc = Jsoup.connect("https://usn.ubuntu.com/usn/xenial/").get();
        } catch (IOException e) {
            System.err.print("Trouble with connection to the site");
        }
        Element allNotice = doc.select("div.eight-col").first();
        for (Element elem:allNotice.children()) {
            if (elem.hasClass("eight-col notice")){
                Element h3 = elem.getElementsByTag("h3").first();
                String h3Text = h3.text();

                Pattern p = Pattern.compile("((.*)-\\s(\\d{1,2}\\w{1,2}\\s\\w*\\s\\d{4}))");
                Matcher m = p.matcher(h3Text);
                m.find();
                String date_String = m.group(3);

                Date date = DateCheck.transformDate(date_String);
                if (date.after(DateCheck.getUpdateDate()) || (date.compareTo(DateCheck.getUpdateDate()) == 0)) {

                    Elements links = elem.select("a[href]");
                    for (Element link:links) {
                        String href = link.attr("abs:href");
                        hrefList.add(href);
                        priorityMap = getPriorities(hrefList);
                        if (priorityMap.containsKey("Medium") || priorityMap.containsKey("High")){
                            getPackagesToUpdate(hrefList);
                        }
                        else System.exit(1);
                    }
                }
                priorityMap.clear();
                hrefList.clear();
            }

        }
    }

    public static ArrayList<String> getPackagesToUpdate (List<String> list){
        Document doc = null;
        String[] packageArray = null;
        ArrayList<String> finalList = new ArrayList<>();
        try {
            doc = Jsoup.connect(list.get(0)).get();
        } catch (IOException e) {
            System.err.print("Trouble with connection to the site");
        }

        String line = doc.getElementsByTag("dl").text();
        Pattern pattern = Pattern.compile("(?<=Ubuntu 16.04 LTS: ).*");
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        String packages = matcher.group(0);
        String test = packages.replaceAll("((?= Ubuntu ).*)", "");
        packageArray = test.split(" ");
        for (int i = 0; i < packageArray.length - 1; i=i+2) {
            finalList.add(packageArray[i]);
        }
        return finalList;
    }

    public static Map<String, Integer> getPriorities(ArrayList<String> links){
        Document doc = null;
        Map<String, Integer> priorityMap = new HashMap<>();
        for (int i = 1; i < links.size(); i++) {
            try {
                doc = Jsoup.connect(links.get(i)).get();
            } catch (IOException e) {
                System.err.print("Trouble with connection to the site");
            }
            Pattern pattern = Pattern.compile("(.*)(bugs)");
            Matcher matcher = pattern.matcher(doc.baseUri());
            if (matcher.find()){
                int count = priorityMap.containsKey("Bug") ? priorityMap.get("Bug") : 0;
                priorityMap.put("Bug", count + 1);
                continue;
            }
            else {
                String priority = doc.select("#container > div:nth-child(2)").first().child(1).text();
                int count = priorityMap.containsKey(priority) ? priorityMap.get(priority) : 0;
                priorityMap.put(priority, count + 1);
            }
        }
        return priorityMap;
    }
}

