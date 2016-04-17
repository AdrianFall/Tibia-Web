package core.repository;

import core.repository.model.web.Account;
import core.repository.model.web.VerificationToken;

/**
 * Created by Adrian on 14/05/2015.
 */
public interface VerificationTokenRepo {
    public VerificationToken createVerificationToken(VerificationToken token);
    public VerificationToken findVerificationToken(String verificationToken);
    public VerificationToken updateVerificationToken(VerificationToken token, Account acc);
    public VerificationToken findCurrentVerificationTokenOfAccount(Account acc);
}
