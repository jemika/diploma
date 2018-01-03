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
    public static void main(String[] args) {
        getNeededDate();
    }

//    public static void getSiteDate() {
//
//        Document doc = null;
//        try {
//            doc = Jsoup.connect("https://usn.ubuntu.com/usn/").get();
//        } catch (IOException e) {
//            System.err.println("THERE IS NOT INTERNET ACCESS!");
//            System.exit(-1);
//        }
//
//        Element content = doc.select("h3").first();
//
//        String line = String.valueOf(content);
//        String pattern = "\\d{1,2}\\w{2}\\s+\\w+\\s+\\d{4}";
//        Pattern r = Pattern.compile(pattern);
//        Matcher matcher = r.matcher(line);
//        String date = "";
//        if (matcher.find( )) {
//            date = matcher.group(0);
//        }else {
//            System.out.println("NO MATCH");
//        }
//
////        Pattern day = Pattern.compile("^(\\d{1,2})");
////        Matcher dayMatcher = day.matcher(date);
////        dayMatcher.find();
////        String day_String = dayMatcher.group(0);
//
////        String day_String = DateCheck.findDay(date);
//
////        Pattern month = Pattern.compile("\\w{4,}");//todo: month enum
////        Matcher monthMatcher = month.matcher(date);
////        monthMatcher.find();
////        String month_String = monthMatcher.group(0);
////        Month month1 = Month.valueOf(month_String.toUpperCase());
////        month_String = String.valueOf(month1.getValue());
//
////        String month_String = DateCheck.findMonth(date);
//
//
//
////        Pattern year = Pattern.compile("(\\d{4})");
////        Matcher yearMatcher = year.matcher(date);
////        yearMatcher.find();
////        String year_String = yearMatcher.group(0);
//
////        String year_String = DateCheck.findYear(date);
//
////        String stringToDate = String.format("%s-%s-%s",day_String,month_String,year_String);
////        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");
//
//
//        Date string_date = DateCheck.transformDate(date);
//
//        System.out.println(String.format("update on site 'https://usn.ubuntu.com/usn/' was at %s",
//                string_date));
//
//    }
    public static void getNeededDate(){
        ArrayList<String> hreflist = new ArrayList<>();
        Map<String, Integer> priorityMap = null;
        Document doc = null;
        try {
            doc = Jsoup.connect("https://usn.ubuntu.com/usn/xenial/").get();
        } catch (IOException e) {
            System.err.print("Trouble with connection to the site");
        }
//        Elements links = doc.select("a[href]");
//        System.out.println(links.toString());

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
                        hreflist.add(href);
                        priorityMap = getPriorities(hreflist);
                        if (priorityMap.containsKey("Medium") || priorityMap.containsKey("High")){
                            getPackagesToUpdate(hreflist);
                        }
                    }
                }
                priorityMap.clear();
                hreflist.clear();
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

