package core.repository.impl.hibernate;

import core.repository.OlderaCrawlerRepo;
import core.repository.model.crawler.servers.oldera.OlderaPlayer;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Adrian on 17/04/2016.
 */
@Repository
@Transactional
public class OlderaCrawlerRepoImpl implements OlderaCrawlerRepo {

    @Autowired
    @Qualifier(value="crawlerSessionFactory")
    SessionFactory crawlerSessionFactory;


    @Override
    public List<OlderaPlayer> getOnlinePlayers() {
        List<OlderaPlayer> onlinePlayers = (List<OlderaPlayer>) crawlerSessionFactory.getCurrentSession()
                .createCriteria(OlderaPlayer.class)
                .add(Restrictions.eq("isOnline", true))
                .list();

        return onlinePlayers;
    }
}
