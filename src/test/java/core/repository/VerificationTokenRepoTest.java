package core.repository;

import config.PersistenceConfig;
import config.SpringConfig;
import config.WebSecurityConfig;
import core.repository.model.web.Account;
import core.repository.model.web.VerificationToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Adrian on 30/03/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class, WebSecurityConfig.class, SpringConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
public class VerificationTokenRepoTest {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private VerificationTokenRepo tokenRepo;

    // First account
    private Account firstAcc;

    @Before
    @Rollback(true)
    public void setup() throws  Exception{
        firstAcc = new Account();
        firstAcc.setEmail("xa@xa");
        firstAcc.setPassword("superhard");
        accountRepo.createAccount(firstAcc);

    }

    @Test
    public void createAndFindAndUpdateVerificationToken() throws Exception {
        assertNotNull(firstAcc.getId());
        String token = UUID.randomUUID().toString();
        VerificationToken createdVerificaitonToken = tokenRepo.createVerificationToken(new VerificationToken(token, firstAcc));
        assertNotNull(createdVerificaitonToken);

        // Now find
        VerificationToken verificationToken = tokenRepo.findVerificationToken(token);
        assertNotNull(verificationToken);
        assertNotNull(verificationToken.getAcc());

        // And Create updateToken
        String newToken = UUID.randomUUID().toString();
        VerificationToken tokenToUpdate =  new VerificationToken(newToken, verificationToken.getAcc());
        VerificationToken updatedToken = tokenRepo.updateVerificationToken(tokenToUpdate, firstAcc);
        assertNotNull(updatedToken);
        assertNotEquals(updatedToken.getToken(), token);
        assertEquals(updatedToken.getAcc(), createdVerificaitonToken.getAcc());

        // Ensure that it was updated
        assertNull(tokenRepo.findVerificationToken(token));
        assertNotNull(tokenRepo.findVerificationToken(newToken));
    }


}
