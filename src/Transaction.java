import java.util.Date;

public class Transaction {
    private double amount; // Amount in transaction
    private Date timestamp; // Time and date of transaction
    private String memo; // Memo for transaction
    private Account inAccount; // Account transaction occurred in

    /**
     * Create a new transaction
     * @param amount Amount transacted
     * @param inAccount Account transaction belongs to
     */
    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    /**
     * Create a new transaction
     * @param amount Amount transacted
     * @param memo Memo for transaction
     * @param inAccount Account transaction belongs to
     */
    public Transaction(double amount, String memo, Account inAccount) {
        // Call the two argument constructor first
        this(amount, inAccount);

        // Set the memo
        this.memo = memo;
    }

    /**
     * Get amount of transaction
     * @return the amount
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * Returns summary line string for transaction
     * @return summary string
     */
    public String getSummaryLine() {

        if (this.amount >= 0) {
            return String.format("%s : $%.02f : %s", this.timestamp.toString(),
                    this.amount, this.memo);
        } else {
            return String.format("%s : $(%.02f) : %s", this.timestamp.toString(),
                    this.amount, this.memo);
        }
    }
}
