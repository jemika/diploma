import java.io.*;

public class BashExec {

    public static void addToCron(){

        String[] cmd = {"/bin/bash","-c",""};
        String hourlyCron = String.format("0 * * * * /usr/bin/java -jar %sdiploma.jar", Util.getCurrentDir());
        cmd[2] = String.format("crontab -l > current_cron\n" +
                "cat >> current_cron << EOF\n" +
                "%s\n" +
                "EOF\n" +
                "crontab < current_cron\n" +
                "rm -f current_cron", hourlyCron);
        try
        {
            Runtime r = Runtime.getRuntime();
            r.exec(cmd);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendEmail(String message){

        String[] cmd = {"/bin/bash","-c",""};
        String command = String.format("/usr/sbin/sendmail %s <<EOF\n" +
                "subject:Message from diploma\n" +
                "from:diplom@gmail.com\n"+
                "%s\n" +
                "EOF", Util.getEmail(), message);

        cmd[2] = command;

        try
        {
            Runtime r = Runtime.getRuntime();
            r.exec(cmd);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateAllApps(){

        String[] cmd = {"/bin/bash","-c",""};
        cmd[2] = ("/usr/bin/apt-get -y upgrade");
        try
        {
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

        try (FileReader fileReader = new FileReader("/home/jemik/IdeaProjects/diploma/out/artifacts/diploma_jar/list_to_update");
                BufferedReader inputFile = new BufferedReader(fileReader))

        {
         while ((packName = inputFile.readLine())!= null) {
             count++;
             String command = String.format("/usr/bin/apt-get -y install --only-upgrade %s",
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
}

