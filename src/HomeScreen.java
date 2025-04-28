import java.io.File;
import java.util.ArrayList;
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
            System.out.println("D Add Deposit\nP Make Payment\nL Display the ledger options\nX Exit application");
            Scanner scanner = new Scanner(System.in);
            String action = " ";
            //Reassure the user the input requested
            String choice = scanner.nextLine().toUpperCase(); //user can either type it in lower or upper case
            System.out.println("You selected: " + choice);

            //All possible actions made by the user
            switch (choice) {
                //MAKE DEPOSIT
                case "D":
                    System.out.println("Enter the amount you want to deposit:");
                    String depositInput = scanner.nextLine().trim();
                    double depositAmount = 0.0;

                    try {
                        depositAmount = Double.parseDouble(depositInput); //So it only accepts numbers
                    } catch (NumberFormatException e) {
                        System.out.println("System only accepts numbers.");
                        break;
                    }
                    //Ask for a description of the deposit
                    System.out.println("Enter a description for the deposit (example: Payroll, bonuses, etc.):");
                    String depositDescription = scanner.nextLine().trim();

                    try {
                        String formattedAmount = String.format("%.2f", depositAmount);

                        //Display current date and time
                        LocalDateTime now = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss|"); //the way you want it to be formatted and displayed
                        String formattedDateTime = now.format(formatter);

                        java.io.FileWriter writer = new java.io.FileWriter("transactionsAmount.csv", true);
                        writer.write(formattedDateTime + depositDescription + "|" + formattedAmount + "\n"); //write the deposit to the file
                        writer.close();
                        System.out.println("Deposit of $" + depositAmount + " has been added to your transactions");
                    } catch (Exception e) {
                        System.out.println("An error occurred while saving your deposit.");
                    }
                    break;

                //MAKE PAYMENT
                case "P":
                    System.out.println("Enter the amount you want to pay:");
                    String paymentInput = scanner.nextLine().trim();
                    double paymentAmount = 0.0;

                    try {
                        paymentAmount = Double.parseDouble(paymentInput); //So it only accepts numbers
                    } catch (NumberFormatException e) {
                        System.out.println("System only accepts numbers.");
                        break;
                    }
                    //Ask for a description of the deposit
                    System.out.println("Enter a description for the payment (example: Family support, services, etc.):");
                    String paymentDescription = scanner.nextLine().trim();

                    try {
                        String formattedAmount = String.format("%.2f", paymentAmount);

                        //Display current date and time
                        LocalDateTime now = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss|"); //the way you want it to be formatted and displayed
                        String formattedDateTime = now.format(formatter);

                        java.io.FileWriter writer = new java.io.FileWriter("transactionsAmount.csv", true);
                        writer.write(formattedDateTime + paymentDescription + "|-" + formattedAmount + "\n"); //write the deposit to the file
                        writer.close();
                        System.out.println("Payment of $" + paymentAmount + " has been made and added to your transaction history");
                    } catch (Exception e) {
                        System.out.println("An error occurred while saving your payment.");
                    }
                    break;

                //ALL TRANSACTIONS VIEW
                case "L":
                    boolean inLedger = true;
                    System.out.println("How would you like to view your transactions?");
                    System.out.println(" A All Transactions");
                    System.out.println(" D Only Deposits");
                    System.out.println(" G Only Payments");
                    System.out.println(" R View Reports");

                    Scanner inputScanner = new Scanner(System.in);
                    String ledgerOption = inputScanner.nextLine().trim().toUpperCase();

                    switch (ledgerOption) {
                        case "A":  //sort the search with the following switches
                            System.out.println("Here is a ledger of all your transactions");
                            try {
                                File taskFile = new File("transactionsAmount.csv");
                                Scanner fileScanner = new Scanner(taskFile);

                                ArrayList<String> transactions = new ArrayList<>();

                                while (fileScanner.hasNextLine()) {
                                    String line = fileScanner.nextLine();
                                    transactions.add(line);
                                }

                                fileScanner.close();
                                //Print from last to first

                                for (int i = transactions.size() - 1; i >= 0; i--) {
                                    System.out.println(transactions.get(i));
                                }
                            } catch (FileNotFoundException e) {
                                System.out.println("No more transactions to show. Returning to Home Screen...");
                                break;
                            }
                            break;

                        case "D":
                            //ALL DEPOSITS VIEW
                            System.out.println("Here is a ledger of all your DEPOSITS:");
                            try {
                                File taskFile = new File("transactionsAmount.csv");
                                Scanner fileScanner = new Scanner(taskFile);

                                ArrayList<String> deposits = new ArrayList<>();

                                while (fileScanner.hasNextLine()) {
                                    String line = fileScanner.nextLine();

                                    //split line intro parts
                                    String[] parts = line.split("\\|");
                                    if (parts.length >= 4) {
                                        String amountPart = parts[3];  //amount is the 4th field starting from 0

                                        try {
                                            double amount = Double.parseDouble(amountPart);
                                            if (amount > 0) {
                                                deposits.add(line);
                                            }
                                        } catch (NumberFormatException e) {
                                        }
                                    }
                                }
                                fileScanner.close();
                                for (int i = deposits.size() - 1, count = 1; i >= 0; i--, count++) {
                                    System.out.println(deposits.get(i));
                                }
                            } catch (FileNotFoundException e) {
                                System.out.println("No deposits found. Returning to Home Screen...");
                                break;
                            }
                            break;

                        case "G":
                            //ALL PAYMENTS VIEW
                            System.out.println("Here is a ledger of all your PAYMENTS:");
                            try {
                                File taskFile = new File("transactionsAmount.csv");
                                Scanner fileScanner = new Scanner(taskFile);

                                ArrayList<String> payments = new ArrayList<>();

                                while (fileScanner.hasNextLine()) {
                                    String line = fileScanner.nextLine();

                                    //split line intro parts
                                    String[] parts = line.split("\\|");
                                    if (parts.length >= 4) {
                                        String amountPart = parts[3];  //amount is the 4th field starting from 0

                                        try {
                                            double amount = Double.parseDouble(amountPart);
                                            if (amount < 0) {
                                                payments.add(line);
                                            }
                                        } catch (NumberFormatException e) {
                                        }
                                    }
                                }
                                fileScanner.close();
                                for (int i = payments.size() - 1, count = 1; i >= 0; i--, count++) {
                                    System.out.println(payments.get(i));
                                }
                            } catch (FileNotFoundException e) {
                                System.out.println("No payments found. Returning to Home Screen...");
                                break;
                            }
                            break;
                        default:
                            System.out.println("Invalid option. Returning to Home Screen");
                            break;
                    }
                    System.out.println("All registered payments. Type X to return to the Home Screen.");
                    String ledgerChoice = inputScanner.nextLine().trim().toUpperCase();
                    if (ledgerChoice.equals("x")) {
                        inLedger = false;
                    }

                    break;

                case "X":
                    isRunning = false; //application ends
                    System.out.println("Thank you for choosing The Zarkovian Transactions Center.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}












