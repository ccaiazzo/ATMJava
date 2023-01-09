import java.util.Scanner;
public class ATM {
    public static void main(String[] args) {

        // Initialize scanner
        Scanner sc = new Scanner(System.in);

        // Initialize bank
        Bank theBank = new Bank("Bank of Twilight Town");

        // Add a user, which also creates savings account
        User aUser = theBank.addUser("Scrooge", "McDuck", "0713");

        // Add a checking account
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {

            // Stay in the login prompt until successful login
            curUser = ATM.mainMenuPrompt(theBank, sc);

            // Stay in main menu until user quits
            ATM.printUserMenu(curUser, sc);
        }
    }

    /**
     * Print ATM's login menu
     * @param theBank Bank object whose accounts to use
     * @param sc Scanner object for user input
     * @return Authorized User object
     */
    public static User mainMenuPrompt(Bank theBank, Scanner sc) {

        // Initializes
        String userID;
        String pin;
        User authUser;

        // Prompt the user for ID/PIN combo until a correct one is reached
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.printf("Enter PIN: ");
            pin = sc.nextLine();

            // Try to get the User object corresponding to the ID and PIN combo
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID/PIN combination. " + "Please try again.");
            }
        } while(authUser == null); // Continue looping until successful login

        return authUser;
    }

    /**
     * Prints a menu detailing possible actions for user
     * @param theUser Logged in user object
     * @param sc Scanner object used for input
     */
    public static void printUserMenu(User theUser, Scanner sc) {

        // Print a summary of the user's account
        theUser.printAccountsSummary();

        // Initialize
        int choice;

        // User menu
        do {
            System.out.printf("Welcome %s, what would you like to do?\n",
                    theUser.getFirstName());
            System.out.println("    1) Show account transaction history");
            System.out.println("    2) Withdrawal");
            System.out.println("    3) Deposit");
            System.out.println("    4) Transfer");
            System.out.println("    5) Quit");
            System.out.println();
            System.out.println("Enter choice: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please choose 1-5");
            }
        } while (choice < 1 || choice > 5);

        // Process the choice
        switch (choice) {

            case 1:
                ATM.showTransHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;



        }
        // Redisplay this menu unless the user wants to quit
        if (choice != 5) {
            ATM.printUserMenu(theUser, sc);
        }
    }

    /**
     * Prints transaction history for specified account
     * @param theUser Logged in user object
     * @param sc Scanner object used for input
     */
    public static void showTransHistory(User theUser, Scanner sc) {

        int theAcct;

        // Get account whose transaction history to look at
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + " whose transactions " +
                    "you want to see: ", theUser.numAccounts());
            theAcct = sc.nextInt()-1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (theAcct < 0 || theAcct >= theUser.numAccounts());

        // Print the transaction history
        theUser.printAcctTransactionHistory(theAcct);
    }

    /**
     * Process transferring funds from one account to another
     * @param theUser Logged in user object
     * @param sc Scanner object used for input
     */
    public static void transferFunds(User theUser, Scanner sc) {

        // Initializes
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        // Get account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        // Get account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer to: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        // Get amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n"
                + "balance of $%.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        // Do the transfer
        theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
                "Transfer to account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format(
                "Transfer from account %s", theUser.getAcctUUID(fromAcct)));
    }

    /**
     * Process withdrawal of funds from account
     * @param theUser Logged in user object
     * @param sc Scanner object used for input
     */
    public static void withdrawFunds(User theUser, Scanner sc) {
        // Initializes
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        // Get account to withdraw from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to withdraw from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        // Get amount to withdraw
        do {
            System.out.printf("Enter the amount to withdraw (max $%.02f): $",
                    acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n"
                        + "balance of $%.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        // Close rest of input line
        sc.nextLine();

        // Get a memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        // Do withdrawal

        theUser.addAcctTransaction(fromAcct,-1*amount,memo);
    }

    /**
     * Process deposit of funds to account
     * @param theUser Logged in user object
     * @param sc Scanner object used for input
     */
    public static void depositFunds(User theUser, Scanner sc) {
        // Initializes
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        // Get account to deposit to
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to deposit to: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);

        // Get amount to deposit
        do {
            System.out.printf("Enter the amount to deposit (max $%.02f): $",
                    acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            }
        } while (amount < 0);

        // Close rest of input line
        sc.nextLine();

        // Get a memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        // Do withdrawal

        theUser.addAcctTransaction(toAcct,1*amount,memo);
    }
}
