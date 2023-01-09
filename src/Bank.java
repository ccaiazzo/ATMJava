import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name; // Bank's name
    private ArrayList<User> users; // List of users
    private ArrayList<Account> accounts; // List of accounts

    /**
     * Create a new Bank object with empty lists of users and accounts
     * @param name Name of bank
     */
    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Generate a new universally unique ID for a user
     * @return user's UUID
     */
    public String getNewUserUUID() {

        // Initializes
        String uuid;
        Random rng = new Random();
        int length = 6;
        boolean nonUnique;

        // Continue looping until we get a unique ID
        do {

            // Generate the number
            uuid = "";
            for (int i = 0; i < length; i++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            // Check to make sure it's unique
            nonUnique = false;
            for (User u : this.users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);
        return uuid;

    }

    public String getNewAccountUUID() {
        // Initializes
        String uuid;
        Random rng = new Random();
        int length = 10;
        boolean nonUnique;

        // Continue looping until we get a unique ID
        do {

            // Generate the number
            uuid = "";
            for (int i = 0; i < length; i++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            // Check to make sure it's unique
            nonUnique = false;
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);
        return uuid;
    }

    /**
     * Add an account to bank's account list
     * @param anAcct Account to add
     */
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    /**
     * Create a new user for the bank
     * @param firstName First name of user
     * @param lastName Last name of user
     * @param pin User's PIN
     * @return new User object
     */
    public User addUser(String firstName, String lastName, String pin) {
        // Create a new User object and add it to our list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        // Create a savings account for the user and add to User and
        // bank account lists
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    public User userLogin(String userID, String pin) {

        // Search through list of users
        for(User u: this.users) {

            // Check if user ID is correct
            if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }
        }

        // If web haven't found user or have incorrect PIN
        return null;
    }

    public String getName() {
        return this.name;
    }
}
