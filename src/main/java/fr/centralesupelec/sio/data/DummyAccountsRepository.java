package fr.centralesupelec.sio.data;

import fr.centralesupelec.sio.model.Account;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * A concrete {@link AccountsRepository} backed by an in-memory list of static {@link Account} entities.
 */
public class DummyAccountsRepository extends AccountsRepository {

    // Hold entities in a simple list.
    private final List<Account> mAccounts;

    DummyAccountsRepository()  {
        // Define a single account
        Account a1 = new Account();
        a1.setUsername("admin@ecp.sio.fr");
        String passwordToHash = "password";
        try {
            String generatedPassword = null;
            // Create  MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());

            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
            // we set a hashed password then we compare the password get from the user to the one in the dummy
            a1.setPasswordHash(generatedPassword);
        }
         catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        // Warning: the list below will be immutable (be the contained entities can be modified)
        mAccounts = Collections.singletonList(a1);
    }

    @Override
    public Account getAccount(String username) {
        return mAccounts.stream()
                .filter(account -> account.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    // Below are (unused) variants of getAccount(username)

    // A simple loop with index
    public Account getAccountWithIndexedLoop(String username) {
        for (int i = 0; i < mAccounts.size(); i++) {
            Account a = mAccounts.get(i);
            if (a.getUsername().equalsIgnoreCase(username)) {
                return a;
            }
        }
        return null;
    }

    // A better iteration syntax for collections, more readable
    public Account getAccountWithIteration(String username) {
        for (Account a: mAccounts) {
            if (a.getUsername().equalsIgnoreCase(username)) {
                return a;
            }
        }
        return null;
    }

    // A newer "stream" manipulation syntax, with method chaining
    public Account getAccountWithStream(String username) {
        return mAccounts
                // Obtain a streamable view of the list
                .stream()
                // Keep only items matching a predicate (function that return a boolean)
                .filter(new Predicate<Account>() {
                    @Override
                    public boolean test(Account account) {
                        return account.getUsername().equalsIgnoreCase(username);
                    }
                })
                // Get the first item (returns an Optional<Movie>)
                .findFirst()
                // If not found, return null
                .orElse(null);
    }

    // Same version as above, where the Predicate is replaced by a lambda
    public Account getAccountWithStreamLambda(String username) {
        return mAccounts.stream()
                .filter(account -> account.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

}
