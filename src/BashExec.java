import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BashExec {

    public static void listAppsNeedUpdatesFull() {

        Process process;
        BufferedReader bufferedReader = null;
        String line;
        String[] cmd = {
                "/bin/bash",
                "-c",
                "apt-show-versions | grep upgradeable | grep security"
        };
        try {
            process = Runtime.getRuntime().
                exec(cmd);

            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter printWriter = new PrintWriter("packages_need_updates", "UTF-8")){
            while ((line = bufferedReader.readLine()) != null){
                printWriter.write(line + "\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showOnlyNames(){

       listAppsNeedUpdatesFull();

        String pattern = "^([^:]+)";
        Pattern r = Pattern.compile(pattern);

        File file = new File("packages_need_updates");
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            PrintWriter printWriter = new PrintWriter("packages_need_updates(only names)", "UTF-8")){
            while((line = bufferedReader.readLine()) != null){
                Matcher matcher = r.matcher(line);
                if (matcher.find( )) {
                    System.out.println(matcher.group(1));
                    printWriter.write(matcher.group(1) + "\n");
                }else {
                    System.out.println("NO MATCH");
                }
            }

        } catch (IOException e) {
            System.out.println("cant access file");
        }

    }
}

