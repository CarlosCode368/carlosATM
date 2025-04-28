import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class HomeScreen {
    public static void main(String[] args) {
        // Greet the user
        boolean isRunning = true; //a loop will start until the user inputs a correct action
        while (isRunning) {
            System.out.println("Welcome to The Zarkovian Bank. How can we assist you today?");
            //Open home menu and the option for the user to input his request
            System.out.println("D Add Deposit\nP Make Payment\nL Display the ledger screen\nX Exit application");
            Scanner scanner = new Scanner(System.in);
            String action = " ";
            //Reassure the user the input requested
            String choice = scanner.nextLine().toUpperCase(); //user can either type it in lower or upper case
            System.out.println("You selected: " + choice);

            //All possible actions made by the user
            switch (choice) {
                case "L":
                    boolean inLedger=true;
                    System.out.println("Here is a ledger of all your transactions:");
                    try {
                        File taskFile = new File("transactionsAmount.csv");
                        Scanner fileScanner = new Scanner(taskFile);
                        while (fileScanner.hasNextLine()) {
                            String line = fileScanner.nextLine();
                            System.out.println(line);
                        }
                        fileScanner.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("No more transactions to show. Returning to Home Screen...");
                        break;
                    }
                    System.out.println("All registered transactions. Type X to return to the Home Screen.");
                    Scanner inputScanner = new Scanner(System.in);
                    String ledgerChoice = inputScanner.nextLine().trim().toUpperCase();
                    if (ledgerChoice.equals("x")) {
                        inLedger = false;
                    }

                    break;

                case "X":
                    isRunning = false; //application ends
                    System.out.println("Thank you for choosing The Zarkovian Bank");
                    break;
            }
        }
    }
}





