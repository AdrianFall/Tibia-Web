package core.repository.service;

import core.repository.model.web.Account;
import core.repository.model.web.PasswordResetToken;
import core.repository.model.web.Test;
import core.repository.model.web.VerificationToken;
import core.repository.service.exception.EmailExistsException;

/**
 * Created by Adrian on 09/05/2015.
 */
public interface AccountService {
    public Account createAccount(Account acc) throws EmailExistsException;
    public Account updateAccount(Account acc);
    public VerificationToken createVerificationToken(Account acc, String token);
    public VerificationToken findVerificationToken(String token);
    public VerificationToken findCurrentVerificationTokenOfAccountByEmail(String email);
    public Account findAccount(String email);
    public VerificationToken updateVerificationToken(VerificationToken newToken, Account acc);
    public PasswordResetToken createPasswordResetToken(Account acc, String token);
    public PasswordResetToken findPasswordResetToken(String token);
    public PasswordResetToken findCurrentPasswordResetTokenOfAccountByEmail(String email);
    public String encodePassword(String password);
    public Test getTest(Account acc);
    public Test setTest(Account acc, String testWord);
    public boolean deleteAllAccounts();
}
