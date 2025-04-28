import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HomeScreen {
    public static void main(String[] args) {
        // Greet the user
        boolean isRunning = true; //a loop will start until the user inputs a correct action
        while (isRunning) {
            System.out.println("Welcome to The Zarkovian Transactions Center. How can we assist you today?");
            //Open home menu and the option for the user to input his request
            System.out.println("D Add Deposit\nP Make Payment\nL Display the ledger screen\nX Exit application");
            Scanner scanner = new Scanner(System.in);
            String action = " ";
            //Reassure the user the input requested
            String choice = scanner.nextLine().toUpperCase(); //user can either type it in lower or upper case
            System.out.println("You selected: " + choice);

            //All possible actions made by the user
            switch (choice) {
                case"D":
                    System.out.println("Enter the amount you want to deposit:");
                    String depositInput=scanner.nextLine().trim();
                    double depositAmount=0.0;

                    try {
                        depositAmount = Double.parseDouble(depositInput); //So it only accepts numbers
                    }catch(NumberFormatException e){
                        System.out.println("System only accepts numbers.");
                        break;
                    }
                    //Ask for a description of the deposit
                    System.out.println("Enter a description for the deposit (example: Payroll, bonuses, etc.):");
                    String depositDescription=scanner.nextLine().trim();

                    try {
                        String formattedAmount=String.format("%.2f", depositAmount);

                        //Display current date and time
                        LocalDateTime now= LocalDateTime.now();
                        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss|"); //the way you want it to be formatted and displayed
                        String formattedDateTime= now.format(formatter);

                        java.io.FileWriter writer = new java.io.FileWriter("transactionsAmount.csv", true);
                        writer.write(formattedDateTime + depositDescription +"|" +formattedAmount +"\n"); //write the deposit to the file
                        writer.close();
                        System.out.println("Deposit of $" + depositAmount + " has been added to your transactions");
                    }catch(Exception e){
                        System.out.println("An error occurred while saving your deposit.");
                    }
                    break;

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
                    System.out.println("Thank you for choosing The Zarkovian Transactions Center.");
                    break;
            }
        }
    }
}





