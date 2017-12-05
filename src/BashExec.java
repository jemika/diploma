import java.io.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BashExec {


    public static void update() throws IOException, InterruptedException {

        String[] cmd = {"/bin/bash","-c",""};
        System.out.println("For start the procedure of updating please enter your 'sudo' password");
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String password = bufferedReader.readLine();
        try {

            String commandTest = String.format("echo %s| sudo -S ls -la", password);//todo check root password another way
            String [] cmdTest = {"/bin/bash","-c",""};
            cmdTest[2] = commandTest;
            Runtime rTest = Runtime.getRuntime();
            Process pTest = rTest.exec(cmdTest);
            BufferedReader inTest = new BufferedReader(new
                    InputStreamReader(pTest.getInputStream()));
            if (inTest.readLine() == null) {
                System.out.println("Your password is incorrect");
                System.exit(-1);
            }
            inTest.close();

        } catch (IOException e) {
            System.out.println(e);
        }

        String packName;
        int count = 0;



        try (FileReader fileReader = new FileReader("packages_need_updates(only names)");
                BufferedReader inputFile = new BufferedReader(fileReader))

        {
         while ((packName = inputFile.readLine())!= null){
             count++;

             String command = String.format("echo %s| sudo -S apt-get -y install --only-upgrade %s",
                     password,packName);

//             String command = String.format("echo %s| sudo -S whereis %s",
//                     password,packName);
             cmd[2] = command;
//             System.out.println(Arrays.toString(cmd));

             try {

                 Runtime r = Runtime.getRuntime();
                 Process p = r.exec(cmd);

                 BufferedReader in = new BufferedReader(new
                         InputStreamReader(p.getInputStream()));
                 String inputLine;

                 while ((inputLine = in.readLine()) != null) {
                     String message = String.format("[%d\\%d] %s", count, Counter.quantityOfUpdates,inputLine);
                     System.out.println(message);
                 }
                 in.close();

             } catch (IOException e) {
                 System.out.println(e);
             }

         }
        }
        DateCheck.writeDate();
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

