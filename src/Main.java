import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        File file = new File("config.properties");
        if (!file.exists()) {
            Properties properties = new Properties();
            System.out.print("Hello!\nWe need to configure this program, because it is first start." +
                    "\nDo you want this program send you emails when updating has done?" +
                    "\nPlease, type [yes or no]");
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
                    System.out.println("Program needs to update all your packages. After that computer will reboot, when you " +
                            "will be ready, please type 'yes");
                    flag = true;
                    answer = bufferedReader.readLine();
                    while (flag) {
                        switch (answer) {
                            case "yes":
                                flag = false;
//                                BashExec.updateAllApps();
                                properties.setProperty("updateDay", Util.getDate());
                                properties.store(outputStream, "config");
                                BashExec.addToCron();
                                System.out.println("All programs have successfully updated.\n" +
                                        "A new task added in CronTab" +
                                        "Please, REBOOT your PC.");
                                BashExec.addToCron();
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
        Parser.getNeededDate();
    }
}




////        Parser.getSiteDate();
////        DateCheck.checkDate();
//        System.out.println(String.format("For now we you can update %d packages", Counter.quantityOfUpdates));
//        System.out.println("Are you want to update? (yes/no)");
//        try (InputStreamReader inputStreamReader = new InputStreamReader(System.in);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader))
//        {
//        String answer = bufferedReader.readLine();
//        boolean flag = true;
//        while(flag){
//            switch (answer){
//
//                case "yes": BashExec.update();
//                flag = false;
//                    break;
//                case "no":
//                    System.out.println("goodbye!");
//                    System.exit(1);
//                default:
//                    System.out.println("please, type 'yes' or 'no'");
//                    answer = bufferedReader.readLine();
//            }
//        }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("\n SUCCESS!!");
//
//    }

