import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;

public class User {
    private String firstName; //User's first name
    private String lastName; //User's last name
    private String uuid; //User's ID number
    private byte pinHash[]; // MD5 hash of user's PIN
    private ArrayList<Account> accounts; // List of accounts for user

    /**
     * Create a new user
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param pin the user's account PIN number
     * @param theBank  the Bank object the user is a customer of
     */
    public User(String firstName, String lastName, String pin, Bank theBank) {

        // Set user's name
        this.firstName = firstName;
        this.lastName = lastName;

        // Store the PIN's MD5 hash instead of the original value for security reasons
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        // Get a new, unique universal ID for the user
        this.uuid = theBank.getNewUserUUID();

        // Create empty list of accounts
        this.accounts = new ArrayList<Account>();

        // Print long message
        System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);

    }

    /**
     * Add an account for user
     * @param anAcct account to add
     */
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    /**
     * Return user's UUID
     * @return the UUID
     */
    public String getUUID() {
        return this.uuid;
    }

    /**
     * Check whether given PIN matches true user PIN
     * @param aPin The PIN to check against user PIN
     * @return whether PIN is valid or not
     */
    public boolean validatePin(String aPin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()),
                    this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    /**
     * Returns user's first name
     * @return user's first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Prints summaries for all accounts of user
     */
    public void printAccountsSummary() {
        System.out.printf("\n\n%s's accounts summary\n", this.firstName);
        for(int i = 0; i < this.accounts.size(); i++) {
            System.out.printf("%d) %s\n", i+1,
                    this.accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * Get number of accounts of user
     * @return number of accounts
     */
    public int numAccounts() {
        return this.accounts.size();
    }

    /**
     * Print transaction history for an account
     * @param acctIdx index of account to use
     */
    public void printAcctTransactionHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }

    /**
     * Get balance of account
     * @param acctIdx index of account to use
     * @return balance of account
     */
    public double getAcctBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    /**
     * Get UUID of account
     * @param acctIdx index of account to use
     * @return UUID of account
     */
    public String getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID();
    }

    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }
}
