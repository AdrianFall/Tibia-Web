package core.repository;

import core.repository.model.web.Account;
import core.repository.model.web.PasswordResetToken;

/**
 * Created by Adrian on 29/06/2015.
 */
public interface PasswordResetTokenRepo {
    public PasswordResetToken createPasswordResetToken(PasswordResetToken token);
    public PasswordResetToken findPasswordResetToken(String token);
    public PasswordResetToken findCurrentPasswordResetTokenOfAccount(Account acc);
    public PasswordResetToken updatePasswordResetToken(PasswordResetToken newToken, Account acc);
}
