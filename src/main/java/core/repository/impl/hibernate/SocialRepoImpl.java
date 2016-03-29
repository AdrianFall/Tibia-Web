package core.repository.impl.hibernate;

import core.entity.SocialProvider;
import core.repository.SocialRepo;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Adrian on 06/07/2015.
 */
@Repository
@Transactional
public class SocialRepoImpl implements SocialRepo {
    /*@PersistenceContext
    private EntityManager emgr;*/

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public SocialProvider findSocialProviderByName(String name) {
        /*Query query = emgr.createQuery("SELECT s FROM SocialProvider s WHERE s.provider_name = :name");
        query.setParameter("name", name);

        if (!query.getResultList().isEmpty())
            return (SocialProvider) query.getResultList().get(0);
        else
            return null;*/

        SocialProvider socialProvider = (SocialProvider) sessionFactory.getCurrentSession()
                .createCriteria(SocialProvider.class)
                .add(Restrictions.eq("provider_name", name))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();
        return socialProvider;
    }
}
