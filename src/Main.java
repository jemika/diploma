import java.io.*;
import java.util.Properties;

public class Main {


    public static void main(String[] args) {

        File file = new File("/home/jemik/IdeaProjects/diploma/out/artifacts/diploma_jar/config.properties");
        if (!file.exists()) {
            Properties properties = new Properties();
            System.out.print("Hello!\nWe need to configure this program, because it is first start." +
                    "\nDo you want this program send you emails when updating has done?" +
                    "\nPlease, type [yes or no]\n");
            try (InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                 OutputStream outputStream = new FileOutputStream(file)) {
                String answer = bufferedReader.readLine();
                boolean flag = true;
                while (flag) {
                    switch (answer) {

                        case "yes":
                            System.out.println("Ok, please, write your email address.\nYou can change it in file " +
                                    "with name 'config.properties' in root directory later.");
                            String email = bufferedReader.readLine();
                            properties.setProperty("email", email);
                            System.out.println("On your email was sent a test message, please check it.");
                            BashExec.sendEmail("This is a test message to check your email.");
                            flag = false;
                            break;
                        case "no":
                            System.out.println("Ok, you can add your email later in file 'config.properties'.");
                            properties.setProperty("email", "null");
                            properties.store(outputStream, "config");
                            flag = false;
                            break;
                        default:
                            System.out.println("please, type 'yes' or 'no'");
                            answer = bufferedReader.readLine();
                    }
                    System.out.println("Program needs to update all your packages.\n" +
                            "When you " +
                            "will be ready, please type 'yes");
                    flag = true;
                    answer = bufferedReader.readLine();
                    while (flag) {
                        switch (answer) {
                            case "yes":
                                flag = false;
                                BashExec.updateAllApps();
                                properties.setProperty("updateDay", Util.writeDate());
                                properties.store(outputStream, "config");

                                BashExec.addToCron();
                                System.out.println("\n************************\n");
                                System.out.println("All programs have successfully updated.\n" +
                                        "A new task added in CronTab" +
                                        "Please, REBOOT your PC.");
                                break;
                            default:
                                System.out.println("For continue you should type 'yes");
                                answer = bufferedReader.readLine();
                        }

                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BashExec.sendEmail("test_connect");
        Parser.getNeededDate();

        file = new File("/home/jemik/IdeaProjects/diploma/out/artifacts/diploma_jar/list_to_update");
        if (file.exists()){
            String information = BashExec.update();
            String email = Util.getEmail();
            try (InputStream inputStream = new FileInputStream("/home/jemik/IdeaProjects/diploma/out/artifacts/diploma_jar/config.properties");
            OutputStream outputStream = new FileOutputStream("/home/jemik/IdeaProjects/diploma/out/artifacts/diploma_jar/config.properties"))
            {
                Properties properties = new Properties();
                properties.load(inputStream);
                properties.setProperty("updateDay", Util.writeDate());
                properties.setProperty("email", email);
                properties.store(outputStream, null);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String message = String.format("%s was detected new information on site 'https://usn.ubuntu.com/usn/' " +
                    "for your OS.\n" +
                    "There is %d available updates.\n" +
                    "After checking your system %s",
                    Util.getDateWithTime(),
                    Counter.quantityOfUpdates,
                    information);
            BashExec.sendEmail(message);
            file.delete();

        }
    }
}