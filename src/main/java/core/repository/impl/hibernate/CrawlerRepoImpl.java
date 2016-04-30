package core.repository.impl.hibernate;

import core.repository.AccountRepo;
import core.repository.CrawlerRepo;
import core.repository.model.crawler.TibiaHuntedPlayer;
import core.repository.model.crawler.TibiaPlayer;
import core.repository.model.crawler.servers.oldera.OlderaPlayer;
import core.repository.model.crawler.servers.thronia.ThroniaPlayer;
import core.repository.model.web.Account;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Adrian on 24/04/2016.
 */
@Repository
@Transactional
public class CrawlerRepoImpl implements CrawlerRepo {

    // Logger
    static Logger logger = Logger.getLogger(CrawlerRepoImpl.class.getName());

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    AccountRepo accountRepo;

    @Override
    public List<TibiaPlayer> getOnlinePlayers(String serverName) {
        ProjectionList p = Projections.projectionList();
        p.add(Projections.property("name"));
        p.add(Projections.property("level"));
        p.add(Projections.property("vocation"));
        // p.add(Projections.property("id"));
        //p.add(Projections.property("server_name"));
        //p.add(Projections.property("server_name"));

        List<Object[]> onlinePlayersArray = (List<Object[]>) sessionFactory.getCurrentSession()
                .createCriteria(TibiaPlayer.class)
                .add(Restrictions.eq("isOnline", true))
                .add(Restrictions.eq("server.name", serverName))
                .setProjection(Projections.distinct(p))
                .list();

        List<TibiaPlayer> onlinePlayers = new ArrayList<>();

        onlinePlayersArray.forEach(e -> {
            TibiaPlayer player = new TibiaPlayer();
            player.setName((String) e[0]);
            player.setLevel((Integer) e[1]);
            player.setVocation((String) e[2]);
            onlinePlayers.add(player);
        });


        return onlinePlayers;
    }

    @Override
    public TibiaPlayer findTibiaPlayer(String playerName, String serverName) {

        TibiaPlayer tibiaPlayer = (TibiaPlayer) sessionFactory.getCurrentSession()
                .createCriteria(TibiaPlayer.class)
                .add(Restrictions.eq("server.name", serverName))
                .add(Restrictions.eq("name", playerName))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .uniqueResult();

        return tibiaPlayer;
    }

    @Override
    public TibiaPlayer findHuntedPlayer(Long playerId, Long accountId, String serverName) {
        TibiaHuntedPlayer huntedPlayer = (TibiaHuntedPlayer) sessionFactory.getCurrentSession()
                .createCriteria(TibiaHuntedPlayer.class)
                .add(Restrictions.eq("accountId", accountId))
                .add(Restrictions.eq("tibiaServerName", serverName))
                .add(Restrictions.eq("tibiaPlayer.id", playerId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .uniqueResult();

        if (huntedPlayer != null) {
            TibiaPlayer tibiaPlayer = (TibiaPlayer) sessionFactory.getCurrentSession()
                    .createCriteria(TibiaPlayer.class)
                    .add(Restrictions.eq("id", playerId))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .uniqueResult();
            return tibiaPlayer;
        }


        return null;
    }

    @Override
    public TibiaHuntedPlayer findHuntedPlayer(Long accountId, String huntedPlayerName, String serverName) {
        TibiaPlayer tibiaPlayer = findTibiaPlayer(huntedPlayerName, serverName);

        if (tibiaPlayer == null) {
            logger.warning("findHuntedPlayer -> couldn't find tibiaPlayer name: " + huntedPlayerName + " serverName: " + serverName);
            return null;
        }

        TibiaHuntedPlayer huntedPlayer = (TibiaHuntedPlayer) sessionFactory.getCurrentSession()
                .createCriteria(TibiaHuntedPlayer.class)
                .add(Restrictions.eq("accountId", accountId))
                .add(Restrictions.eq("tibiaServerName", serverName))
                .add(Restrictions.eq("tibiaPlayer.id", tibiaPlayer.getId()))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .uniqueResult();

        return huntedPlayer;

    }

    @Override
    public TibiaHuntedPlayer findHuntedPlayer(Long huntedPlayerId) {
        TibiaHuntedPlayer huntedPlayer = (TibiaHuntedPlayer) sessionFactory.getCurrentSession()
                .createCriteria(TibiaHuntedPlayer.class)
                .add(Restrictions.eq("id", huntedPlayerId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .uniqueResult();

        return huntedPlayer;
    }

    @Override
    public void removeHuntedPlayer(TibiaHuntedPlayer huntedPlayer) {
        sessionFactory.getCurrentSession().delete(huntedPlayer);
    }



    @Override
    public TibiaPlayer addToHuntedList(Long playerId, Long accountId, String serverName) {
        TibiaHuntedPlayer tibiaHuntedPlayer = new TibiaHuntedPlayer(accountId, serverName, playerId);
        sessionFactory.getCurrentSession()
                .save(tibiaHuntedPlayer);
        sessionFactory.getCurrentSession().flush();

        return findHuntedPlayer(playerId, accountId, serverName);
    }


    @Override
    public boolean removeFromOlderaHuntedList(Long playerId, Long accountId) {
        TibiaHuntedPlayer olderaHuntedPlayer = (TibiaHuntedPlayer) sessionFactory.getCurrentSession()
                .createCriteria(TibiaHuntedPlayer.class)
                .add(Restrictions.eq("accountId", accountId))
                .add(Restrictions.eq("tibiaServerName", "Oldera"))
                .add(Restrictions.eq("tibiaPlayerId", playerId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .uniqueResult();

        if (olderaHuntedPlayer != null) {
            sessionFactory.getCurrentSession()
                    .delete(olderaHuntedPlayer);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFromThroniaHuntedList(Long playerId, Long accountId) {
        TibiaHuntedPlayer throniaHuntedPlayer = (TibiaHuntedPlayer) sessionFactory.getCurrentSession()
                .createCriteria(TibiaHuntedPlayer.class)
                .add(Restrictions.eq("accountId", accountId))
                .add(Restrictions.eq("tibiaServerName", "Thronia"))
                .add(Restrictions.eq("tibiaPlayerId", playerId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .uniqueResult();

        if (throniaHuntedPlayer != null) {
            sessionFactory.getCurrentSession()
                    .delete(throniaHuntedPlayer);
            return true;
        }
        return false;
    }

    @Override
    public List<TibiaPlayer> getHuntedList(Long accountId, String serverName) {
        List<TibiaHuntedPlayer> tibiaHuntedPlayers = sessionFactory.getCurrentSession()
                .createCriteria(TibiaHuntedPlayer.class)
                .add(Restrictions.eq("accountId", accountId))
                .add(Restrictions.eq("tibiaServerName", serverName))
                .list();

        if (tibiaHuntedPlayers != null) {
            List<TibiaPlayer> tibiaPlayers = new ArrayList<>();
            tibiaHuntedPlayers.forEach(e -> {
                TibiaPlayer tibiaPlayer = findHuntedPlayer(e.getTibiaPlayer().getId(), accountId, serverName);
                if (tibiaPlayer != null) {
                    tibiaPlayers.add(tibiaPlayer);
                }
            });
            return tibiaPlayers;
        }
        return null;
    }
}
