import java.io.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BashExec {

    public static void main(String[] args) {


    }

    public static void addToCron(){

        String[] cmd = {"/bin/bash","-c",""};
        String hourlyCron = String.format("* * * * * /usr/bin/java -jar %s/diploma.jar", Util.getCurrentDir());
        cmd[2] = String.format("crontab -l > current_cron\n" +
                "cat >> current_cron << EOF\n" +
                "%s\n" +
                "EOF\n" +
                "crontab < current_cron\n" +
                "rm -f current_cron", hourlyCron);
        try {
            Runtime r = Runtime.getRuntime();
            r.exec(cmd);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendEmail(String message){

        String[] cmd = {"/bin/bash","-c",""};
        String command = String.format("sendmail %s <<EOF\n" +
                "subject:Message from diploma\n" +
                "from:diplom@gmail.com\n"+
                "%s\n" +
                "EOF", Util.getEmail(), message);

        cmd[2] = command;

        try {
            Runtime r = Runtime.getRuntime();
            r.exec(cmd);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateAllApps(){

        String[] cmd = {"/bin/bash","-c",""};
        cmd[2] = ("apt-get -y upgrade");
        try {

            Runtime r = Runtime.getRuntime();
            Process p = r.exec(cmd);

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }




    public static void update(){

        String[] cmd = {"/bin/bash","-c",""};
        String packName = null;
        int count = 0;

        try (FileReader fileReader = new FileReader(Util.getCurrentDir() + "/list_to_update");
                BufferedReader inputFile = new BufferedReader(fileReader))

        {
         while ((packName = inputFile.readLine())!= null) {
             count++;
             String command = String.format("apt-get -y install --only-upgrade %s",
                     packName);
             cmd[2] = command;
             try {
                 Runtime r = Runtime.getRuntime();
                 Process p = r.exec(cmd);

                 BufferedReader in = new BufferedReader(new
                 InputStreamReader(p.getInputStream()));
                 String inputLine = null;

                 while ((inputLine = in.readLine()) != null) {
                     String message = String.format("[%d\\%d] %s", count, Counter.quantityOfUpdates,inputLine);
                     System.out.println(message);
                 }
                 in.close();

             } catch (IOException e) {
                 System.out.println(e);
             }

         }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       }

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
//                    System.out.println(matcher.group(1));//todo do printwriter in other class
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

