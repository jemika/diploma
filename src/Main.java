import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
//        Parser.getSiteDate();
//        DateCheck.checkDate();
        System.out.println(String.format("For now we you can update %d packages", Counter.quantityOfUpdates));
        System.out.println("Are you want to update? (yes/no)");
        try (InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader))
        {
        String answer = bufferedReader.readLine();
        boolean flag = true;
        while(flag){
            switch (answer){

                case "yes": BashExec.update();
                flag = false;
                    break;
                case "no":
                    System.out.println("goodbye!");
                    System.exit(1);
                default:
                    System.out.println("please, type 'yes' or 'no'");
                    answer = bufferedReader.readLine();
            }
        }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n SUCCESS!!");

    }
}
