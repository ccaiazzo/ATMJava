import java.util.ArrayList;

public class Account {
    private String name; // Name of account
    private String uuid; // Account's ID number
    private User holder; // User objects that owns account
    private ArrayList<Transaction> transactions; // List of account's transactions

    /**
     *
     * @param name name of account
     * @param holder User object that holds account
     * @param theBank Bank that issues account
     */
    public Account(String name, User holder, Bank theBank) {
        // Set account name and holder
        this.name = name;
        this.holder = holder;

        // Get new account UUID
        this.uuid = theBank.getNewAccountUUID();

        // Initialize transactions
        this.transactions = new ArrayList<Transaction>();
    }

    public String getUUID() {
        return this.uuid;
    }

    /**
     * Get summary line for account
     * @return the string summary
     */
    public String getSummaryLine() {

        // Get account's balance
        double balance = this.getBalance();

        // Format the summary line, depending on whether the balance is
        // negative
        if (balance >= 0) {
            return String.format("%s : $%.02f : %s",
                    this.uuid, balance, this.name);
        } else {
            return String.format("%s : $(%.02f) : %s",
                    this.uuid, balance, this.name);
        }
    }

    /**
     * Returns the balance of account
     * @return account balance
     */
    public double getBalance() {

        double balance = 0;
        for(Transaction t: this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    /**
     * Print transaction history of account
     */
    public void printTransHistory() {
        System.out.printf("\nTransaction history for account %s\n",
                this.uuid);
        for(int i = this.transactions.size()-1; i >= 0; i--) {
            System.out.printf(this.transactions.get(i).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * Adds new transaction object to list
     * @param amount Amount in transaction
     * @param memo Memo in transaction
     */
    public void addTransaction(double amount, String memo) {

        // Create new transaction object and add to list
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
}
