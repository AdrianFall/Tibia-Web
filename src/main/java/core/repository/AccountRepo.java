package core.repository;

import core.repository.model.web.Account;
import core.repository.model.web.Test;

/**
 * Created by Adrian on 10/05/2015.
 */
public interface AccountRepo {

    public Account findAccount(Long id);
    public Account findAccountByUsername(String username);
    public Account findAccountByEmail(String email);
    public Account createAccount(Account acc);
    public Account updateAccount(Account acc);
    public Test getTest(Account acc);
    public Test setTestWord(Account acc, String testWord);
    public boolean deleteAllAccounts();
}
