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
                    while (inLedger) {
                        System.out.println("How would you like to view your transactions?");
                        System.out.println(" A All Transactions");
                        System.out.println(" D Only Deposits");
                        System.out.println(" G Only Payments");
                        System.out.println(" R View Reports");
                        System.out.println(" H Return to Home Screen");

                        String ledgerOption = scanner.nextLine().trim().toUpperCase();

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


                            // NOW THE REPORTS VIEW
                            case "R":
                                boolean inReports = true;
                                while (inReports) {  //The loop will stay in the reports below
                                    System.out.println("Always get your info, never miss a payment. Trust the Zarkovian Transactions Center.");
                                    System.out.println(" 1 Month to Date");
                                    System.out.println(" 2 Previous Month");
                                    System.out.println(" 3 Year to Date");
                                    System.out.println(" 4 Previous Year");
                                    System.out.println(" 5 Search by Vendor or Description");
                                    System.out.println(" 6 Custom Search");
                                    System.out.println("Type 0 to return to the Home Menu");

                                    System.out.println("Enter your choice: ");
                                    String reportChoice = scanner.nextLine().trim().toUpperCase();

                                    LocalDateTime now = LocalDateTime.now();
                                    int currentMonth = now.getMonthValue();
                                    int currentYear = now.getYear();
                                    int previousYear=currentYear-1;
                                    String returnChoice="";

                                    switch (reportChoice) {
                                        case "1":
                                            System.out.println("Showing Month to Date Report...");
                                            try {
                                                File taskFile = new File("transactionsAmount.csv");
                                                Scanner fileScanner = new Scanner(taskFile);

                                                ArrayList<String> mtdTransactions = new ArrayList<>();  //defining the month to date transactions

                                                while (fileScanner.hasNextLine()) {
                                                    String line = fileScanner.nextLine();

                                                    String[] parts = line.split("\\|");
                                                    if (parts.length >= 3) {
                                                        String datePart = parts[0];
                                                        String[] dateParts = datePart.split("-");

                                                        if (dateParts.length == 3) {
                                                            int transactionYear = Integer.parseInt(dateParts[0]);
                                                            int transactionMonth = Integer.parseInt(dateParts[1]);
                                                            //comparing the month and year displayed with the current month and year in real time
                                                            if (transactionYear == currentYear && transactionMonth == currentMonth) {
                                                                mtdTransactions.add(line);
                                                            }
                                                        }
                                                    }
                                                }

                                                fileScanner.close();

                                                if (mtdTransactions.isEmpty()) {
                                                    System.out.println("No transactions for this month.");
                                                } else {
                                                    System.out.println("Month to Date Transactions:");
                                                    for (String transaction : mtdTransactions) {
                                                        System.out.println(transaction);
                                                    }
                                                }
                                            } catch (FileNotFoundException e) {
                                                System.out.println("No transactions found.Returning to Home Screen...");

                                            }
                                            System.out.println("Type X to return to the Reports Screen");
                                            returnChoice = scanner.nextLine().trim().toUpperCase();
                                            if (returnChoice.equals("X")) {
                                                System.out.println("Returning to the Reports Screen...");
                                                break;
                                            }
                                            break;

                                        case "2": //for now, I left the transaction for last month empty just so i can see the error message.
                                            System.out.println("Showing Previous Month Report...");

                                            int lastMonth=(currentMonth==1)?12:currentMonth-1;


                                            try {
                                                File taskFile = new File("transactionsAmount.csv");
                                                Scanner fileScanner = new Scanner(taskFile);

                                                ArrayList<String> lmTransactions = new ArrayList<>();  //defining the month to date transactions

                                                while (fileScanner.hasNextLine()) {
                                                    String line = fileScanner.nextLine();

                                                    String[] parts = line.split("\\|");
                                                    if (parts.length >= 3) {
                                                        String datePart = parts[0];
                                                        String[] dateParts = datePart.split("-");

                                                        if (dateParts.length == 3) {
                                                            int transactionYear = Integer.parseInt(dateParts[0]);
                                                            int transactionMonth = Integer.parseInt(dateParts[1]);
                                                            //comparing the month and year displayed with the current month and year in real time
                                                            if (transactionYear == previousYear && transactionMonth == lastMonth) {
                                                                lmTransactions.add(line);
                                                            }
                                                        }
                                                    }
                                                }

                                                fileScanner.close();

                                                if (lmTransactions.isEmpty()) {
                                                    System.out.println("No transactions for the previous month.");
                                                } else {
                                                    System.out.println("Previous Month Transactions:");
                                                    for (String transaction : lmTransactions) {
                                                        System.out.println(transaction);
                                                    }
                                                }
                                            } catch (FileNotFoundException e) {
                                                System.out.println("No transactions found.Returning to Home Screen...");

                                            }
                                            System.out.println("Type X to return to the Reports Screen");
                                            returnChoice = scanner.nextLine().trim().toUpperCase();
                                            if (returnChoice.equals("X")) {
                                                System.out.println("Returning to the Reports Screen...");
                                                break;
                                            }
                                            break;

                                        case "3": //YEAR TO DATE code
                                                    System.out.println("Showing Year to Date Report...");

                                            try {
                                                File taskFile = new File("transactionsAmount.csv");
                                                Scanner fileScanner = new Scanner(taskFile);

                                                ArrayList<String> ytdTransactions = new ArrayList<>();  //defining the month to date transactions

                                                while (fileScanner.hasNextLine()) {
                                                    String line = fileScanner.nextLine();

                                                    String[] parts = line.split("\\|");
                                                    if (parts.length >= 3) {
                                                        String datePart = parts[0];
                                                        String[] dateParts = datePart.split("-");

                                                        if (dateParts.length == 3) {
                                                            int transactionYear = Integer.parseInt(dateParts[0]);
                                                            int transactionMonth = Integer.parseInt(dateParts[1]);
                                                            if (transactionYear == currentYear && transactionMonth >= 1 && transactionMonth <= currentMonth) {
                                                                ytdTransactions.add(line);
                                                            }
                                                        }
                                                    }
                                                }

                                                fileScanner.close();

                                                if (ytdTransactions.isEmpty()) {
                                                    System.out.println("No transactions this year so far.");
                                                } else {
                                                    System.out.println("This year's transactions so far:");
                                                    for (String transaction : ytdTransactions) {
                                                        System.out.println(transaction);
                                                    }
                                                }
                                            } catch (FileNotFoundException e) {
                                                System.out.println("No transactions found.Returning to Home Screen...");

                                            }
                                            System.out.println("Type X to return to the Reports Screen");
                                            returnChoice = scanner.nextLine().trim().toUpperCase();
                                            if (returnChoice.equals("X")) {
                                                System.out.println("Returning to the Reports Screen...");
                                                break;
                                            }
                                            break;

                                                case "4":
                                                    System.out.println("Showing Previous Year Report...");
                                                    try {
                                                        File taskFile = new File("transactionsAmount.csv");
                                                        Scanner fileScanner = new Scanner(taskFile);

                                                        ArrayList<String> pyTransactions = new ArrayList<>();  //defining the month to date transactions

                                                        while (fileScanner.hasNextLine()) {
                                                            String line = fileScanner.nextLine();

                                                            String[] parts = line.split("\\|");
                                                            if (parts.length >= 3) {
                                                                String datePart = parts[0];
                                                                String[] dateParts = datePart.split("-");

                                                                if (dateParts.length == 3) {
                                                                    int transactionYear = Integer.parseInt(dateParts[0]);
                                                                    if(transactionYear==previousYear){
                                                                        pyTransactions.add(line);
                                                                    }
                                                                }
                                                            }
                                                        }

                                                        fileScanner.close();

                                                        if (pyTransactions.isEmpty()) {
                                                            System.out.println("No transactions last year.");
                                                        } else {
                                                            System.out.println("Last year transactions:");
                                                            for (String transaction : pyTransactions) {
                                                                System.out.println(transaction);
                                                            }
                                                        }
                                                    } catch (FileNotFoundException e) {
                                                        System.out.println("No transactions found.Returning to Home Screen...");

                                                    }
                                                    System.out.println("Type X to return to the Reports Screen");
                                                    returnChoice = scanner.nextLine().trim().toUpperCase();
                                                    if (returnChoice.equals("X")) {
                                                        System.out.println("Returning to the Reports Screen...");
                                                        break;
                                                    }
                                                    break;

                                                case "5":
                                                    System.out.println("Search by Vendor/Description...");
                                                    System.out.print("Enter a keyword to search (vendor OR description): ");
                                                    String keyword = scanner.nextLine().trim().toLowerCase();

                                                    try {
                                                        File taskFile = new File("transactionsAmount.csv");
                                                        Scanner fileScanner = new Scanner(taskFile);

                                                        ArrayList<String> matchingTransactions = new ArrayList<>();

                                                        while (fileScanner.hasNextLine()) {
                                                            String line = fileScanner.nextLine();
                                                            String[] parts = line.split("\\|");

                                                            if (parts.length >= 4) {  //I tried amazon lowered case, and it didn't work, so I tried this new line
                                                                String vendorOrDescription=parts[2].trim().toLowerCase();

                                                                if (vendorOrDescription.contains(keyword)){
                                                                    matchingTransactions.add(line);
                                                                }
                                                            }
                                                        }

                                                        fileScanner.close();

                                                        if (matchingTransactions.isEmpty()) {
                                                            System.out.println("No transactions found with the keyword: " + keyword);
                                                        } else {
                                                            System.out.println("Matching Transactions:");
                                                            for (String transaction : matchingTransactions) {
                                                                System.out.println(transaction);
                                                            }
                                                        }

                                                    } catch (FileNotFoundException e) {
                                                        System.out.println("Transaction file not found. Returning to Home Screen...");
                                                    }

                                                    System.out.println("Type X to return to the Reports Screen");
                                                    returnChoice = scanner.nextLine().trim().toUpperCase();
                                                    if (returnChoice.equals("X")) {
                                                        System.out.println("Returning to the Reports Screen...");
                                                        break;
                                                    }
                                                    break;
                                                case "6":
                                                    System.out.println("Custom Search...");
                                                    // code for custom search
                                                    break;

                                                case "0":
                                                    inReports = false;
                                                    break;

                                                default:
                                                    System.out.println("Invalid choice. Please try again.");
                                                    break;

                                    }
                                }
                        }
                    }

                                break;

                            case "H":
                                inLedger=false;
                                break;
                            default:
                                System.out.println("Invalid choice Please try again.");

                            //EXIT THE PROGRAM
                            case "X":
                                isRunning = false; //application ends
                                System.out.println("Thank you for choosing The Zarkovian Transactions Center.");
                                break;
                        }
                    }
            }
        }






















