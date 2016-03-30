package core.repository.service;

import core.repository.model.Account;
import core.repository.model.PasswordResetToken;
import core.repository.model.Test;
import core.repository.model.VerificationToken;
import core.repository.service.exception.EmailExistsException;

/**
 * Created by Adrian on 09/05/2015.
 */
public interface AccountService {
    public Account createAccount(Account acc) throws EmailExistsException;
    public Account updateAccount(Account acc);
    public VerificationToken createVerificationToken(Account acc, String token);
    public VerificationToken findVerificationToken(String token);
    public Account findAccount(String email);
    public VerificationToken updateVerificationToken(VerificationToken newToken, Account acc);
    public PasswordResetToken createPasswordResetToken(Account acc, String token);
    public PasswordResetToken findPasswordResetToken(String token);
    public String encodePassword(String password);
    public Test getTest(Account acc);
    public Test setTest(Account acc, String testWord);
}
