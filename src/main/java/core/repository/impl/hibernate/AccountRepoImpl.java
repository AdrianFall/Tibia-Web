package core.repository.impl.hibernate;

import core.repository.model.web.Account;
import core.repository.model.web.Role;
import core.repository.model.web.Test;
import core.repository.AccountRepo;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Adrian on 10/05/2015.
 */
@Repository
@Transactional
public class AccountRepoImpl implements AccountRepo {

    /*@PersistenceContext
    private EntityManager emgr;*/

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Account findAccount(Long id) {
        /*return emgr.find(Account.class, id);*/
        Account acc = (Account) sessionFactory.getCurrentSession()
                .createCriteria(Account.class)
                .add(Restrictions.eq("id", id))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();

        return acc;
    }

    @Override
    public Account findAccountByUsername(String username) {
        /*Query query = emgr.createQuery("SELECT a FROM Account a WHERE a.username = :username");
        query.setParameter("username", username);

        if (!query.getResultList().isEmpty())
            return (Account) query.getResultList().get(0);
        else
            return null;*/
        Account account = (Account) sessionFactory.getCurrentSession()
                .createCriteria(Account.class)
                .add(Restrictions.eq("username", username))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();
        return account;
    }

    @Override
    public Account findAccountByEmail(String email) {
        /*Query query = emgr.createQuery("SELECT a FROM Account a WHERE a.email = :email");
        query.setParameter("email", email);

        if (!query.getResultList().isEmpty())
            return (Account) query.getResultList().get(0);
        else
            return null;*/
        Account account = (Account) sessionFactory.getCurrentSession()
                .createCriteria(Account.class)
                .add(Restrictions.eq("email", email))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();
        return account;
    }

    @Override
    public Account createAccount(Account acc) {
        // Find the user role
        Role role = (Role) sessionFactory.getCurrentSession()
                .createCriteria(Role.class)
                .add(Restrictions.eq("role", "ROLE_USER"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();
        if (role == null)
            return null;

        // attach the roles to account obj
        Set<Role> accRoles = new HashSet<>();
        accRoles.add(role);
        acc.setAccRoles(accRoles);

        // create the new acc
        sessionFactory.getCurrentSession()
                .save(acc);
        sessionFactory.getCurrentSession().flush();

        return acc;
    }

    @Override
    public Account updateAccount(Account acc) {
        /*return emgr.merge(acc);*/
        sessionFactory.getCurrentSession()
                .saveOrUpdate(acc);
        sessionFactory.getCurrentSession()
                .flush();
        return acc;
    }

    @Override
    public Test getTest(Account acc) {
        /*Query query = emgr.createQuery("SELECT t FROM Test t WHERE t.acc =:acc");
        query.setParameter("acc", acc);

        if (query.getResultList().isEmpty())
            return null;

        Test test = (Test) query.getResultList().get(0);

        return test;*/
        Test test = (Test) sessionFactory.getCurrentSession()
                .createCriteria(Test.class)
                .add(Restrictions.eq("acc", acc))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();
        return test;
    }

    @Override
    public Test setTestWord(Account acc, String testWord) {
        /*Test test = getTest(acc);
        if (test == null) { // no record for this acc yet
            System.out.println("      GOTTA PERSIST NEW TEST");
            // persist
            test = new Test();
            test.setAcc(acc);
            test.setTest_word(testWord);
            emgr.persist(test);
            emgr.flush();
            System.out.println("               PERSISTED!");
        } else { // record exists for this acc
            System.out.println("          FOUND EXISTING RECORD, GONNA MERGE");
            test.setTest_word(testWord);
            emgr.merge(test);
            emgr.flush();
        }

        return test;*/

        Test test = getTest(acc);
        if (test == null) {
            test = new Test();
            test.setAcc(acc);
        }

        // update test word
        test.setTest_word(testWord);

        sessionFactory.getCurrentSession()
                .saveOrUpdate(test);
        sessionFactory.getCurrentSession()
                .flush();
        return test;
    }

    @Override
    public boolean deleteAllAccounts() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Test; DELETE FROM persistent_logins; DELETE FROM password_reset_token; DELETE FROM verification_token; DELETE FROM accounts_roles;");
        query.executeUpdate();
        Query finalQuery = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM account;");
        int numberOfEntitiesUpdated = finalQuery.executeUpdate();
        if (numberOfEntitiesUpdated > 0)
            return true;
        return false;
    }
}
