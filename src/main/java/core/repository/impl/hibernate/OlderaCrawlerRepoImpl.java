package core.repository.impl.hibernate;

import core.repository.OlderaCrawlerRepo;
import core.repository.model.crawler.servers.oldera.OlderaPlayer;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 17/04/2016.
 */
@Repository
@Transactional
public class OlderaCrawlerRepoImpl implements OlderaCrawlerRepo {

    @Autowired
    SessionFactory sessionFactory;


    @Override
    public List<OlderaPlayer> getOnlinePlayers() {
        ProjectionList p = Projections.projectionList();
        p.add(Projections.property("name"));
        p.add(Projections.property("level"));
        p.add(Projections.property("vocation"));
        // p.add(Projections.property("id"));
        //p.add(Projections.property("server_name"));
        //p.add(Projections.property("server_name"));

        List<Object[]> onlinePlayersArray = (List<Object[]>) sessionFactory.getCurrentSession()
                .createCriteria(OlderaPlayer.class)
                .add(Restrictions.eq("isOnline", true))
                //.add(Restrictions.eq("server", "Oldera"))
                .setProjection(Projections.distinct(p))
                .list();

        List<OlderaPlayer> onlinePlayers = new ArrayList<>();

        onlinePlayersArray.forEach(e -> {
            OlderaPlayer olderaPlayer = new OlderaPlayer();
            olderaPlayer.setName((String) e[0]);
            olderaPlayer.setLevel((Integer) e[1]);
            olderaPlayer.setVocation((String) e[2]);
            onlinePlayers.add(olderaPlayer);
        });


        return onlinePlayers;
    }
}
